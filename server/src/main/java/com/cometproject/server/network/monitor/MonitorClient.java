package com.cometproject.server.network.monitor;


import com.cometproject.server.boot.Comet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;


/**
 * Describes monitor client behavior for the networking subsystem.
 */
public class MonitorClient {
    public final String MONITOR_HOST = "monitor.comet.openhabbo.com";
    public final int MONITOR_PORT = 13337;

    /**
     * Creates a monitor client instance for the networking subsystem.
     *
     * @param loopGroup Loop group supplied by the caller.
     */
    public MonitorClient(EventLoopGroup loopGroup) {
        createBootstrap(new Bootstrap(), loopGroup);
    }

    /**
     * Creates bootstrap for this networking contract.
     *
     * @param bootstrap Bootstrap supplied by the caller.
     * @param eventLoop Event loop supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
        if (bootstrap != null) {
            final MonitorClientHandler handler = new MonitorClientHandler();

            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);

            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                /**
                 * Executes init channel for this networking contract.
                 *
                 * @param socketChannel Socket channel supplied by the caller.
                 */
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(handler);
                }
            });

            bootstrap.remoteAddress(Comet.isDebugging ? "monitor.comet.openhabbo.com" : MONITOR_HOST, MONITOR_PORT);
            bootstrap.connect().addListener(new ConnectionListener(this));
        }
        return bootstrap;
    }

    /**
     * Describes connection listener behavior for the networking subsystem.
     */
    public class ConnectionListener implements ChannelFutureListener {
        private MonitorClient client;

        /**
         * Executes connection listener for this networking contract.
         *
         * @param client Client supplied by the caller.
         */
        public ConnectionListener(MonitorClient client) {
            this.client = client;
        }

        /**
         * Executes operation complete for this networking contract.
         *
         * @param channelFuture Channel future supplied by the caller.
         */
        @Override
        public void operationComplete(ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                MonitorClientHandler.isConnected = false;

                final EventLoop loop = channelFuture.channel().eventLoop();
                loop.schedule(() -> client.createBootstrap(new Bootstrap(), loop), 1L, TimeUnit.SECONDS);
            }
        }
    }
}
