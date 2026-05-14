package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes one way gate floor item behavior for the room subsystem.
 */
public class OneWayGateFloorItem extends RoomItemFloor {
    private boolean isInUse = false;
    private RoomEntity interactingEntity;

    /**
     * Creates a one way gate floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public OneWayGateFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (this.isInUse) {
            return false;
        }

        if (this.getPosition().squareInFront(this.getRotation()).getX() != entity.getPosition().getX() || this.getPosition().squareInFront(this.getRotation()).getY() != entity.getPosition().getY()) {
            entity.moveTo(this.getPosition().squareInFront(this.getRotation()).getX(), this.getPosition().squareInFront(this.getRotation()).getY());
            return false;
        }

        Position squareBehind = this.getPosition().squareBehind(this.getItemData().getRotation());

        if (!this.getRoom().getMapping().isValidStep(this.getItemData().getPosition(), squareBehind, true)) {
            return false;
        }

        this.isInUse = true;

        entity.setOverriden(true);
        entity.moveTo(squareBehind.getX(), squareBehind.getY());

        this.getItemData().setData("1");
        this.sendUpdate();

        this.interactingEntity = entity;
        this.setTicks(RoomItemFactory.getProcessTime(1.0));

        return true;
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        entity.setIsOneGate(true);
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        entity.setIsOneGate(false);
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        if (this.isInUse) {
            this.interactingEntity.setOverriden(false);
            this.setTicks(RoomItemFactory.getProcessTime(1.0));
        }

        this.getItemData().setData("0");
        this.sendUpdate();

        this.isInUse = false;
        this.interactingEntity = null;
    }

    /**
     * Returns the interacting entity for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntity getInteractingEntity() {
        return interactingEntity;
    }
}
