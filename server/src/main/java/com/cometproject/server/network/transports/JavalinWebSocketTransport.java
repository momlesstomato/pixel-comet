package com.cometproject.server.network.transports;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.ConfigurationSource;
import com.cometproject.api.config.network.TransportConfiguration;
import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.registry.ConnectionRegistry;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.networking.transports.ConnectionTransport;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.api.APIManager;
import com.cometproject.server.network.connections.JavalinWebSocketConnection;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.sessions.SessionManager;
import com.cometproject.server.network.util.ProtocolMessageCodec;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.WebSocketSessionManager;
import com.cometproject.server.network.websockets.packets.incoming.IWebSocketHandler;
import com.cometproject.server.network.websockets.packets.incoming.battleroyale.AsyncMovementHandler;
import com.cometproject.server.network.websockets.packets.incoming.battleroyale.BattleRoyaleLeaveQueueHandler;
import com.cometproject.server.network.websockets.packets.incoming.battleroyale.BattleRoyaleWeaponSwapHandler;
import com.cometproject.server.network.websockets.packets.incoming.minigames.AcceptDuelSuggestionHandler;
import com.cometproject.server.network.websockets.packets.incoming.minigames.AcceptMinigameSuggestionHandler;
import com.cometproject.server.network.websockets.packets.incoming.minigames.VisitEventHandler;
import com.cometproject.server.network.websockets.packets.incoming.player.AuthenticationHandler;
import com.cometproject.server.network.websockets.packets.incoming.player.BuilderSyncHandler;
import com.cometproject.server.network.websockets.packets.incoming.player.CallForHelpHandler;
import com.cometproject.server.network.websockets.packets.incoming.player.MacroHandler;
import com.cometproject.server.network.websockets.packets.incoming.system.SubscriptionRevisionHandler;

import io.javalin.Javalin;
import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Hosts the hotel WebSocket transport and the browser side-channel on Javalin.
 */
