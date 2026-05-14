package com.cometproject.server.storage.cache.objects.items;

import com.cometproject.api.game.rooms.objects.data.LimitedEditionItemData;
import com.google.gson.JsonObject;

/**
 * Describes wall item data object behavior for the storage subsystem.
 */
public class WallItemDataObject extends RoomItemDataObject {
    private final String wallPosition;

    /**
     * Creates a wall item data object instance for the storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param itemDefinitionId Item definition id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param owner Owner supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param data Data supplied by the caller.
     * @param wallPosition Wall position supplied by the caller.
     * @param limitedEditionItemData Limited edition item data supplied by the caller.
     */
    public WallItemDataObject(long id, int itemDefinitionId, int roomId, int owner, String ownerName, String data, String wallPosition, LimitedEditionItemData limitedEditionItemData) {
        super(id, itemDefinitionId, roomId, owner, ownerName, data, limitedEditionItemData);

        this.wallPosition = wallPosition;
    }

    /**
     * Executes to JSON object for this storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonObject toJsonObject() {
        final JsonObject object = super.toJsonObject();

        object.addProperty("wallPosition", this.wallPosition);

        return object;
    }

    /**
     * Returns the wall position for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getWallPosition() {
        return wallPosition;
    }
}
