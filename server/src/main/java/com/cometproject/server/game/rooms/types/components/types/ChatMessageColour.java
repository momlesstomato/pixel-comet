package com.cometproject.server.game.rooms.types.components.types;

/**
 * Enumerates chat message colour values used by the room processing subsystem.
 */
public enum ChatMessageColour {
    red,
    blue,
    green,
    purple,
    cyan;

    /**
     * Returns the all available for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public static String getAllAvailable() {
        return "red, blue, green, purple, cyan, normal";
    }
}
