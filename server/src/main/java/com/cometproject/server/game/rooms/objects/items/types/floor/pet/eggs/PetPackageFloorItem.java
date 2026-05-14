package com.cometproject.server.game.rooms.objects.items.types.floor.pet.eggs;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.pets.PetPackageMessageComposer;

/**
 * Describes pet package floor item behavior for the room subsystem.
 */
public abstract class PetPackageFloorItem extends RoomItemFloor {

    /**
     * Creates a pet package floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public PetPackageFloorItem(RoomItemData itemData, Room room) {
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
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (isWiredTrigger) {
            return false;
        }

        // send petpackage msg
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }

        final PlayerEntity playerEntity = (PlayerEntity) entity;

        playerEntity.getPlayer().getSession().send(new PetPackageMessageComposer(this.getVirtualId()));
        return true;
    }

    /**
     * Returns the pet type id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getPetTypeId();

    /**
     * Returns the race id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getRaceId();
}
