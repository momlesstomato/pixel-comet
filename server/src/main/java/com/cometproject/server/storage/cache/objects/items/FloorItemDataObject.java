package com.cometproject.server.storage.cache.objects.items;

import com.cometproject.api.game.rooms.objects.data.LimitedEditionItemData;
import com.cometproject.api.game.utilities.Position;
import com.google.gson.JsonObject;

/**
 * Describes floor item data object behavior for the storage subsystem.
 */
public class FloorItemDataObject extends RoomItemDataObject {

    private final Position position;
    private final int rotation;

    /**
     * Creates a floor item data object instance for the storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param itemDefinitionId Item definition id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param owner Owner supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param data Data supplied by the caller.
     * @param position Position supplied by the caller.
     * @param rotation Rotation supplied by the caller.
     * @param limitedEditionItemData Limited edition item data supplied by the caller.
     */
    public FloorItemDataObject(long id, int itemDefinitionId, int roomId, int owner, String ownerName, String data, Position position, int rotation, LimitedEditionItemData limitedEditionItemData) {
        super(id, itemDefinitionId, roomId, owner, ownerName, data, limitedEditionItemData);

        this.position = position;
        this.rotation = rotation;
    }

    /**
     * Executes to JSON object for this storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonObject toJsonObject() {
        final JsonObject object = super.toJsonObject();
        final JsonObject positionObject = new JsonObject();

        positionObject.addProperty("x", this.position.getX());
        positionObject.addProperty("y", this.position.getY());
        positionObject.addProperty("z", this.position.getZ());

        object.add("position", positionObject);
        object.addProperty("rotation", this.rotation);

        return object;
    }

    /**
     * Returns the position for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the rotation for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRotation() {
        return rotation;
    }
}
