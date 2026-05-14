package com.cometproject.api.game.players.data.components.navigator;

/**
 * Defines the i saved search contract for the player subsystem.
 */
public interface ISavedSearch {
    /**
     * Returns the view associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getView();

    /**
     * Returns the search query associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getSearchQuery();
}
