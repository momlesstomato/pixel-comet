package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarUpdateMessageComposer;


/**
 * Describes seat floor item behavior for the room subsystem.
 */
public class SeatFloorItem extends RoomItemFloor {

    /**
     * Creates a seat floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public SeatFloorItem(RoomItemData itemData, Room room) {
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

        this.toggleInteract(true);
        this.sendUpdate();

        if (this instanceof AdjustableHeightSeatFloorItem) {
            for (RoomEntity sitter : this.getEntitiesOnItem()) {
                this.onEntityStepOn(sitter, true);
            }
        }

        this.saveData();
        return true;
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param instantUpdate Instant update supplied by the caller.
     */
    public void onEntityStepOn(RoomEntity entity, boolean instantUpdate) {
        double height = (entity instanceof PetEntity || entity.hasAttribute("transformation")) ? this.getSitHeight() / 2 : this.getSitHeight();

        entity.setBodyRotation(this.getRotation());
        entity.setHeadRotation(this.getRotation());
        entity.addStatus(RoomEntityStatus.SIT, String.valueOf(height).replace(',', '.'));

        if (instantUpdate)
            this.getRoom().getEntities().broadcastMessage(new AvatarUpdateMessageComposer(entity));
        else
            entity.markNeedsUpdate();
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        this.onEntityStepOn(entity, false);
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        if (entity.hasStatus(RoomEntityStatus.SIT)) {
            entity.removeStatus(RoomEntityStatus.SIT);
        }

        entity.markNeedsUpdate();
    }

    /**
     * Returns the sit height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getSitHeight() {
        return this.getDefinition().getHeight();
    }
}
