package com.cometproject.server.network.websockets;

import com.cometproject.api.utilities.Initialisable;

public class WebSocketManager implements Initialisable {

    @Override
    public void initialize() {
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

    private static WebSocketManager instance;

    public static WebSocketManager getInstance() {
        if (instance == null) instance = new WebSocketManager();

        return instance;
    }

}
