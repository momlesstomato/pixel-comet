package com.cometproject.server.network.clients;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.networking.registry.ConnectionRegistry;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.connections.NettyTcpConnection;
import com.cometproject.server.network.messages.outgoing.misc.PingMessageComposer;
import com.cometproject.server.protocol.messages.MessageEvent;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

/**
 * Describes client handler behavior for the networking subsystem.
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<MessageEvent> {
    private static final AttributeKey<INetSession> ATTR_SESSION = AttributeKey.newInstance("NetSession");

    private static Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class.getName());

    private static ClientHandler clientHandlerInstance;
    private final INetSessionFactory sessionFactory;

    /**
     * Creates a client handler instance for the networking subsystem.
     *
     * @param sessionFactory Session factory supplied by the caller.
     */
    public ClientHandler(INetSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the instance for this networking contract.
     *
     * @param netSessionFactory Net session factory supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static ClientHandler getInstance(INetSessionFactory netSessionFactory) {
        if (clientHandlerInstance == null)
            clientHandlerInstance = new ClientHandler(netSessionFactory);

        return clientHandlerInstance;
    }

    /**
     * Executes channel active for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final NettyTcpConnection connection = new NettyTcpConnection(ctx);
        final INetSession session = this.sessionFactory.createSession(connection);

        if (session == null) {
            connection.dispose();
            ctx.disconnect();
            return;
        }

        final ConnectionRegistry connectionRegistry = NetworkManager.getInstance().getConnectionRegistry();
        if (connectionRegistry != null) {
            connectionRegistry.register(connection);
        }

        ctx.channel().attr(ATTR_SESSION).set(session);
    }

    /**
     * Executes channel inactive for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (ctx.channel().attr(ATTR_SESSION).get() == null) {
            return;
        }

        try {
            INetSession session = ctx.channel().attr(ATTR_SESSION).get();

            final ConnectionRegistry connectionRegistry = NetworkManager.getInstance().getConnectionRegistry();
            if (connectionRegistry != null) {
                connectionRegistry.unregister(session.getConnection().getId());
            }

            this.sessionFactory.disposeSession(session);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        ctx.channel().attr(ATTR_SESSION).set(null);
        ctx.disconnect(); // TODO: REMOVE THIS IF IT FUCKS UP
    }

    /**
     * Executes user event triggered for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param evt Evt supplied by the caller.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (NetworkManager.IDLE_TIMER_ENABLED) {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.READER_IDLE) {
                    ctx.close();
                } else if (e.state() == IdleState.WRITER_IDLE) {
                    ctx.channel().writeAndFlush(new PingMessageComposer(), ctx.voidPromise());
                }
            }
        }

        if (evt instanceof ChannelInputShutdownEvent) {
            ctx.close();
        }
    }

    /**
     * Executes exception caught for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param cause Cause supplied by the caller.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (ctx.channel().isActive()) {
            ctx.close();
        }

        if (cause instanceof IOException || cause instanceof TooLongFrameException) return;

        LOGGER.error("Exception caught in ClientHandler", cause);
    }

    /**
     * Executes channel read0 for this networking contract.
     *
     * @param channelHandlerContext Channel handler context supplied by the caller.
     * @param event Event supplied by the caller.
     */
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, MessageEvent event) {
        try {
            final INetSession session = channelHandlerContext.channel().attr(ATTR_SESSION).get();

            if (session != null) {
                session.getMessageHandler().handleMessage(event, session);
            }
        } catch (Exception e) {
            LOGGER.error("Error while receiving message", e);
        }
    }

    /**
     * Executes channel read complete for this networking contract.
     *
     * @param context Context supplied by the caller.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }
}