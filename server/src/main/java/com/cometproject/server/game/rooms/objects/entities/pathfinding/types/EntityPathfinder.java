package com.cometproject.server.game.rooms.objects.entities.pathfinding.types;

import com.cometproject.server.game.rooms.objects.entities.pathfinding.Pathfinder;

/**
 * Describes entity pathfinder behavior for the room pathfinding subsystem.
 */
public class EntityPathfinder extends Pathfinder {
    private static EntityPathfinder pathfinderInstance;

    /**
     * Returns the instance for this room pathfinding contract.
     *
     * @return Value exposed by the contract.
     */
    public static EntityPathfinder getInstance() {
        if (pathfinderInstance == null) {
            pathfinderInstance = new EntityPathfinder();
        }

        return pathfinderInstance;
    }
}
