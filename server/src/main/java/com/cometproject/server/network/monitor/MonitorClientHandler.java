package com.cometproject.server.network.monitor;


import com.cometproject.server.boot.Comet;
import com.cometproject.server.network.NetworkManager;
import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;


/**
 * Describes monitor client handler behavior for the networking subsystem.
 */
public class MonitorClientHandler extends SimpleChannelInboundHandler {
    public static boolean isConnected = false;

    private Logger LOGGER = LoggerFactory.getLogger(MonitorClientHandler.class.getName());
    private ByteBuf handshakeMessage;
    private MonitorMessageHandler messageHandler;
    private Gson gson = new Gson();

    private ChannelHandlerContext context;

    /**
     * Creates a monitor client handler instance for the networking subsystem.
     */
    public MonitorClientHandler() {
        String message = "{\"name\":\"hello\", \"message\": {\"version\": \"" + Comet.getBuild() + "\", \"port\": " + NetworkManager.getInstance().getServerPort() + "}}";

        this.handshakeMessage = Unpooled.buffer(message.getBytes().length);

        for (int i = 0; i < this.handshakeMessage.capacity(); i++) {
            this.handshakeMessage.writeByte(message.getBytes()[i]);
        }
    }

    /**
     * Executes channel active for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        MonitorClientHandler.isConnected = true;

        this.context = ctx;
        this.messageHandler = new MonitorMessageHandler();

        ctx.writeAndFlush(this.handshakeMessage);
    }

    /**
     * Executes channel read0 for this networking contract.
     *
     * @param channelHandlerContext Channel handler context supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) {
        ByteBuf buffer = (ByteBuf) msg;
        String messageJson = buffer.toString(Charset.defaultCharset());

        MonitorPacket message = this.gson.fromJson(messageJson, MonitorPacket.class);

        this.messageHandler.handle(message, channelHandlerContext);
    }

    /**
     * Executes channel read complete for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * Executes channel inactive for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MonitorClientHandler.isConnected = false;

        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> NetworkManager.getInstance().getMonitorClient().createBootstrap(new Bootstrap(), eventLoop), 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    /**
     * Executes exception caught for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param cause Cause supplied by the caller.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Exception caught from MonitorClient", cause);
        ctx.close();
    }

    /**
     * Returns the context for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public ChannelHandlerContext getContext() {
        return context;
    }
}
