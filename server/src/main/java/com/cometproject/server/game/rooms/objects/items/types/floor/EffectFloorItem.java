package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes effect floor item behavior for the room subsystem.
 */
public class EffectFloorItem extends RoomItemFloor {
    private final int effectId;

    /**
     * Creates a effect floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public EffectFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        this.effectId = this.getDefinition().getEffectId();
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return;
        }

        if (entity.getCurrentEffect() != null && entity.getCurrentEffect().getEffectId() == this.effectId) {
            entity.applyEffect(null);
        } else {
            entity.applyEffect(new PlayerEffect(this.effectId, 0));
        }
    }
}
