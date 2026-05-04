package com.cometproject.server.network.websockets;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;

public class WebSocketManager implements Startable {

    @Override
    public void start() {
/*
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new HttpServerCodec())
                                    .addLast(new WebSocketHandler());
                        }

                    })
                    .option(ChannelOption.SO_BACKLOG, 1024);

            serverBootstrap.bind(2087);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
*/
    }

    public static WebSocketManager getInstance() {
        return CometBootstrap.resolve(WebSocketManager.class);
    }

}
