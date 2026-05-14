package com.cometproject.server.game.rooms.objects.items;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.UpdateWallItemMessageComposer;
import com.cometproject.storage.api.StorageContext;

/**
 * Describes room item wall behavior for the room subsystem.
 */
public abstract class RoomItemWall extends RoomItem {
    private FurnitureDefinition itemDefinition;

    private String wallPosition;

    /**
     * Creates a room item wall instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public RoomItemWall(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.wallPosition = roomItemData.getWallPosition();
    }

    /**
     * Executes serialize for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void serialize(IComposer msg) {
        msg.writeString(this.getVirtualId());
        msg.writeInt(this.getDefinition().getSpriteId());
        msg.writeString(this.getWallPosition());

        msg.writeString(this.getState());
        msg.writeInt(!this.getDefinition().getInteraction().equals("default") ? 1 : 0);
        msg.writeInt(-1);
        msg.writeInt(-1);

        //msg.writeInt(this.getRoom().getData().getOwnerId());
        msg.writeInt(1);
    }

    /**
     * Returns the state for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getState() {
        return this.getItemData().getData();
    }

    /**
     * Executes send update for this room contract.
     */
    @Override
    public void sendUpdate() {
        Room r = this.getRoom();

        if (r != null && r.getEntities() != null) {
            r.getEntities().broadcastMessage(new UpdateWallItemMessageComposer(this, this.getItemData().getOwnerId(), this.getRoom().getData().getOwner()));
        }
    }

    /**
     * Executes save for this room contract.
     */
    public void save() {
        this.saveData();
    }

    /**
     * Persists data for this room contract.
     */
    @Override
    public void saveData() {
        StorageContext.getCurrentContext().getRoomItemRepository().saveData(this.getId(), this.getItemData().getData());
    }

    /**
     * Returns the definition for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public FurnitureDefinition getDefinition() {
        if (this.itemDefinition == null) {
            this.itemDefinition = ItemManager.getInstance().getDefinition(this.getItemData().getItemId());
        }

        return this.itemDefinition;
    }

    /**
     * Returns the wall position for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getWallPosition() {
        return this.wallPosition;
    }

    /**
     * Updates the wall position for this room contract.
     *
     * @param wallPosition Wall position supplied by the caller.
     */
    public void setWallPosition(String wallPosition) {
        this.wallPosition = wallPosition;
    }

    /**
     * Returns the rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRotation() {
        return 0;
    }

    /**
     * Updates the rotation for this room contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    public void setRotation(int rotation) {
    }
}
