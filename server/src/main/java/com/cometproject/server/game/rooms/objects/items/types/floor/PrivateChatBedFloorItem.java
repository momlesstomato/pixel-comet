package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Describes private chat bed floor item behavior for the room subsystem.
 */
public class PrivateChatBedFloorItem extends DefaultFloorItem {

    private List<PlayerEntity> entities = Lists.newArrayList();

    /**
     * Creates a private chat bed floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public PrivateChatBedFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity) || this.entities.contains(entity)) return;

        entity.setPrivateChatItemId(this.getId());
        this.entities.add((PlayerEntity) entity);

        entity.setBodyRotation(this.getRotation());
        entity.setHeadRotation(this.getRotation());
        entity.addStatus(RoomEntityStatus.LAY, this.getDefinition().getHeight() + "");
        entity.markNeedsUpdate();
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity)) return;

        entity.setPrivateChatItemId(0);
        this.entities.remove(entity);
        entity.removeStatus(RoomEntityStatus.LAY);
        entity.markNeedsUpdate();
    }

    /**
     * Executes broadcast message for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void broadcastMessage(MessageComposer msg) {
        for (PlayerEntity playerEntity : this.entities) {
            playerEntity.getPlayer().getSession().send(msg);
        }
    }
}
