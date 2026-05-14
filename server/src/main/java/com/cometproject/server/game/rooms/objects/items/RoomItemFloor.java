package com.cometproject.server.game.rooms.objects.items;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.rooms.objects.IFloorItem;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.AffectedTile;
import com.cometproject.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.AdjustableHeightFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.MagicStackFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.SoundMachineFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;
import com.cometproject.server.utilities.attributes.Collidable;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;


/**
 * Describes room item floor behavior for the room subsystem.
 */
public abstract class RoomItemFloor extends RoomItem implements Collidable, IFloorItem {
    private FurnitureDefinition itemDefinition;
    private RoomEntity collidedEntity;
    private boolean hasQueuedSave;
    private String coreState;
    private boolean stateSwitched = false;

    /**
     * Creates a room item floor instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public RoomItemFloor(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes serialize for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param isNew Is new supplied by the caller.
     */
    public void serialize(IComposer msg, boolean isNew) {
        msg.writeInt(this.getVirtualId());
        msg.writeInt(this.getDefinition().getSpriteId());
        msg.writeInt(this.getPosition().getX());
        msg.writeInt(this.getPosition().getY());
        msg.writeInt(this.getRotation());

        msg.writeString(this instanceof MagicStackFloorItem ? this.getItemData().getData() : this.getPosition().getZ());

        final double walkHeight = this instanceof AdjustableHeightFloorItem ? this.getOverrideHeight() : this.getDefinition().getHeight();
        msg.writeString(walkHeight);

        if (this.getLimitedEditionItemData() != null) {
            msg.writeInt(0);
            msg.writeString("");
            msg.writeBoolean(true);
            msg.writeBoolean(false);
            msg.writeString(this.getItemData().getData());

            msg.writeInt(this.getLimitedEditionItemData().getLimitedRare());
            msg.writeInt(this.getLimitedEditionItemData().getLimitedRareTotal());
        } else {
            this.composeItemData(msg);
        }

        msg.writeInt(-1);
        //msg.writeInt(!this.getDefinition().getInteraction().equals("default") ? 1 : 0);
        msg.writeInt(!(this instanceof DefaultFloorItem) && !(this instanceof SoundMachineFloorItem) ? 1 : 0);
        msg.writeInt(this.getItemData().getOwnerId());

        if (isNew)
            msg.writeString(this.getItemData().getOwnerName());
    }

    /**
     * Executes serialize for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void serialize(IComposer msg) {
        this.serialize(msg, false);
    }

    /**
     * Returns the definition for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public FurnitureDefinition getDefinition() {
        if (this.itemDefinition == null) {
            this.itemDefinition = ItemManager.getInstance().getDefinition(this.getItemData().getItemId());
        }

        return this.itemDefinition;
    }

    /**
     * Handles the item added to stack callback for this room contract.
     *
     * @param floorItem Floor item supplied by the caller.
     */
    public void onItemAddedToStack(RoomItemFloor floorItem) {
        // override me
    }

    /**
     * Handles the entity pre step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void onEntityPreStepOn(RoomEntity entity) {
        // override me
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void onEntityStepOn(RoomEntity entity) {
        // override me
    }

    /**
     * Handles the entity post step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void onEntityPostStepOn(RoomEntity entity) {
        // override me
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void onEntityStepOff(RoomEntity entity) {
        // override me
    }

    /**
     * Handles the position changed callback for this room contract.
     *
     * @param newPosition New position supplied by the caller.
     */
    public void onPositionChanged(Position newPosition) {
        // override me
    }

    /**
     * Indicates whether movement cancelled applies to this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isMovementCancelled(RoomEntity entity) {
        return false;
    }

    /**
     * Indicates whether movement cancelled applies to this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param position Position supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isMovementCancelled(RoomEntity entity, Position position) {
        return this.isMovementCancelled(entity);
    }

    /**
     * Executes save for this room contract.
     */
    @Override
    public void save() {
        this.getItemData().setData(this.getDataObject());
        this.getRoom().getItemProcess().saveItem(this);
//        StorageContext.getCurrentContext().getRoomItemRepository().saveItem(this.getItemData());
    }

    /**
     * Persists data for this room contract.
     */
    @Override
    public void saveData() {
        this.save();
    }

    /**
     * Executes send update for this room contract.
     */
    @Override
    public void sendUpdate() {
        Room r = this.getRoom();

        if (r != null) {
            r.getEntities().broadcastMessage(new UpdateFloorItemMessageComposer(this));
        }
    }

