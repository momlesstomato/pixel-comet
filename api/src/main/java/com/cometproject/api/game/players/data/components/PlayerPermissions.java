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
     * Returns the effective legacy rank id for packets and legacy checks.
     *
     * @return the effective legacy rank id.
     */
    default int getLegacyRankId() {
        return this.getRank().getId();
    }

    /**
     * Returns the effective hierarchy priority for staff comparisons.
     *
     * @return the effective priority.
     */
    default int getHighestPriority() {
        return this.getRank().getId();
    }

    /**
     * Indicates whether this player contract has command.
     *
     * @param key Key value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasCommand(String key);
}
