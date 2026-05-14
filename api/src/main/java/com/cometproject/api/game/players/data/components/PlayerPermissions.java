package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.players.data.IPlayerComponent;
import com.cometproject.api.game.players.data.components.permissions.PlayerRank;

/**
 * Defines the player permissions contract for the player subsystem.
 */
public interface PlayerPermissions extends IPlayerComponent {
    /**
     * Returns the rank associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerRank getRank();

    /**
     * Indicates whether this player contract has command.
     *
     * @param key Key value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasCommand(String key);
}
