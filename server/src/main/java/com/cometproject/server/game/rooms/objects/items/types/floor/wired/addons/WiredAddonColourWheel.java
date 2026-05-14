package com.cometproject.server.game.rooms.objects.items.types.floor.wired.addons;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.utilities.RandomUtil;


/**
 * Describes wired addon colour wheel behavior for the room subsystem.
 */
public class WiredAddonColourWheel extends RoomItemFloor {
    private static final int TIMEOUT = 4;

    /**
     * Creates a wired addon colour wheel instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredAddonColourWheel(RoomItemData itemData, Room room) {
        super(itemData, room);

        this.getItemData().setData("0");
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
        if (!isWiredTrigger && entity != null) {
            if (!this.getPosition().touching(entity.getPosition())) {
                entity.moveTo(this.getPosition().squareBehind(this.getRotation()).getX(), this.getPosition().squareBehind(this.getRotation()).getY());
                return true;
            }
        }

        this.getItemData().setData("9");
        this.sendUpdate();

        this.setTicks(RoomItemFactory.getProcessTime(TIMEOUT / 2));
        return true;
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        final int randomInteger = RandomUtil.getRandomInt(1, 8);

        this.getItemData().setData(randomInteger + "");
        this.sendUpdate();
    }
}
