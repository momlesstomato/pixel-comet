package com.cometproject.server.game.rooms.types.components.types;

/**
 * Describes chat message behavior for the room processing subsystem.
 */
public class ChatMessage {
    private int playerId;
    private String message;

    /**
     * Creates a chat message instance for the room processing subsystem.
     *
     * @param userId User id supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public ChatMessage(int userId, String message) {
        this.playerId = userId;
        this.message = message;
    }

    /**
     * Returns the player id for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the message for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }
}
