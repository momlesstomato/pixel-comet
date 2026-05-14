package com.cometproject.server.game.pets.commands.types;

import com.cometproject.server.game.pets.commands.PetCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;

/**
 * Describes follow command behavior for the pet subsystem.
 */
public class FollowCommand extends PetCommand {
    /**
     * Executes execute for this pet contract.
     *
     * @param executor Executor supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean execute(PlayerEntity executor, PetEntity entity) {
        entity.moveTo(executor.getPosition().squareInFront(executor.getBodyRotation()));
        entity.getPetAI().free();

        entity.getPetAI().setFollowingPlayer(executor);

        executor.getFollowingEntities().add(entity);
        return false;
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
