package com.cometproject.server.game.utilities;

import com.cometproject.api.game.utilities.Position;


/**
 * Describes distance calculator behavior for the Comet subsystem.
 */
public class DistanceCalculator {
    /**
     * Executes calculate for this Comet contract.
     *
     * @param pos1X Pos1 x supplied by the caller.
     * @param pos1Y Pos1 y supplied by the caller.
     * @param pos2X Pos2 x supplied by the caller.
     * @param pos2Y Pos2 y supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculate(int pos1X, int pos1Y, int pos2X, int pos2Y) {
        return Math.abs(pos1X - pos2X) + Math.abs(pos1Y - pos2Y);
    }

    /**
     * Executes calculate for this Comet contract.
     *
     * @param position Position supplied by the caller.
     * @param position2 Position2 supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculate(Position position, Position position2) {
        return calculate(position.getX(), position.getY(), position2.getX(), position2.getY());
    }

    /**
     * Executes calculate y for this Comet contract.
     *
     * @param pos1Y Pos1 y supplied by the caller.
     * @param pos2Y Pos2 y supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculateY(int pos1Y, int pos2Y) {
        return Math.abs(pos1Y - pos2Y);
    }

    /**
     * Executes calculate y for this Comet contract.
     *
     * @param position Position supplied by the caller.
     * @param position2 Position2 supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculateY(Position position, Position position2) {
        return calculateY(position.getY(), position2.getY());
    }

    /**
     * Executes calculate x for this Comet contract.
     *
     * @param pos1Y Pos1 y supplied by the caller.
     * @param pos2Y Pos2 y supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculateX(int pos1Y, int pos2Y) {
        return Math.abs(pos1Y - pos2Y);
    }

    /**
     * Executes calculate x for this Comet contract.
     *
     * @param position Position supplied by the caller.
     * @param position2 Position2 supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculateX(Position position, Position position2) {
        return calculateY(position.getX(), position2.getX());
    }

    /**
     * Executes tiles touching for this Comet contract.
     *
     * @param pos1X Pos1 x supplied by the caller.
     * @param pos1Y Pos1 y supplied by the caller.
     * @param pos2X Pos2 x supplied by the caller.
     * @param pos2Y Pos2 y supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean tilesTouching(int pos1X, int pos1Y, int pos2X, int pos2Y) {
        if (!(Math.abs(pos1X - pos2X) > 1 || Math.abs(pos1Y - pos2Y) > 1)) {
            return true;
        }

        if (pos1X == pos2X && pos1Y == pos2Y) {
            return true;
        }

        return false;
    }

    /**
     * Executes tiles touching for this Comet contract.
     *
     * @param pos1 Pos1 supplied by the caller.
     * @param pos2 Pos2 supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean tilesTouching(Position pos1, Position pos2) {
        return tilesTouching(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
    }
}
