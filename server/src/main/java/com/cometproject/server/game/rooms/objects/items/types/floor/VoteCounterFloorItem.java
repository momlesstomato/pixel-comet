package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes vote counter floor item behavior for the room subsystem.
 */
public class VoteCounterFloorItem extends RoomItemFloor {
    /**
     * Creates a vote counter floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public VoteCounterFloorItem (RoomItemData itemData, Room room) {
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
        if (isWiredTrigger) {
            return false;
        }

        PlayerEntity p = ((PlayerEntity) entity);

        if(p.hasRights()){
            this.getItemData().setData("0");
            this.sendUpdate();
            this.saveData();
            return true;
        }

        return false;
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        this.getItemData().setData("0");
        this.sendUpdate();
        this.saveData();
    }

    /**
     * Handles votation for this room contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param score Score supplied by the caller.
     */
    public void handleVotation(int playerId, int score){
        int result = Integer.parseInt(this.getItemData().getData()) + score;

        this.getItemData().setData(result + "");
        this.getItemData().setData(Math.min(result, 999) + "");
        this.saveData();

        super.sendUpdate();
    }

}
