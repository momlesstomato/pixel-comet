package com.cometproject.server.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.network.ConnectionRegistryConfiguration;
import com.cometproject.api.networking.registry.ConnectionRegistry;
import com.cometproject.api.utilities.Startable;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.network.messages.GameMessageHandler;
import com.cometproject.server.network.messages.MessageHandler;
import com.cometproject.server.network.monitor.MonitorClient;
import com.cometproject.server.network.registry.InMemoryConnectionRegistry;
import com.cometproject.server.network.registry.RedisConnectionRegistry;
import com.cometproject.server.network.sessions.SessionManager;
import com.cometproject.server.network.sessions.net.NetSessionFactory;
import com.cometproject.server.network.transports.JavalinWebSocketTransport;
import com.cometproject.server.network.transports.NettyTcpTransport;

import io.netty.util.ResourceLeakDetector;


public class NetworkManager implements Startable {
    public static boolean IDLE_TIMER_ENABLED = Boolean.parseBoolean(Configuration.currentConfig().get("comet.network.idleTimer.enabled", "true"));
    public static int IDLE_TIMER_READER_TIME = Integer.parseInt(Configuration.currentConfig().get("comet.network.idleTimer.readerIdleTime", "60"));
    public static int IDLE_TIMER_WRITER_TIME = Integer.parseInt(Configuration.currentConfig().get("comet.network.idleTimer.writerIdleTime", "0"));
    public static int IDLE_TIMER_ALL_TIME = Integer.parseInt(Configuration.currentConfig().get("comet.network.idleTimer.allIdleTime", "0"));
    private static Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class.getName());
    private int serverPort;
    private SessionManager sessions;
    private MessageHandler messageHandler;
    private MonitorClient monitorClient;
    private ConnectionRegistry connectionRegistry;
    private TransportManager transportManager;

    public NetworkManager() {

    }

    public static NetworkManager getInstance() {
        return CometBootstrap.resolve(NetworkManager.class);
    }

    @Override
    public void start() {
        this.start(Configuration.currentConfig().get("comet.network.host"), Configuration.currentConfig().get("comet.network.port"));
    }

    private void start(final String ip, final String ports) {
        this.sessions = new SessionManager();
        this.messageHandler = new MessageHandler();
        this.connectionRegistry = this.createConnectionRegistry();
        this.transportManager = new TransportManager();

        this.serverPort = Integer.parseInt(ports.split(",")[0]);

        //InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());

        System.setProperty("io.netty.leakDetectionLevel", "disabled");
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);

        final INetSessionFactory sessionFactory = new NetSessionFactory(this.sessions, new GameMessageHandler());
        this.transportManager.addTransport(new NettyTcpTransport(Configuration.currentConfig(), ip, ports, sessionFactory));
        this.transportManager.addTransport(new JavalinWebSocketTransport(
                Configuration.currentConfig(),
                this.sessions,
                sessionFactory,
                this.connectionRegistry
        ));
        this.transportManager.start();
    }

    @Override
    public void stop() {
        if (this.transportManager != null) {
            this.transportManager.stop();
        }

        if (this.connectionRegistry instanceof RedisConnectionRegistry redisConnectionRegistry) {
            redisConnectionRegistry.dispose();
        }
    }

    public SessionManager getSessions() {
        return this.sessions;
    }

    public MessageHandler getMessages() {
        return this.messageHandler;
    }

    public ConnectionRegistry getConnectionRegistry() {
        return this.connectionRegistry;
    }

    public MonitorClient getMonitorClient() {
        return monitorClient;
    }

    public int getServerPort() {
        return serverPort;
    }

    private ConnectionRegistry createConnectionRegistry() {
        final String implementation = Configuration.currentConfig().getOrDefault(
                ConnectionRegistryConfiguration.IMPLEMENTATION,
                ConnectionRegistryConfiguration.defaults().get(ConnectionRegistryConfiguration.IMPLEMENTATION)
        );

        if ("redis".equalsIgnoreCase(implementation)) {
            LOGGER.info("Using Redis-backed connection registry");
            return new RedisConnectionRegistry();
        }

        LOGGER.info("Using in-memory connection registry");
        return new InMemoryConnectionRegistry();
    }
}
