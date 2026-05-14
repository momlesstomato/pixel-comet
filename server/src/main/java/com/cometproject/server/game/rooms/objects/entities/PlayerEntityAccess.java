package com.cometproject.server.game.rooms.objects.entities;

import com.cometproject.server.game.players.types.Player;


/**
 * Defines the player entity access contract for the room subsystem.
 */
public interface PlayerEntityAccess {
    /**
     * Returns the player for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    Player getPlayer();
}
