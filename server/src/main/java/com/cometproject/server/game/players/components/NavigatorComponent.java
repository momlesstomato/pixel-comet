package com.cometproject.server.game.players.components;

import com.cometproject.api.game.players.data.components.navigator.ISavedSearch;
import com.cometproject.server.game.players.components.types.navigator.SavedSearch;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.storage.queries.player.PlayerDao;

import java.util.Map;
import java.util.Set;

/**
 * Owns navigator behavior inside the player subsystem.
 */
public class NavigatorComponent extends PlayerComponent {

    private final Map<Integer, ISavedSearch> savedSearches;
    private final Map<String, Integer> viewModes;
    private final Set<Integer> favouriteRooms;

    /**
     * Creates a navigator component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public NavigatorComponent(final Player player) {
        super(player);

        this.savedSearches = PlayerDao.getSavedSearches(this.getPlayer().getId());
        this.favouriteRooms = PlayerDao.getFavouriteRooms(this.getPlayer().getId());
        this.viewModes = PlayerDao.getViewModes(this.getPlayer().getId());
    }

    /**
     * Releases resources owned by this player component.
     */
    public void dispose() {
        this.savedSearches.clear();
        this.favouriteRooms.clear();
        this.viewModes.clear();
    }

    /**
     * Indicates whether search saved applies to this player contract.
     *
     * @param newSearch New search supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSearchSaved(SavedSearch newSearch) {
        for (ISavedSearch savedSearch : this.getSavedSearches().values()) {
            if (savedSearch.getView().equals(newSearch.getView()) && savedSearch.getSearchQuery().equals(newSearch.getSearchQuery()))
                return true;
        }

        return false;
    }

    /**
     * Returns the favourite rooms for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getFavouriteRooms() {
        return this.favouriteRooms;
    }

    /**
     * Returns the view modes for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, Integer> getViewModes() {
        return this.viewModes;
    }

    /**
     * Returns the saved searches for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, ISavedSearch> getSavedSearches() {
        return this.savedSearches;
    }
}
