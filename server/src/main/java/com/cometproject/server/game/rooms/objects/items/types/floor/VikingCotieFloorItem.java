package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes viking cotie floor item behavior for the room subsystem.
 */
public class VikingCotieFloorItem extends RoomItemFloor {
    /**
     * Creates a viking cotie floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public VikingCotieFloorItem (RoomItemData itemData, Room room) {
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

        if (this.getPosition().distanceTo(new Position(entity.getPosition().getX(), entity.getPosition().getY())) > 3) {
            entity.moveTo(this.getPosition().squareInFront(this.getRotation()).getX(), this.getPosition().squareInFront(this.getRotation()).getY());
            return false;
        }

        if(p.getCurrentEffect().getEffectId() == 5) {
            int count = Integer.parseInt(this.getItemData().getData());

            if(count < 5){
                count++;
                this.getItemData().setData(count);
                p.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_65, 1);
            }

            this.sendUpdate();
            this.saveData();
        }
        return true;
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
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
    }
}
