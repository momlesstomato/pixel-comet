package com.cometproject.stresstest.connections;

import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.codec.MessageDecoder;
import com.cometproject.server.protocol.codec.MessageEncoder;
import com.cometproject.stresstest.CometStressTest;
import com.cometproject.stresstest.connections.messages.SSOTicketMessageComposer;
import com.cometproject.stresstest.connections.messages.WalkMessageComposer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Describes comet client connection behavior for the tooling subsystem.
 */
public class CometClientConnection {
    private boolean isConnected = false;

    private boolean isOnline = false;
    private boolean isInRoom = false;
    private boolean isWalk = true;

    private Channel channel;
    /**
     * Creates a comet client connection instance for the tooling subsystem.
     *
     * @param config Config supplied by the caller.
     * @param loopGroup Loop group supplied by the caller.
     */
    public CometClientConnection(CometClientConfig config, EventLoopGroup loopGroup) {
        final Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            /**
             * Executes init channel for this tooling contract.
             *
             * @param socketChannel Socket channel supplied by the caller.
             */
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast("encoder", new MessageEncoder());
                socketChannel.pipeline().addLast("decoder", new MessageDecoder());
            }
        });

        bootstrap.remoteAddress(config.getHostName(), config.getPort());

        ChannelFuture connectFuture = bootstrap.connect();

        connectFuture.addListener((future) -> {
            if (!future.isSuccess()) {
                System.out.println("[" + config.getSsoTicket() + "] Failed to connect to server!");
            } else {
                System.out.println("[" + config.getSsoTicket() + "] Connected to the server.");

                // we can do shit! :D
                this.isConnected = true;
                this.channel = connectFuture.channel();

                this.channel.writeAndFlush(new SSOTicketMessageComposer(config.getSsoTicket()));
                this.isOnline = true;
            }
        });
    }

    /**
     * Executes tick for this tooling contract.
     */
    public void tick() {
        if(this.isOnline() && this.isInRoom() && this.isWalk()) {
            int x = CometStressTest.getRandom(1, 32);
            int y = CometStressTest.getRandom(0, 32);

            this.send(new WalkMessageComposer(x, y));
        }
    }

    /**
     * Executes send for this tooling contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void send(MessageComposer msg) {
        this.channel.writeAndFlush(msg);
    }

    /**
     * Executes disconnect for this tooling contract.
     */
    public void disconnect() {
        if(!this.isConnected) {
            channel.disconnect();
        }
    }

    /**
     * Indicates whether online applies to this tooling contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Indicates whether connected applies to this tooling contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isConnected() {
        return isConnected;
    }


    /**
     * Indicates whether in room applies to this tooling contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isInRoom() {
        return isInRoom;
    }

    /**
     * Updates the is in room for this tooling contract.
     *
     * @param isInRoom Is in room supplied by the caller.
     */
    public void setIsInRoom(boolean isInRoom) {
        this.isInRoom = isInRoom;
    }

    /**
     * Indicates whether walk applies to this tooling contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isWalk() {
        return isWalk;
    }

    /**
     * Updates the is walk for this tooling contract.
     *
     * @param isWalk Is walk supplied by the caller.
     */
    public void setIsWalk(boolean isWalk) {
        this.isWalk = isWalk;
    }

}
