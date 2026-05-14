package com.cometproject.server.storage.cache.objects.items;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.server.storage.cache.CachableObject;
import com.google.gson.JsonObject;

/**
 * Describes player item data object behavior for the storage subsystem.
 */
public class PlayerItemDataObject extends CachableObject {
    private PlayerItem playerItem;

    /**
     * Creates a player item data object instance for the storage subsystem.
     *
     * @param playerItem Player item supplied by the caller.
     */
    public PlayerItemDataObject(PlayerItem playerItem) {
        this.playerItem = playerItem;
    }

    /**
     * Executes to JSON for this storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonObject toJson() {
        JsonObject coreObject = new JsonObject();

        coreObject.addProperty("id", playerItem.getId());
        coreObject.addProperty("baseId", playerItem.getBaseId());
        coreObject.addProperty("extraData", playerItem.getExtraData());

        return coreObject;
    }
}
