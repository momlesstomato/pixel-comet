package com.cometproject.server.network.sessions;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionState;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.games.snowwar.data.SnowWarPlayerData;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.types.components.games.RoomGame;
import com.cometproject.server.game.rooms.types.components.games.survival.SurvivalGame;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalPlayer;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalQueue;
import com.cometproject.server.network.connections.NettyTcpConnection;
import com.cometproject.server.network.messages.outgoing.notification.LogoutMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarUpdateMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.protocol.crypto.exceptions.HabboEncryption;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;

import io.netty.channel.ChannelHandlerContext;


/**
 * Describes session behavior for the network session subsystem.
 */
public class Session implements ISession {
    public static int CLIENT_VERSION = 0;
    private final Connection connection;
    private final int networkId;
    private final UUID uuid = UUID.randomUUID();
    private Logger LOGGER = LoggerFactory.getLogger("session");
    private SessionEventHandler eventHandler;

    private boolean isClone = false;
    private String uniqueId = "";
    private Player player;
    private boolean disconnectCalled = false;
    public SnowWarPlayerData snowWarPlayerData;
    private final List<String> authTokens = new CopyOnWriteArrayList<>();

    private WebSocketClientConnection wsChannel;
    private final HabboEncryption encryption;
    private boolean handshakeFinished;
    private long lastPing = Comet.getTime();

    /**
     * Creates a session instance for the network session subsystem.
     *
     * @param connection Connection supplied by the caller.
     * @param networkId Network id supplied by the caller.
     */
    public Session(final Connection connection, final int networkId) {
        this.connection = connection;
        this.networkId = networkId;
        this.encryption = CometSettings.cryptoEnabled ? new HabboEncryption(CometSettings.crypto_e, CometSettings.crypto_n, CometSettings.crypto_d) : null;
        this.connection.setState(ConnectionState.AUTHENTICATING);
    }

    /**
     * Executes initialise for this network session contract.
     */
    public void initialise() {
        this.eventHandler = new SessionEventHandler(this);
    }

