package com.cometproject.server.game.rooms.objects.items.types.floor.wired.addons;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.utilities.RandomUtil;


/**
 * Describes wired addon pyramid behavior for the room subsystem.
 */
public class WiredAddonPyramid extends RoomItemFloor {
    private boolean hasEntity = false;

    /**
     * Creates a wired addon pyramid instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredAddonPyramid(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.setTicks(RandomUtil.getRandomInt(5, 8) * 2);
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        this.hasEntity = true;
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        this.hasEntity = false;
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        if (this.hasEntity) {
            this.setTicks(RoomItemFactory.getProcessTime(1.0));
            return;
        }

        if (this.getItemData().getData().equals("1")) {
            this.getItemData().setData("0");
        } else {
            this.getItemData().setData("1");
        }

        this.sendUpdate();
        this.setTicks(RoomItemFactory.getProcessTime(RandomUtil.getRandomInt(5, 8)));

        this.getRoom().getMapping().updateTile(this.getPosition().getX(), this.getPosition().getY());
    }
}
