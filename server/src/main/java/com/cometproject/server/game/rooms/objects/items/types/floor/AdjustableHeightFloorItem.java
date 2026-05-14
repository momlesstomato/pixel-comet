package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarUpdateMessageComposer;
import org.apache.commons.lang3.StringUtils;


/**
 * Describes adjustable height floor item behavior for the room subsystem.
 */
public class AdjustableHeightFloorItem extends RoomItemFloor {
    /**
     * Creates a adjustable height floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public AdjustableHeightFloorItem(RoomItemData itemData, Room room) {
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
        if (!isWiredTrigger) {
            if (!(entity instanceof PlayerEntity)) {
                return false;
            }

            PlayerEntity pEntity = (PlayerEntity) entity;

            if (!pEntity.getRoom().getRights().hasRights(pEntity.getPlayerId())
                    && !pEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                return false;
            }
        }

        for (RoomItemFloor floorItem : this.getItemsOnStack()) {
            if (floorItem.getId() != this.getId() && floorItem.getPosition().getZ() >= this.getPosition().getZ())
                return false;
        }

        final double oldHeight = this.getOverrideHeight();

        this.toggleInteract(true);
        this.sendUpdate();

        double newHeight = this.getOverrideHeight();

        for (RoomEntity entityOnItem : this.getEntitiesOnItem()) {
            if (entityOnItem.hasStatus(RoomEntityStatus.SIT)) {
                entityOnItem.removeStatus(RoomEntityStatus.SIT);
            }

            double entityHeight = (newHeight > oldHeight) ? entityOnItem.getPosition().getZ() + (newHeight - oldHeight) : this.getTile().getTileHeight();

            entityOnItem.setPosition(new Position(entityOnItem.getPosition().getX(), entityOnItem.getPosition().getY(), entityHeight));
            this.getRoom().getEntities().broadcastMessage(new AvatarUpdateMessageComposer(entityOnItem));
        }

        this.saveData();
        return true;
    }

    /**
     * Returns the override height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public double getOverrideHeight() {
        if (this.getDefinition().getVariableHeights() != null && !this.getItemData().getData().isEmpty()) {
            if (!StringUtils.isNumeric(this.getItemData().getData())) {
                return 0;
            }

            int heightIndex = Integer.parseInt(this.getItemData().getData());

            if (heightIndex >= this.getDefinition().getVariableHeights().length) {
                return 0;
            }

            return this.getDefinition().getVariableHeights()[heightIndex];
        } else if (this.getDefinition().getVariableHeights() != null && this.getDefinition().getVariableHeights().length != 0) {
            return this.getDefinition().getVariableHeights()[0];
        } else {
            if (this.getItemData().getData().isEmpty() || !StringUtils.isNumeric(this.getItemData().getData())) {
                return 0.5;
            } else {
                return Double.parseDouble(this.getItemData().getData());
            }
        }
    }
}
