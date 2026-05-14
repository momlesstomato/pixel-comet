package com.cometproject.server.network.monitor;


import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Describes monitor message handler behavior for the networking subsystem.
 */
public class MonitorMessageHandler {
    private List<String> messageRegistry;
    private Logger LOGGER = LoggerFactory.getLogger(MonitorMessageHandler.class.getName());

    /**
     * Creates a monitor message handler instance for the networking subsystem.
     */
    public MonitorMessageHandler() {
        this.messageRegistry = new ArrayList<>();

        this.messageRegistry.add("hello");
        this.messageRegistry.add("heartbeat");
    }

    /**
     * Executes handle for this networking contract.
     *
     * @param message Message supplied by the caller.
     * @param ctx Netty channel context for the current operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean handle(MonitorPacket message, ChannelHandlerContext ctx) {
        String messageHeader = message.getName();

        if (!this.messageRegistry.contains(messageHeader)) {
            return false;
        }

        try {
            MonitorMessageLibrary.request = message.getMessage();
            MonitorMessageLibrary.ctx = ctx;

            Method method = MonitorMessageLibrary.class.getMethod(messageHeader);
            method.invoke(new Object[0]);
        } catch (Exception e) {
            LOGGER.error("Error while handling monitor packet", e);
            return false;
        }

        return true;
    }
}
