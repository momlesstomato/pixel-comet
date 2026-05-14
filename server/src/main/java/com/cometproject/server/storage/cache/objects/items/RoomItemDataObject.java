package com.cometproject.server.storage.cache.objects.items;

import com.cometproject.api.game.rooms.objects.data.LimitedEditionItemData;
import com.cometproject.server.storage.cache.CachableObject;
import com.google.gson.JsonObject;

/**
 * Describes room item data object behavior for the storage subsystem.
 */
public abstract class RoomItemDataObject extends CachableObject {
    private final long id;
    private final int itemDefinitionId;
    private final int roomId;
    private final int owner;
    private final String ownerName;
    private final String data;

    private final LimitedEditionItemData limitedEditionItemData;

    /**
     * Creates a room item data object instance for the storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param itemDefinitionId Item definition id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param owner Owner supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param data Data supplied by the caller.
     * @param limitedEditionItemData Limited edition item data supplied by the caller.
     */
    public RoomItemDataObject(long id, int itemDefinitionId, int roomId, int owner, String ownerName, String data, LimitedEditionItemData limitedEditionItemData) {
        this.id = id;
        this.itemDefinitionId = itemDefinitionId;
        this.roomId = roomId;
        this.owner = owner;
        this.ownerName = ownerName;
        this.data = data;
        this.limitedEditionItemData = limitedEditionItemData;
    }

    /**
     * Executes to JSON object for this storage contract.
     *
     * @return Result produced by the operation.
     */
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        final JsonObject limitedItemData = new JsonObject();

        jsonObject.addProperty("id", this.id);
        jsonObject.addProperty("itemDefinitionId", this.itemDefinitionId);
        jsonObject.addProperty("roomId", this.roomId);
        jsonObject.addProperty("owner", this.owner);
        jsonObject.addProperty("ownerName", this.ownerName);
        jsonObject.addProperty("data", this.data);

        if (this.limitedEditionItemData != null) {
            limitedItemData.addProperty("itemId", this.limitedEditionItemData.getItemId());
            limitedItemData.addProperty("limitedRare", this.limitedEditionItemData.getLimitedRare());
            limitedItemData.addProperty("limitedRareTotal", this.limitedEditionItemData.getLimitedRareTotal());
        }

        jsonObject.add("limitedEditionItemData", this.limitedEditionItemData == null ? null : limitedItemData);

        return jsonObject;
    }

    /**
     * Returns the id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the item definition id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemDefinitionId() {
        return itemDefinitionId;
    }

    /**
     * Returns the room id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Returns the owner for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Returns the owner name for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Returns the data for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getData() {
        return data;
    }

    /**
     * Returns the limited edition item data for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public LimitedEditionItemData getLimitedEditionItemData() {
        return limitedEditionItemData;
    }
}
