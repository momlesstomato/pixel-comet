package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.data.BackgroundTonerData;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;


/**
 * Describes background toner floor item behavior for the room subsystem.
 */
public class BackgroundTonerFloorItem extends RoomItemFloor {
    /**
     * Creates a background toner floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BackgroundTonerFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        BackgroundTonerData data = BackgroundTonerData.get(this.getItemData().getData());

        boolean enabled = (data != null);

        msg.writeInt(0);
        msg.writeInt(5);
        msg.writeInt(4);
        msg.writeInt(enabled ? 1 : 0);

        if (enabled) {
            msg.writeInt(data.getHue());
            msg.writeInt(data.getSaturation());
            msg.writeInt(data.getLightness());
        } else {
            this.getItemData().setData("0;#;0;#;0");
            this.saveData();

            msg.writeInt(0);
            msg.writeInt(0);
            msg.writeInt(0);
        }
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
        this.getItemData().setData("");
        this.saveData();
        this.getRoom().getEntities().broadcastMessage(new UpdateFloorItemMessageComposer(this));

        return true;
    }
}
