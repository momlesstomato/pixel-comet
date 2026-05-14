package com.cometproject.api.game.rooms.entities;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.rooms.IRoom;

/**
 * Defines the player room entity contract for the room subsystem.
 */
public interface PlayerRoomEntity extends RoomEntity {
    /**
     * Returns the player associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayer getPlayer();

    /**
     * Returns the room associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IRoom getRoom();
}
