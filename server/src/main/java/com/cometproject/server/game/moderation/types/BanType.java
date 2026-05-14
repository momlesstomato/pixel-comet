package com.cometproject.server.game.moderation.types;

/**
 * Enumerates ban type values used by the moderation subsystem.
 */
public enum BanType {
    IP,
    USER,
    TRADE,
    PRISON,
    MUTE,
    MACHINE;

    /**
     * Returns the type for this moderation contract.
     *
     * @param type Type supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static BanType getType(String type) {
        return type.equals("ip") ? IP : type.equals("user") ? USER : type.equals("trade") ? TRADE : type.equals("mute") ? MUTE : type.equals("prison") ? PRISON : MACHINE;
    }
}
