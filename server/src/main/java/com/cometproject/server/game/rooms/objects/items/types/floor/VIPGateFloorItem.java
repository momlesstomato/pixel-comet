package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.composers.group.GroupEditErrorMessageComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes VIP gate floor item behavior for the room subsystem.
 */
public class VIPGateFloorItem extends RoomItemFloor {
    public boolean isOpen = false;

    /**
     * Creates a VIP gate floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public VIPGateFloorItem(RoomItemData roomItemData, Room room) {
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
        this.getItemData().setData(this.isOpen ? 1 : 0);
        this.sendUpdate();
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        this.setTicks(RoomItemFactory.getProcessTime(1.0));
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
        this.getItemData().setData(this.isOpen ? 1 : 0);
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

        boolean canWalk = (!playerEntity.getPlayer().getSubscription().isValid());

        if(canWalk){
            playerEntity.getPlayer().getSession().send(new GroupEditErrorMessageComposer(2));
        }

        return canWalk;
    }
}