    /**
     * Handles the disconnect callback for this network session contract.
     */
    public void onDisconnect() {
        if (this.disconnectCalled) {
            return;
        }

        this.disconnectCalled = true;

        PlayerManager.getInstance().getPlayerLoadExecutionService().submit(() -> {
            try {
                if (player != null && player.getData() != null)
                    PlayerManager.getInstance().remove(player.getId(), player.getData().getUsername(), this.networkId, this.getIpAddress());

                this.eventHandler.dispose();

                if (this.player != null) {
                    if (this.getPlayer().getPermissions().getRank().modTool()) {
                        ModerationManager.getInstance().removeModerator(this);
                    }

                    if (this.getPlayer().getPermissions().getRank().messengerLogChat()) {
                        ModerationManager.getInstance().removeLogChatUser(this);
                    }

                    if (this.getPlayer().getPermissions().getRank().messengerAlfaChat()) {
                        ModerationManager.getInstance().removeAlfaChatUser(this);
                    }

                    if(this.getPlayer().getSurvivalRoomId() > 0) {
                        SurvivalQueue.getInstance().removePlayerFromQueue(this.getPlayer().getSurvivalRoomId(), this.getPlayer().getId(), this.getPlayer().getQueueData());
                    }

                    if(this.getPlayer().getEntity() != null && this.getPlayer().getEntity().isSurvivalMode()) {
                        final RoomGame game = this.getPlayer().getEntity().getRoom().getGame().getInstance();

                        if (!(game instanceof SurvivalGame))
                            return;

                        final SurvivalGame survivalGame = (SurvivalGame) game;
                        final SurvivalPlayer survivalPlayer = survivalGame.survivalPlayer(this.getPlayer().getData().getId());
                        if (survivalPlayer != null) {
                            ((SurvivalGame) game).playerLeaves(this.getPlayer().getData().getId(), true);
                        }
                    }

                    try {
                        this.getPlayer().dispose();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                this.revokeAuthTokens();
                this.setPlayer(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Executes disconnect for this network session contract.
     */
    public void disconnect() {
        this.onDisconnect();

        this.connection.close(ConnectionCloseCode.NORMAL);
    }

    /**
     * Returns the IP address for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public String getIpAddress() {
        String ipAddress = "0.0.0.0";

        if (this.player == null || !CometSettings.useDatabaseIp) {
            return this.connection.getRemoteAddress();
        } else {
            if (this.getPlayer() != null) {
                ipAddress = PlayerDao.getIpAddress(this.getPlayer().getId());
            }
        }

        return ipAddress;
    }

    /**
     * Executes disconnect for this network session contract.
     *
     * @param reason Reason supplied by the caller.
     */
    public void disconnect(String reason) {
        this.send(new LogoutMessageComposer(reason));
        this.disconnect();
    }

    /**
     * Handles message event for this network session contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handleMessageEvent(MessageEvent msg) {
        this.eventHandler.handle(msg);
    }

    /**
     * Executes send queue for this network session contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @return Result produced by the operation.
     */
    public Session sendQueue(final IMessageComposer msg) {
        return this.send(msg, true);
    }

    /**
     * Executes send for this network session contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @return Result produced by the operation.
     */
    public Session send(IMessageComposer msg) {
        return this.send(msg, false);
    }

    /**
     * Executes send for this network session contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param queue Queue supplied by the caller.
     * @return Result produced by the operation.
     */
    public Session send(IMessageComposer msg, boolean queue) {
        if (msg == null) {
            return this;
        }

        if (msg.getId() == 0) {
            LOGGER.debug("Unknown header ID for message: " + msg.getClass().getSimpleName());
        }

        final ConnectionState connectionState = this.connection.getState();
        if (connectionState == ConnectionState.CLOSING || connectionState == ConnectionState.CLOSED) {
            return this;
        }

        this.connection.send(msg);

        if (!(msg instanceof AvatarUpdateMessageComposer) && !(msg instanceof UpdateFloorItemMessageComposer))
            LOGGER.debug("Sent packet {} (id={}) to connection {}",
                    msg.getClass().getSimpleName(), msg.getId(), this.networkId);

        return this;
    }

    /**
     * Executes flush for this network session contract.
     */
    public void flush() {
        this.connection.flush();
    }

    /**
     * Returns the logger for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public Logger getLogger() {
        return this.LOGGER;
    }

    /**
     * Returns the player for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Updates the player for this network session contract.
     *
     * @param player Player participating in the operation.
     */
    public void setPlayer(Player player) {
        if (player == null || player.getData() == null) {
            this.player = null;
            return;
        }

        String username = player.getData().getUsername();

        this.LOGGER = LoggerFactory.getLogger("[" + username + "][" + player.getId() + "]");
        this.player = player;
        this.snowWarPlayerData = new SnowWarPlayerData(player);

        PlayerManager.getInstance().put(player.getId(), this.networkId, username, this.getIpAddress());
        this.connection.setState(ConnectionState.AUTHENTICATED);

        if (player.getPermissions().getRank().modTool()) {
            ModerationManager.getInstance().addModerator(player.getSession());
        }

        if (player.getPermissions().getRank().messengerAlfaChat()) {
            ModerationManager.getInstance().addAlfa(player.getSession());
        }

        if (player.getPermissions().getRank().messengerLogChat()) {
            ModerationManager.getInstance().addLogChatUser(player.getSession());
        }
    }

    /**
     * Returns the connection for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Returns the channel for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public ChannelHandlerContext getChannel() {
        if (this.connection instanceof NettyTcpConnection) {
            return ((NettyTcpConnection) this.connection).getContext();
        }

        return null;
    }

    /**
     * Returns the unique id for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Updates the unique id for this network session contract.
     *
     * @param uniqueId Unique id supplied by the caller.
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Returns the session id for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public UUID getSessionId() {
        return uuid;
    }

    /**
     * Returns the network id for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNetworkId() {
        return this.networkId;
    }

    /**
     * Returns the encryption for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public HabboEncryption getEncryption() {
        return this.encryption;
    }

    /**
     * Indicates whether handshake finished applies to this network session contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isHandshakeFinished() {
        return handshakeFinished;
    }

    /**
     * Updates the handshake finished for this network session contract.
     *
     * @param handshakeFinished Handshake finished supplied by the caller.
     */
    public void setHandshakeFinished(boolean handshakeFinished) {
        this.handshakeFinished = handshakeFinished;
    }

    /**
     * Returns the last ping for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastPing() {
        return lastPing;
    }

    /**
     * Updates the last ping for this network session contract.
     *
     * @param lastPing Last ping supplied by the caller.
     */
    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    /**
     * Returns the WebSocket channel for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public WebSocketClientConnection getWsChannel() {
        return wsChannel;
    }

    /**
     * Updates the WebSocket channel for this network session contract.
     *
     * @param wsChannel Websocket channel supplied by the caller.
     */
    public void setWsChannel(WebSocketClientConnection wsChannel) {
        this.wsChannel = wsChannel;
    }

    /**
     * Registers a Redis-backed session token to be revoked when the session closes.
     *
     * @param authToken The token associated with this session.
     */
    public void registerAuthToken(final String authToken) {
        if (authToken != null && !authToken.isBlank()) {
            this.authTokens.add(authToken);
        }
    }

    private void revokeAuthTokens() {
        if (this.authTokens.isEmpty()) {
            return;
        }

        final ISsoTicketService ssoTicketService = CometBootstrap.getCurrentInjector().getInstance(ISsoTicketService.class);

        for (String authToken : this.authTokens) {
            try {
                ssoTicketService.revoke(authToken);
            } catch (Exception exception) {
                this.LOGGER.warn("Failed to revoke session token during disconnect", exception);
            }
        }

        this.authTokens.clear();
    }
}
