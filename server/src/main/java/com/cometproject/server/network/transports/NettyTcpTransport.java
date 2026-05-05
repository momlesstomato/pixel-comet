package com.cometproject.server.network.transports;

import com.cometproject.api.config.ConfigurationSource;
import com.cometproject.api.config.network.TransportConfiguration;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.transports.ConnectionTransport;
import com.cometproject.networking.api.INetworkingServer;
import com.cometproject.networking.api.INetworkingServerFactory;
import com.cometproject.networking.api.NetworkingContext;
import com.cometproject.networking.api.config.NetworkingServerConfig;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.network.NettyNetworkingServerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Starts the legacy Netty TCP listener behind the transport abstraction.
 */
public final class NettyTcpTransport implements ConnectionTransport {
    private final ConfigurationSource configuration;
    private final String host;
    private final String ports;
    private final INetSessionFactory sessionFactory;
    private INetworkingServer server;

    /**
     * Creates a TCP transport wrapper using the existing Netty networking server.
     *
     * @param configuration The active configuration source.
     * @param host The TCP bind host.
     * @param ports The configured comma-delimited TCP ports.
     * @param sessionFactory The shared session factory.
     */
    public NettyTcpTransport(
            final ConfigurationSource configuration,
            final String host,
            final String ports,
            final INetSessionFactory sessionFactory
    ) {
        this.configuration = configuration;
        this.host = host;
        this.ports = ports;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the transport type advertised by this listener.
     *
     * @return The TCP transport type.
     */
    @Override
    public ConnectionTransportType getType() {
        return ConnectionTransportType.TCP;
    }

    /**
     * Returns whether the TCP listener should be started.
     *
     * @return True when TCP transport is enabled.
     */
    @Override
    public boolean isEnabled() {
        return Boolean.parseBoolean(this.configuration.getOrDefault(
                TransportConfiguration.TCP_ENABLED,
                TransportConfiguration.defaults().get(TransportConfiguration.TCP_ENABLED)
        ));
    }

    /**
     * Starts the configured Netty TCP listener.
     */
    @Override
    public void start() {
        final INetworkingServerFactory serverFactory = new NettyNetworkingServerFactory(this.configuration);
        NetworkingContext.setCurrentContext(new NetworkingContext(serverFactory));
        this.server = serverFactory.createServer(new NetworkingServerConfig(this.host, this.parsePorts()), this.sessionFactory);
        this.server.start();
    }

    /**
     * Stops the transport.
     *
     * <p>The underlying Netty server bootstrap does not currently expose a shutdown hook,
     * so shutdown remains best-effort until the lower-level server lifecycle is extended.
     */
    @Override
    public void stop() {
    }

    private Set<Short> parsePorts() {
        final Set<Short> portSet = new LinkedHashSet<>();

        for (String port : this.ports.split(",")) {
            portSet.add(Short.parseShort(port.trim()));
        }

        return portSet;
    }
}