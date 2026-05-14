package com.cometproject.api.game.utilities;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;


/**
 * Describes position behavior for the Comet subsystem.
 */
public class Position {
    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;

    public static final int[] COLLIDE_TILES = new int[]{
            NORTH, EAST, SOUTH, WEST
    };

    public static final int[] DIAG_TILES = new int[]{
            NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST
    };


    private int x;
    private int y;
    private double z;

    private int flag = -1;

    /**
     * Creates a position instance for the utility subsystem.
     *
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     * @param z Z value supplied by the caller.
     */
    public Position(int x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a position instance for the utility subsystem.
     *
     * @param old Old value supplied by the caller.
     */
    public Position(Position old) {
        this.x = old.getX();
        this.y = old.getY();
        this.z = old.getZ();
    }

    /**
     * Creates a position instance for the utility subsystem.
     */
    public Position() {
        this.x = 0;
        this.y = 0;
        this.z = 0d;
    }

    /**
     * Creates a position instance for the utility subsystem.
     *
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0d;
    }

    /**
     * Executes the add operation for this utility contract.
     *
     * @param other Other value supplied by the caller.
     * @return Result produced by the mutation.
     */
    public Position add(Position other) {
        return new Position(other.getX() + getX(), other.getY() + getY(), other.getZ() + getZ());
    }

    /**
     * Executes the subtract operation for this utility contract.
     *
     * @param other Other value supplied by the caller.
     * @return Result produced by the operation.
     */
    public Position subtract(Position other) {
        return new Position(other.getX() - getX(), other.getY() - getY(), other.getZ() - getZ());
    }

    /**
     * Returns the distance squared associated with this utility contract.
     *
     * @param point Point value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public int getDistanceSquared(Position point) {
        int dx = this.getX() - point.getX();
        int dy = this.getY() - point.getY();

        return (dx * dx) + (dy * dy);
    }

    /**
     * Executes the validate wall position operation for this utility contract.
     *
     * @param position Position value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static String validateWallPosition(String position) {
        try {
            String[] data = position.split(" ");
            if (data[2].equals("l") || data[2].equals("r")) {
                String[] width = data[0].substring(3).split(",");
                int widthX = Integer.parseInt(width[0]);
                int widthY = Integer.parseInt(width[1]);
//                if (widthX < 0 || widthY < 0 || widthX > 200 || widthY > 200)
//                    return null;

                String[] length = data[1].substring(2).split(",");
                int lengthX = Integer.parseInt(length[0]);
                int lengthY = Integer.parseInt(length[1]);
//                if (lengthX < 0 || lengthY < 0 || lengthX > 200 || lengthY > 200)
//                    return null;

                return ":w=" + widthX + "," + widthY + " " + "l=" + lengthX + "," + lengthY + " " + data[2];
            }
        } catch (Exception ignored) {

        }

        return null;
    }

    /**
     * Executes the calculate height operation for this utility contract.
     *
     * @param definition Definition value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static double calculateHeight(FurnitureDefinition definition) {
        if (definition.getInteraction().equals("gate")) {
            return 0;
        } else if (definition.canSit()) {
            return 0;
        }

        return definition.getHeight();
    }

    /**
     * Executes the calculate rotation operation for this utility contract.
     *
     * @param from From value supplied by the caller.
     * @param to To value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculateRotation(Position from, Position to) {
        return calculateRotation(from.x, from.y, to.x, to.y, false);
    }

    /**
     * Executes the calculate rotation operation for this utility contract.
     *
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     * @param newX New x value supplied by the caller.
     * @param newY New y value supplied by the caller.
     * @param reversed Reversed value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int calculateRotation(int x, int y, int newX, int newY, boolean reversed) {
        int rotation = 0;

        if (x > newX && y > newY)
            rotation = 7;
        else if (x < newX && y < newY)
            rotation = 3;
        else if (x > newX && y < newY)
            rotation = 5;
        else if (x < newX && y > newY)
            rotation = 1;
        else if (x > newX)
            rotation = 6;
        else if (x < newX)
            rotation = 2;
        else if (y < newY)
            rotation = 4;
        else if (y > newY)
            rotation = 0;

        if (reversed) {
            if (rotation > 3) {
                rotation = rotation - 4;
            } else {
                rotation = rotation + 4;
            }
        }

        return rotation;
    }

    /**
     * Executes the square in front operation for this utility contract.
     *
     * @param angle Angle value supplied by the caller.
     * @return Result produced by the operation.
     */
    public Position squareInFront(int angle) {
        return calculatePosition(this.x, this.y, angle, false, 1);
    }

    /**
     * Executes the square in front x operation for this utility contract.
     *
     * @param angle Angle value supplied by the caller.
     * @return Result produced by the operation.
     */
    public Position squareInFrontX(int angle) {
        return calculatePosition(this.x, this.y, angle, false, 1);
    }

    /**
     * Executes the square in front operation for this utility contract.
     *
     * @param angle Angle value supplied by the caller.
     * @param distance Distance value supplied by the caller.
     * @return Result produced by the operation.
     */
    public Position squareInFront(int angle, int distance) {
        return calculatePosition(this.x, this.y, angle, false, distance);
    }

    /**
     * Executes the square behind operation for this utility contract.
     *
     * @param angle Angle value supplied by the caller.
     * @return Result produced by the operation.
     */
    public Position squareBehind(int angle) {
        return calculatePosition(this.x, this.y, angle, true, 1);
    }

    /**
     * Returns the inverted rotation associated with this utility contract.
     *
     * @param currentRotation Current rotation value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public static int getInvertedRotation(int currentRotation) {
        switch (currentRotation) {
            case NORTH_EAST:
                return NORTH_WEST;
            case NORTH_WEST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return NORTH_WEST;
            case SOUTH_EAST:
                return NORTH_EAST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }

        return currentRotation;
    }

    /**
     * Executes the calculate position operation for this utility contract.
     *
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     * @param angle Angle value supplied by the caller.
     * @param isReversed Is reversed value supplied by the caller.
     * @param distance Distance value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static Position calculatePosition(int x, int y, int angle, boolean isReversed, int distance) {
        switch (angle) {
            case 0:
                if (!isReversed)
                    y -= distance;
                else
                    y += distance;
                break;

            case 1:
                if (!isReversed) {
                    x += distance;
                    y -= distance;
                } else {
                    x -= distance;
                    y += distance;
                }
                break;

            case 2:
                if (!isReversed)
                    x += distance;
                else
                    x -= distance;
                break;

            case 3:
                if (!isReversed) {
                    x += distance;
                    y += distance;
                } else {
                    x -= distance;
                    y -= distance;
                }
                break;

            case 4:
                if (!isReversed)
                    y += distance;
                else
                    y -= distance;
                break;

            case 5:
                if (!isReversed) {
                    x -= distance;
                    y += distance;
                } else {
                    x++;
                    y--;
                }
                break;

            case 6:
                if (!isReversed)
                    x -= distance;
                else
                    x += distance;
                break;

            case 7:
                if (!isReversed) {
                    x -= distance;
                    y -= distance;
                } else {
                    x += distance;
                    y += distance;
                }
                break;
        }

        return new Position(x, y);
    }

    /**
     * Executes the distance to operation for this utility contract.
     *
     * @param pos Pos value supplied by the caller.
     * @return Result produced by the operation.
     */
    public double distanceTo(Position pos) {
        return Math.abs(this.getX() - pos.getX()) + Math.abs(this.getY() - pos.getY());
    }

    /**
     * Executes the touching operation for this utility contract.
     *
     * @param pos Pos value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    public boolean touching(Position pos) {
        if (!(Math.abs(this.getX() - pos.getX()) > 1 || Math.abs(this.getY() - pos.getY()) > 1)) {
            return true;
        }

        return this.getX() == pos.getX() && this.getY() == pos.getY();

    }

    /**
     * Executes the copy operation for this utility contract.
     *
     * @return Result produced by the operation.
     */
    public Position copy() {
        return new Position(this.x, this.y, this.z);
    }

    /**
     * Executes to string for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")";
    }

    /**
     * Returns the x for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the z for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Updates the x for this Comet contract.
     *
     * @param x X supplied by the caller.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Executes the increment x operation for this utility contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    public void incrementX(int amount) {
        this.x += amount;
    }

    /**
     * Executes the increment y operation for this utility contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    public void incrementY(int amount) {
        this.y += amount;
    }

    /**
     * Updates the y for this Comet contract.
     *
     * @param y Y supplied by the caller.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Updates the z for this Comet contract.
     *
     * @param z Z supplied by the caller.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Executes equals for this Comet contract.
     *
     * @param o O supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            return ((Position) o).getX() == this.getX() && ((Position) o).getY() == this.getY();
        }

        return false;
    }

    /**
     * Executes hash code for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Returns the flag for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getFlag() {
        return flag;
    }

    /**
     * Updates the flag for this Comet contract.
     *
     * @param flag Flag supplied by the caller.
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }
}
