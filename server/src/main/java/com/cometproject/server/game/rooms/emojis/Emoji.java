package com.cometproject.server.game.rooms.emojis;

/**
 * Describes emoji behavior for the room subsystem.
 */
public class Emoji {
    public int id;
    public String key;

    /**
     * Creates a emoji instance for the room subsystem.
     *
     * @param id Id supplied by the caller.
     * @param key Key supplied by the caller.
     */
    public Emoji(int id, String key){
        this.id = id;
        this.key = key;
    }
}