public final class JavalinWebSocketTransport implements ConnectionTransport {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavalinWebSocketTransport.class);
    private static final String GAME_SOCKET_PATH = "/ws";
    private static final String BROWSER_SOCKET_PATH = "/browser";

    private final ConfigurationSource configuration;
    private final SessionManager sessionManager;
    private final INetSessionFactory sessionFactory;
    private final ConnectionRegistry connectionRegistry;
    private final APIManager apiManager;
    private final Map<String, JavalinWebSocketConnection> gameConnections = new ConcurrentHashMap<>();
    private final Map<String, INetSession> gameSessions = new ConcurrentHashMap<>();
    private final Map<String, WebSocketClientConnection> browserConnections = new ConcurrentHashMap<>();
    private final Map<String, IWebSocketHandler> browserHandlers;

    private Javalin app;

    /**
     * Creates a new Javalin-backed WebSocket transport.
     *
     * @param configuration The active configuration source.
     * @param sessionManager The session manager used by gameplay and browser side-channel cleanup.
     * @param sessionFactory The shared game session factory.
     * @param connectionRegistry The active registry for game transport connections.
     * @param apiManager The management API coordinator sharing this Javalin server.
     */
    public JavalinWebSocketTransport(
            final ConfigurationSource configuration,
            final SessionManager sessionManager,
            final INetSessionFactory sessionFactory,
            final ConnectionRegistry connectionRegistry,
            final APIManager apiManager
    ) {
        this.configuration = configuration;
        this.sessionManager = sessionManager;
        this.sessionFactory = sessionFactory;
        this.connectionRegistry = connectionRegistry;
        this.apiManager = apiManager;
        this.browserHandlers = this.createBrowserHandlers();
    }

    /**
     * Returns the transport type identifier.
     *
     * @return The WebSocket transport type.
     */
    @Override
    public ConnectionTransportType getType() {
        return ConnectionTransportType.WEBSOCKETS;
    }

    /**
     * Returns whether the WebSocket listener should be started.
     *
     * @return True when the WebSocket transport is enabled.
     */
    @Override
    public boolean isEnabled() {
        return this.isWebSocketsEnabled() || this.apiManager.isEnabled();
    }

    /**
     * Starts the Javalin WebSocket listener and registers all WebSocket endpoints.
     */
    @Override
    public void start() {
        this.app = Javalin.create(config -> config.showJavalinBanner = false);

        if (this.apiManager.isEnabled()) {
            this.apiManager.configureRoutes(this.app);
        }

        if (this.isWebSocketsEnabled()) {
            this.registerGameSocket();
            this.registerBrowserSocket();
        }

        this.app.start(this.resolvePort());
    }

    /**
     * Stops the Javalin listener and clears any cached socket state.
     */
    @Override
    public void stop() {
        if (this.app != null) {
            this.app.stop();
            this.app = null;
        }

        this.gameSessions.clear();
        this.gameConnections.clear();
        this.browserConnections.clear();
    }

    private void registerGameSocket() {
        this.app.ws(GAME_SOCKET_PATH, ws -> {
            ws.onConnect(this::handleGameConnect);
            ws.onBinaryMessage(this::handleGameBinaryMessage);
            ws.onMessage(this::handleUnexpectedGameTextMessage);
            ws.onClose(this::handleGameClose);
            ws.onError(this::handleGameError);
        });
    }

    private void registerBrowserSocket() {
        this.app.ws(BROWSER_SOCKET_PATH, this::configureBrowserSocket);
    }

    private void configureBrowserSocket(final WsConfig ws) {
        ws.onConnect(this::handleBrowserConnect);
        ws.onMessage(this::handleBrowserMessage);
        ws.onClose(this::handleBrowserClose);
        ws.onError(this::handleBrowserError);
    }

    private void handleGameConnect(final WsConnectContext context) {
        final String sessionId = context.sessionId();
        final JavalinWebSocketConnection connection = new JavalinWebSocketConnection(context);
        final INetSession session = this.sessionFactory.createSession(connection);

        if (session == null) {
            connection.close(ConnectionCloseCode.NORMAL);
            return;
        }

        this.gameConnections.put(sessionId, connection);
        this.gameSessions.put(sessionId, session);
        this.connectionRegistry.register(connection);
    }

    private void handleGameBinaryMessage(final WsBinaryMessageContext context) {
        final String sessionId = context.sessionId();
        final JavalinWebSocketConnection connection = this.gameConnections.get(sessionId);
        final INetSession session = this.gameSessions.get(sessionId);

        if (connection == null || session == null) {
            context.closeSession(1008, "No active game session.");
            return;
        }

        final ByteBuf payload = Unpooled.wrappedBuffer(context.data(), context.offset(), context.length());

        try {
            ProtocolMessageCodec.decode(payload, connection.getCipher()).forEach(event ->
                    session.getMessageHandler().handleMessage(event, session)
            );
        } catch (Exception exception) {
            LOGGER.error("Failed to process game WebSocket payload", exception);
            this.cleanupGameSocket(sessionId, ConnectionCloseCode.PROTOCOL_ERROR, true);
        } finally {
            payload.release();
        }
    }

    private void handleUnexpectedGameTextMessage(final WsMessageContext context) {
        LOGGER.warn("Rejecting text frame on game WebSocket transport");
        this.cleanupGameSocket(context.sessionId(), ConnectionCloseCode.PROTOCOL_ERROR, true);
    }

    private void handleGameClose(final WsCloseContext context) {
        this.cleanupGameSocket(context.sessionId(), ConnectionCloseCode.NORMAL, false);
    }

    private void handleGameError(final WsErrorContext context) {
        LOGGER.error("Game WebSocket transport error", context.error());
        this.cleanupGameSocket(context.sessionId(), ConnectionCloseCode.PROTOCOL_ERROR, true);
    }

    private void handleBrowserConnect(final WsConnectContext context) {
        final WebSocketClientConnection connection = new WebSocketClientConnection(context);
        this.browserConnections.put(context.sessionId(), connection);
        WebSocketSessionManager.getInstance().addChannel(connection);
    }

    private void handleBrowserMessage(final WsMessageContext context) {
        final WebSocketClientConnection connection = this.browserConnections.get(context.sessionId());

        if (connection == null) {
            return;
        }

        try {
            final IncomingPayload payload = JsonUtil.getInstance().fromJson(context.message(), IncomingPayload.class);

            if (payload == null || payload.handler == null || payload.handler.isBlank()) {
                return;
            }

            final IWebSocketHandler handler = this.browserHandlers.get(payload.handler);
            if (handler != null) {
                handler.handle(connection, context.message());
            }
        } catch (Exception exception) {
            LOGGER.debug("Ignoring malformed browser WebSocket payload", exception);
        }
    }

    private void handleBrowserClose(final WsCloseContext context) {
        this.cleanupBrowserSocket(context.sessionId(), false);
    }

    private void handleBrowserError(final WsErrorContext context) {
        LOGGER.error("Browser WebSocket transport error", context.error());
        this.cleanupBrowserSocket(context.sessionId(), true);
    }

    private void cleanupGameSocket(
            final String sessionId,
            final ConnectionCloseCode closeCode,
            final boolean closeConnection
    ) {
        final INetSession session = this.gameSessions.remove(sessionId);
        final JavalinWebSocketConnection connection = this.gameConnections.remove(sessionId);

        if (connection != null) {
            this.connectionRegistry.unregister(connection.getId());

            if (closeConnection) {
                connection.close(closeCode);
            }
        }

        if (session != null) {
            this.sessionFactory.disposeSession(session);
        }
    }

    private void cleanupBrowserSocket(final String sessionId, final boolean closeConnection) {
        final WebSocketClientConnection connection = this.browserConnections.remove(sessionId);

        if (connection == null) {
            return;
        }

        WebSocketSessionManager.getInstance().removeChannel(connection);
        this.detachBrowserChannel(connection);

        if (closeConnection) {
            connection.close();
        }
    }

    private void detachBrowserChannel(final WebSocketClientConnection connection) {
        for (ISession value : this.sessionManager.getSessions().values()) {
            if (value instanceof Session session && session.getWsChannel() == connection) {
                session.setWsChannel(null);
            }
        }
    }

    private Map<String, IWebSocketHandler> createBrowserHandlers() {
        final Map<String, IWebSocketHandler> handlers = new LinkedHashMap<>();
        handlers.put("authentication", new AuthenticationHandler());
        handlers.put("visitEvent", new VisitEventHandler());
        handlers.put("callForHelp", new CallForHelpHandler());
        handlers.put("asyncMove", new AsyncMovementHandler());
        handlers.put("builderSync", new BuilderSyncHandler());
        handlers.put("leaveQueue", new BattleRoyaleLeaveQueueHandler());
        handlers.put("acceptMinigame", new AcceptMinigameSuggestionHandler());
        handlers.put("acceptDuel", new AcceptDuelSuggestionHandler());
        handlers.put("handleMacro", new MacroHandler());
        handlers.put("weaponSwap", new BattleRoyaleWeaponSwapHandler());
        handlers.put("verifyOffer", new SubscriptionRevisionHandler());
        return Map.copyOf(handlers);
    }

    private int resolvePort() {
        return Integer.parseInt(this.configuration.get(
                TransportConfiguration.WEBSOCKETS_PORT,
                TransportConfiguration.defaults().get(TransportConfiguration.WEBSOCKETS_PORT)
        ));
    }

    private boolean isWebSocketsEnabled() {
        return Boolean.parseBoolean(this.configuration.get(
                TransportConfiguration.WEBSOCKETS_ENABLED,
                TransportConfiguration.defaults().get(TransportConfiguration.WEBSOCKETS_ENABLED)
        ));
    }

    private static final class IncomingPayload {
        private String handler;
    }
}