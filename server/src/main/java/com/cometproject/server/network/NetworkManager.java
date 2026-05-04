package com.cometproject.server.network;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.networking.api.INetworkingServer;
import com.cometproject.networking.api.INetworkingServerFactory;
import com.cometproject.networking.api.NetworkingContext;
import com.cometproject.networking.api.config.NetworkingServerConfig;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.network.messages.GameMessageHandler;
import com.cometproject.server.network.messages.MessageHandler;
import com.cometproject.server.network.monitor.MonitorClient;
import com.cometproject.server.network.sessions.SessionManager;
import com.cometproject.server.network.sessions.net.NetSessionFactory;
import com.google.common.collect.Sets;
import io.netty.util.ResourceLeakDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;


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

        this.serverPort = Integer.parseInt(ports.split(",")[0]);

        //InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());

        System.setProperty("io.netty.leakDetectionLevel", "disabled");
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);

        final INetSessionFactory sessionFactory = new NetSessionFactory(this.sessions, new GameMessageHandler());
        final INetworkingServerFactory serverFactory = new NettyNetworkingServerFactory(Configuration.currentConfig());
        final NetworkingContext networkingContext = new NetworkingContext(serverFactory);

        NetworkingContext.setCurrentContext(networkingContext);

        final Set<Short> portSet = Sets.newHashSet();

        if (ports.contains(",")) {
            for (String port : ports.split(",")) {
                portSet.add(Short.parseShort(port));
            }
        } else {
            portSet.add(Short.parseShort(ports));
        }

        final INetworkingServer gameServer = serverFactory.createServer(new NetworkingServerConfig(ip, portSet),
                sessionFactory);

        gameServer.start();
    }

    public SessionManager getSessions() {
        return this.sessions;
    }

    public MessageHandler getMessages() {
        return this.messageHandler;
    }

    public MonitorClient getMonitorClient() {
        return monitorClient;
    }

    public int getServerPort() {
        return serverPort;
    }
}
