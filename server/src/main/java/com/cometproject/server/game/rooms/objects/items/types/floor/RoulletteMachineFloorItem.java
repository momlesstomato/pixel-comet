package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;

import java.util.Arrays;

/**
 * Describes roullette machine floor item behavior for the room subsystem.
 */
public class RoulletteMachineFloorItem extends RoomItemFloor {
    private PlayerEntity playerEntity = null;
    private boolean isOnBet = false;
    private int betColor = 0;
    /**
     * Creates a roullette machine floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public RoulletteMachineFloorItem(RoomItemData itemData, Room room) {
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
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if(entity instanceof PlayerEntity){
            if(Arrays.asList(this.getRoom().getData().getTags()).contains("69")){
                if(((PlayerEntity) entity).getPlayer().getData().itemOnBet != this){
                    ((PlayerEntity)entity).getPlayer().getData().itemOnBet = this;
                    ((PlayerEntity)entity).getPlayer().getData().hasPaidBet = false;
                    ((PlayerEntity) entity).getPlayer().getSession().send(new MassEventMessageComposer("casino/rouletteFloor"));

                    return false;
                }
            }
        }

        return false;
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
    }
}