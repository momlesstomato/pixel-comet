package com.cometproject.server.game.players.components.types.navigator;

import com.cometproject.api.game.players.data.components.navigator.ISavedSearch;

/**
 * Describes saved search behavior for the player subsystem.
 */
public class SavedSearch implements ISavedSearch {
    private final String view;
    private final String searchQuery;

    /**
     * Creates a saved search instance for the player subsystem.
     *
     * @param view View supplied by the caller.
     * @param searchQuery Search query supplied by the caller.
     */
    public SavedSearch(final String view, final String searchQuery) {
        this.view = view;
        this.searchQuery = searchQuery;
    }

    /**
     * Returns the view for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getView() {
        return view;
    }

    /**
     * Returns the search query for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getSearchQuery() {
        return searchQuery;
    }
}
