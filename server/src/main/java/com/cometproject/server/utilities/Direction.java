package com.cometproject.server.utilities;

/**
 * Enumerates direction values used by the Comet subsystem.
 */
public enum Direction {
    North,
    NorthEast,
    East,
    SouthEast,
    South,
    SouthWest,
    West,
    NorthWest;

    public static final Direction[] VALUES = Direction.values();
    public static final Direction NEUTRAL = Direction.East;
    public final int num;
    public final int modX;
    public final int modY;

    Direction() {
        this.num = this.ordinal();
        switch (this.num) {
            // North
            case 0:
                this.modX = 0;
                this.modY = -1;
                break;

            // NorthEast
            case 1:
                this.modX = +1;
                this.modY = -1;
                break;

            // East
            case 2:
                this.modX = +1;
                this.modY = 0;
                break;

            // SouthEast
            case 3:
                this.modX = +1;
                this.modY = +1;
                break;

            // South
            case 4:
                this.modX = 0;
                this.modY = +1;
                break;

            // SouthWest
            case 5:
                this.modX = -1;
                this.modY = +1;
                break;

            // West
            case 6:
                this.modX = -1;
                this.modY = 0;
                break;

            // NorthWest
            case 7:
                this.modX = -1;
                this.modY = -1;
                break;

            // Uh...
            default:
                this.modX = 0;
                this.modY = 0;
        }
    }

    /**
     * Executes get for this Comet contract.
     *
     * @param num Num supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static final Direction get(int num) {
        return VALUES[num];
    }

    /**
     * Executes random for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public static final Direction random() {
        return VALUES[RandomUtil.getRandomInt(0, 7)];
    }

    /**
     * Executes calculate for this Comet contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     * @param x2 X2 supplied by the caller.
     * @param y2 Y2 supplied by the caller.
     * @return Result produced by the operation.
     */
    public static final Direction calculate(int x, int y, int x2, int y2) {
        if (x > x2) {
            if (y == y2) return West;
            else if (y < y2) return SouthWest;
            else return NorthWest;
        } else if (x < x2) {
            if (y == y2) return East;
            else if (y < y2) return SouthEast;
            else return NorthEast;
        } else {
            if (y < y2) return South;
            else return North;
        }
    }

    /**
     * Executes invert for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public final Direction invert() {
        return VALUES[(this.num + (VALUES.length / 2)) % VALUES.length];
    }

    /**
     * Executes transform for this Comet contract.
     *
     * @param dir Dir supplied by the caller.
     * @return Result produced by the operation.
     */
    public final Direction transform(Direction dir) {
        return VALUES[(this.num + dir.num) % VALUES.length];
    }
}
