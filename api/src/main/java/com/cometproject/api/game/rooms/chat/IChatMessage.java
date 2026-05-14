package com.cometproject.api.game.rooms.chat;

/**
 * Defines the i chat message contract for the room subsystem.
 */
public interface IChatMessage {
    /**
     * Returns the player id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPlayerId();

    /**
     * Returns the message associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMessage();
}
