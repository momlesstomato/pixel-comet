package com.cometproject.server.game.pets.commands.types;

import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.pets.commands.PetCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;

/**
 * Describes here command behavior for the pet subsystem.
 */
public class HereCommand extends PetCommand {
    /**
     * Executes execute for this pet contract.
     *
     * @param executor Executor supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean execute(PlayerEntity executor, PetEntity entity) {
        Position position = executor.getPosition().squareInFront(executor.getBodyRotation());

        entity.moveTo(position);

        executor.getRoom().getMapping().getTile(position.getX(), position.getY()).scheduleEvent(entity.getId(), (e) -> {
            if (e instanceof PetEntity) {
                ((PetEntity) e).getPetAI().sit();
                ((PetEntity) e).getPetAI().applyGesture("sml");

                ((PetEntity) e).getPetAI().increaseExperience(this.experienceGain());
            }


            e.lookTo(executor.getPosition().getX(), executor.getPosition().getY());
        });

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

    /**
     * Executes experience gain for this pet contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int experienceGain() {
        return 10;
    }
}
