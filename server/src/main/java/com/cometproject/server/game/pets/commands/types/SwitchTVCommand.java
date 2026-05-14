package com.cometproject.server.game.pets.commands.types;

import com.cometproject.server.game.pets.commands.PetCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.mapping.RoomEntityMovementNode;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.tasks.CometThreadManager;

import java.util.concurrent.TimeUnit;

/**
 * Describes switch tv command behavior for the pet subsystem.
 */
public class SwitchTVCommand extends PetCommand {
    /**
     * Executes execute for this pet contract.
     *
     * @param executor Executor supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean execute(PlayerEntity executor, PetEntity entity) {
        // find an item.
        RoomItemFloor tv = null;

        for (RoomItemFloor floor : entity.getRoom().getItems().getFloorItems().values()) {
            if (floor.getDefinition().getItemName().endsWith("_tv")) {
                tv = floor;
                break;
            }
        }

        if (tv == null) {
            return false;
        }

        final RoomItemFloor television = tv;
        final RoomTile tileInFront = entity.getRoom().getMapping().getTile(tv.getPosition().squareInFront(tv.getRotation()));

        if (tileInFront != null && tileInFront.getMovementNode() == RoomEntityMovementNode.OPEN) {
            entity.moveTo(tileInFront.getPosition());
            tileInFront.scheduleEvent(entity.getId(), (e) -> {
                e.lookTo(television.getPosition().getX(), television.getPosition().getY());

                CometThreadManager.getInstance().executeSchedule(() -> {
                    television.toggleInteract(true);
                    television.sendUpdate();
                }, 1000l, TimeUnit.MILLISECONDS);
            });
        }

        return true;
    }

    /**
     * Returns the required level for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRequiredLevel() {
        return 0;
    }

    /**
     * Executes requires owner for this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean requiresOwner() {
        return true;
    }
}
