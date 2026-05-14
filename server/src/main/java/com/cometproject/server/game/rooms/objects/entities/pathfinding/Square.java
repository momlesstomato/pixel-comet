package com.cometproject.server.game.rooms.objects.entities.pathfinding;

/**
 * Describes square behavior for the room pathfinding subsystem.
 */
public class Square {
    public int x;
    public int y;

    /**
     * Creates a square instance for the room pathfinding subsystem.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
