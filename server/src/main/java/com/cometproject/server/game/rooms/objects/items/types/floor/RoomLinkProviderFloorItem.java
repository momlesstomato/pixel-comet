package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.GetGuestRoomResultMessageComposer;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes room link provider floor item behavior for the room subsystem.
 */
public class RoomLinkProviderFloorItem extends RoomItemFloor {
    /**
     * Creates a room link provider floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public RoomLinkProviderFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced(){
        this.getItemData().setData("state0internalLink0");
        this.saveData();
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        if(!(entity instanceof PlayerEntity)) return;

        PlayerEntity playerEntity = ((PlayerEntity) entity);
        playerEntity.getPlayer().getSession().send(new GetGuestRoomResultMessageComposer(Integer.parseInt(this.getExtradataInfo().get(1))));

    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(1);
        msg.writeString("internalLink");
        msg.writeString(this.getItemData().getData().split("internalLink")[1].replace("\t", ""));
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
        if(!(entity instanceof PlayerEntity))
            return false;

        PlayerEntity playerEntity = ((PlayerEntity) entity);
        playerEntity.getPlayer().getSession().send(new GetGuestRoomResultMessageComposer(Integer.parseInt(this.getExtradataInfo().get(1))));

        return false;
    }

    private List<String> getExtradataInfo() {
        List<String> values = new ArrayList<>();

        for(String value : this.getItemData().getData().replace("state", "").split("internalLink")) {
            values.add(value.trim());
        }

        return values;
    }
}