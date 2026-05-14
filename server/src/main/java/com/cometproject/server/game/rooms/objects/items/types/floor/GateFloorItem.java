package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.AffectedTile;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes gate floor item behavior for the room subsystem.
 */
public class GateFloorItem extends RoomItemFloor {
    /**
     * Creates a gate floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public GateFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity0 Entity0 supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity0, int requestData, boolean isWiredTrigger) {
        if (!isWiredTrigger) {
            if (!(entity0 instanceof PlayerEntity)) {
                return false;
            }

            PlayerEntity pEntity = (PlayerEntity) entity0;

            if (!pEntity.getRoom().getRights().hasRights(pEntity.getPlayerId())
                    && !pEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                return false;
            }
        }

        for (AffectedTile tile : AffectedTile.getAffectedTilesAt(this.getDefinition().getLength(), this.getDefinition().getWidth(), this.getPosition().getX(), this.getPosition().getY(), this.getRotation())) {
            if (this.getRoom().getEntities().getEntitiesAt(new Position(tile.x, tile.y)).size() > 0) {
                return false;
            }
        }

        if (this.getRoom().getEntities().getEntitiesAt(this.getPosition()).size() > 0) {
            return false;
        }

        for (RoomEntity entity : this.getRoom().getEntities().getAllEntities().values()) {
            if (this.getPosition().distanceTo(entity.getPosition()) <= 1 && entity.isWalking()) {
                return false;
            }
        }

        this.toggleInteract(true);
        this.sendUpdate();

        this.saveData();
        return true;
    }

    /**
     * Indicates whether movement cancelled applies to this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isMovementCancelled(RoomEntity entity) {
        return !this.isOpen();
    }

    /**
     * Indicates whether open applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOpen() {
        return !this.getItemData().getData().equals("0");
    }
}
