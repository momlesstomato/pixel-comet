package com.cometproject.server.game.rooms.objects.items.types.floor.groups;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes group gate floor item behavior for the room subsystem.
 */
public class GroupGateFloorItem extends GroupFloorItem {
    public boolean isOpen = false;

    /**
     * Creates a group gate floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public GroupGateFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.isOpen = false;
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        this.isOpen = true;
        this.sendUpdate();
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        this.setTicks(RoomItemFactory.getProcessTime(0.5));
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        if (this.getTile().getEntities().size() != 0) {
            return;
        }

        this.isOpen = false;
        this.sendUpdate();
    }

    /**
     * Indicates whether movement cancelled applies to this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isMovementCancelled(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return true;
        }

        final PlayerEntity playerEntity = (PlayerEntity) entity;


        return !playerEntity.getPlayer().getGroups().contains(this.getGroupId()) && playerEntity.getPlayer().getData().getRank() < 4;

    }
}
