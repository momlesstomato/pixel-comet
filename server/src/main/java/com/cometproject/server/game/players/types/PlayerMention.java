package com.cometproject.server.game.players.types;

/**
 * Describes player mention behavior for the player subsystem.
 */
public class PlayerMention {
    private final String username;
    private final String message;

    /**
     * Creates a player mention instance for the player subsystem.
     *
     * @param username Username supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public PlayerMention(String username, String message) {
        this.username = username;
        this.message = message;
    }

    /**
     * Returns the username for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the message for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return this.message;
    }
}
