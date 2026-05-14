package com.cometproject.server.game.rooms.objects.items.types.floor.pet.eggs;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.pets.races.PetType;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.utilities.RandomUtil;

/**
 * Describes velociraptor egg floor item behavior for the room subsystem.
 */
public class VelociraptorEggFloorItem extends PetPackageFloorItem {

    /**
     * Creates a velociraptor egg floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public VelociraptorEggFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the pet type id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getPetTypeId() {
        return PetType.VELOCIRAPTOR;
    }

    /**
     * Returns the race id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRaceId() {
        return RandomUtil.getRandomInt(1, 20);
    }
}
