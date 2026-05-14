package com.cometproject.server.game.rooms.objects.items.types.floor.wired.addons;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * Describes wired addon unseen effect behavior for the room subsystem.
 */
public class WiredAddonUnseenEffect extends RoomItemFloor {
    private List<Long> seenEffects;

    /**
     * Creates a wired addon unseen effect instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredAddonUnseenEffect(RoomItemData itemData, Room room) {
        super(itemData, room);

        this.seenEffects = Lists.newArrayList();
    }

    /**
     * Returns the seen effects for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Long> getSeenEffects() {
        return seenEffects;
    }
}
