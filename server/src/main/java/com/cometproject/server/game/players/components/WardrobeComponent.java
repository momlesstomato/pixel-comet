package com.cometproject.server.game.players.components;

import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.storage.queries.player.PlayerClothingDao;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;

import java.util.Set;

/**
 * Owns wardrobe behavior inside the player subsystem.
 */
public class WardrobeComponent {
    private final Set<String> purchasedClothing;

    private final Player player;

    /**
     * Creates a wardrobe component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public WardrobeComponent(final Player player) {
        this.player = player;

        this.purchasedClothing = new ConcurrentHashSet<>();
        PlayerClothingDao.getClothing(this.player.getId(), this.purchasedClothing);
    }

    /**
     * Releases resources owned by this player component.
     */
    public void dispose() {
        this.purchasedClothing.clear();
    }

    /**
     * Returns the clothing for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<String> getClothing() {
        return purchasedClothing;
    }
}
