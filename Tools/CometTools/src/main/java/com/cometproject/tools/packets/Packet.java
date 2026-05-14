package com.cometproject.tools.packets;

import com.cometproject.api.networking.messages.IMessageComposer;
/**
 * Describes packet behavior for the tooling subsystem.
 */
public abstract class Packet {
    private short id;
    private String className;
    private PacketType type;

    /**
     * Creates a packet instance for the tooling subsystem.
     *
     * @param id Id supplied by the caller.
     * @param className Class name supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public Packet(short id, String className, PacketType type) {
        this.id = id;
        this.className = className;
        this.type = type;
    }

    /**
     * Returns the class name for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the type for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public PacketType getType() {
        return type;
    }

    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public short getId() {
        return id;
    }

    /**
     * Enumerates packet type values used by the tooling subsystem.
     */
    public enum PacketType {
        COMPOSER, EVENT
    }
}
