package com.cometproject.server.game.rooms.objects.items.types.floor.pet.breeding.types;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.pets.races.PetType;
import com.cometproject.server.game.rooms.objects.items.types.floor.pet.breeding.BreedingBoxFloorItem;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes bear breeding box floor item behavior for the room subsystem.
 */
public class BearBreedingBoxFloorItem extends BreedingBoxFloorItem {
    /**
     * Creates a bear breeding box floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BearBreedingBoxFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the baby type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    protected int getBabyType() {
        return PetType.BABYBEAR;
    }

    /**
     * Returns the pet type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    protected int getPetType() {
        return PetType.BEAR;
    }
}