    /**
     * Executes temp state for this room contract.
     *
     * @param state State supplied by the caller.
     */
    public void tempState(int state) {
        this.stateSwitched = true;
        this.coreState = this.getItemData().getData();

        this.getItemData().setData(state);
        this.sendUpdate();
    }

    /**
     * Executes restore state for this room contract.
     */
    public void restoreState() {
        this.stateSwitched = false;

        this.getItemData().setData(coreState);
        this.sendUpdate();
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDataObject() {
        return this.getItemData().getData();
    }

    /**
     * Returns the items on stack for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<RoomItemFloor> getItemsOnStack() {
        List<RoomItemFloor> floorItems = Lists.newArrayList();

        List<AffectedTile> affectedTiles = AffectedTile.getAffectedTilesAt(
                this.getDefinition().getLength(), this.getDefinition().getWidth(), this.getPosition().getX(), this.getPosition().getY(), this.getRotation());

        floorItems.addAll(this.getRoom().getItems().getItemsOnSquare(this.getPosition().getX(), this.getPosition().getY()));

        for (AffectedTile tile : affectedTiles) {
            for (RoomItemFloor floorItem : this.getRoom().getItems().getItemsOnSquare(tile.x, tile.y)) {
                if (!floorItems.contains(floorItem)) floorItems.add(floorItem);
            }
        }

        return floorItems;
    }

    /**
     * Returns the entities on item for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<RoomEntity> getEntitiesOnItem() {
        List<RoomEntity> entities = Lists.newArrayList();

        entities.addAll(this.getRoom().getEntities().getEntitiesAt(this.getPosition()));

        for (AffectedTile affectedTile : AffectedTile.getAffectedTilesAt(this.getDefinition().getLength(), this.getDefinition().getWidth(), this.getPosition().getX(), this.getPosition().getY(), this.getRotation())) {
            List<RoomEntity> entitiesOnTile = this.getRoom().getEntities().getEntitiesAt(new Position(affectedTile.x, affectedTile.y));

            entities.addAll(entitiesOnTile);
        }

        return entities;
    }

    /**
     * Returns the partner tile for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getPartnerTile() {
        if (this.getDefinition().getLength() != 2) return null;

        for (AffectedTile affTile : AffectedTile.getAffectedBothTilesAt(this.getDefinition().getLength(), this.getDefinition().getWidth(), this.getPosition().getX(), this.getPosition().getY(), this.getRotation())) {
            if (affTile.x == this.getPosition().getX() && affTile.y == this.getPosition().getY()) continue;

            return new Position(affTile.x, affTile.y);
        }

        return null;
    }

    /**
     * Returns the positions for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public ArrayList<Position> getPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(new Position(getPosition().getX(), getPosition().getY()));
        for (AffectedTile affectedTile : AffectedTile.getAffectedTilesAt(getDefinition().getLength(), getDefinition().getWidth(), getPosition().getX(), getPosition().getY(), getRotation())) {
            Position position = new Position(affectedTile.x, affectedTile.y);
            if (getRoom().getMapping().isValidPosition(position) &&
                    !positions.contains(position))
                positions.add(position);
        }
        return positions;
    }

    /**
     * Returns the collision for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntity getCollision() {
        return this.collidedEntity;
    }

    /**
     * Updates the collision for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void setCollision(RoomEntity entity) {
        this.collidedEntity = entity;
    }

    /**
     * Executes nullify collision for this room contract.
     */
    public void nullifyCollision() {
        this.collidedEntity = null;
    }

    /**
     * Returns the override height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getOverrideHeight() {
        return -1d;
    }

    /**
     * Indicates whether this room contract has queued save.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasQueuedSave() {
        return hasQueuedSave;
    }

    /**
     * Updates the has queued save for this room contract.
     *
     * @param hasQueuedSave Has queued save supplied by the caller.
     */
    public void setHasQueuedSave(boolean hasQueuedSave) {
        this.hasQueuedSave = hasQueuedSave;
    }

    /**
     * Indicates whether state switched applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isStateSwitched() {
        return stateSwitched;
    }

    /**
     * Updates the state switched for this room contract.
     *
     * @param stateSwitched State switched supplied by the caller.
     */
    public void setStateSwitched(boolean stateSwitched) {
        this.stateSwitched = stateSwitched;
    }

    /**
     * Returns the rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRotation() {
        return this.getItemData().getRotation();
    }

    /**
     * Updates the rotation for this room contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    public void setRotation(int rotation) {
        this.getItemData().setRotation(rotation);
    }
}
