package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.players.data.components.navigator.ISavedSearch;

/**
 * Defines the player navigator contract for the player subsystem.
 */
public interface PlayerNavigator {
    /**
     * Indicates whether search saved is enabled for this player contract.
     *
     * @param savedSearch Saved search value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isSearchSaved(ISavedSearch savedSearch);
}
