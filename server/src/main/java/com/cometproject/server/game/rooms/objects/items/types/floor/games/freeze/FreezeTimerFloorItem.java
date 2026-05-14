package com.cometproject.server.game.rooms.objects.items.types.floor.games.freeze;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.AbstractGameTimerFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredUtil;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameType;

import java.util.List;

/**
 * Describes freeze timer floor item behavior for the room subsystem.
 */
public class FreezeTimerFloorItem extends AbstractGameTimerFloorItem {
    private int time = 0;
    public static boolean running = false;

    /**
     * Creates a freeze timer floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FreezeTimerFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes send update for this room contract.
     */
    @Override
    public void sendUpdate() {
        this.time = Integer.parseInt(this.getItemData().getData());

        super.sendUpdate();
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTriggered Is wired triggered supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTriggered) {
        if (!isWiredTriggered) {
            if (!(entity instanceof PlayerEntity)) {
                return false;
            }

            PlayerEntity pEntity = (PlayerEntity) entity;

            if (!pEntity.getRoom().getRights().hasRights(pEntity.getPlayerId())
                    && !pEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                return true;
            }
        }

        int time = Integer.parseInt(this.getItemData().getData());
        this.time = time;

        if (requestData != 1) {

            if (time == 0 || time == 30 || time == 60 || time == 120 || time == 180 || time == 300 || time == 600) {
                switch (time) {
                    default:
                        time = 0;
                        break;
                    case 0:
                        time = 30;
                        break;
                    case 30:
                        time = 60;
                        break;
                    case 60:
                        time = 120;
                        break;
                    case 120:
                        time = 180;
                        break;
                    case 180:
                        time = 300;
                        break;
                    case 300:
                        time = 600;
                        break;
                }
            } else {
                time = 0;
            }

            this.time = time;
            this.getItemData().setData(this.time + "");
            this.sendUpdate();
        } else {
            this.setTicks(RoomItemFactory.getProcessTime(1.0));
        }
        return true;
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        running = this.time > 0;

        if (running) {
            this.time--;

            this.getItemData().setData(this.time + "");
            this.sendUpdate();

            this.setTicks(RoomItemFactory.getProcessTime(1.0));
        } else {
            this.time = 0;
            List<FreezeTileFloorItem> freezeTileFloorItems = this.getRoom().getItems().getByClass(FreezeTileFloorItem.class);
            List<FreezeExitFloorItem> freezeExitFloorItems = this.getRoom().getItems().getByClass(FreezeExitFloorItem.class);

            for (FreezeTileFloorItem freezeItem : freezeTileFloorItems) {
                FreezeExitFloorItem freezeExitFloorItem = WiredUtil.getRandomElement(freezeExitFloorItems);
                if (freezeItem.getEntitiesOnItem().size() > 0 && freezeExitFloorItems.size() > 0) {
                    for (RoomEntity roomEntity : freezeItem.getEntitiesOnItem()) {
                        roomEntity.teleportToItem(freezeExitFloorItem);
                    }
                }
            }
        }
    }

    /**
     * Returns the game type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public GameType getGameType() {
        return GameType.FREEZE;
    }
}
