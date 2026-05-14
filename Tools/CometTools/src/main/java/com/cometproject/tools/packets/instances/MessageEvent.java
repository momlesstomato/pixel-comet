package com.cometproject.tools.packets.instances;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.tools.packets.Packet;

import java.util.List;


/**
 * Represents the message event published by the tooling subsystem.
 */
public class MessageEvent extends Packet {
    private List<String> structure;

    /**
     * Creates a message event instance for the tooling subsystem.
     *
     * @param id Id supplied by the caller.
     * @param className Class name supplied by the caller.
     */
    public MessageEvent(short id, String className) {
        super(id, className, PacketType.EVENT);
    }
}
