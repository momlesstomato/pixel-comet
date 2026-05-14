package com.cometproject.server.game.rooms.objects.entities.pathfinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Describes affected tile behavior for the room pathfinding subsystem.
 */
public class AffectedTile {
    public int x;
    public int y;

    /**
     * Creates a affected tile instance for the room pathfinding subsystem.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public AffectedTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the affected both tiles at for this room pathfinding contract.
     *
     * @param length Length supplied by the caller.
     * @param width Width supplied by the caller.
     * @param posX Pos x supplied by the caller.
     * @param posY Pos y supplied by the caller.
     * @param rotation Rotation supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static List<AffectedTile> getAffectedBothTilesAt(int length, int width, int posX, int posY, int rotation) {
        List<AffectedTile> pointList = new ArrayList<>();

        pointList.add(new AffectedTile(posX, posY));

        if (length > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < length; i++) {
                    pointList.add(new AffectedTile(posX, posY + i));

                    for (int j = 1; j < width; j++) {
                        pointList.add(new AffectedTile(posX + j, posY + i));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < length; i++) {
                    pointList.add(new AffectedTile(posX + i, posY));

                    for (int j = 1; j < width; j++) {
                        pointList.add(new AffectedTile(posX + i, posY + j));
                    }
                }
            }
        }

        if (width > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < width; i++) {
                    pointList.add(new AffectedTile(posX + i, posY));

                    for (int j = 1; j < length; j++) {
                        pointList.add(new AffectedTile(posX + i, posY + j));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < width; i++) {
                    pointList.add(new AffectedTile(posX, posY + i));

                    for (int j = 1; j < length; j++) {
                        pointList.add(new AffectedTile(posX + j, posY + i));
                    }
                }
            }
        }

        return pointList;
    }

    /**
     * Returns the affected tiles at for this room pathfinding contract.
     *
     * @param length Length supplied by the caller.
     * @param width Width supplied by the caller.
     * @param posX Pos x supplied by the caller.
     * @param posY Pos y supplied by the caller.
     * @param rotation Rotation supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static List<AffectedTile> getAffectedTilesAt(int length, int width, int posX, int posY, int rotation) {
        List<AffectedTile> pointList = new ArrayList<>();

        if (length > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < length; i++) {
                    pointList.add(new AffectedTile(posX, posY + i));

                    for (int j = 1; j < width; j++) {
                        pointList.add(new AffectedTile(posX + j, posY + i));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < length; i++) {
                    pointList.add(new AffectedTile(posX + i, posY));

                    for (int j = 1; j < width; j++) {
                        pointList.add(new AffectedTile(posX + i, posY + j));
                    }
                }
            }
        }

        if (width > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < width; i++) {
                    pointList.add(new AffectedTile(posX + i, posY));

                    for (int j = 1; j < length; j++) {
                        pointList.add(new AffectedTile(posX + i, posY + j));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < width; i++) {
                    pointList.add(new AffectedTile(posX, posY + i));

                    for (int j = 1; j < length; j++) {
                        pointList.add(new AffectedTile(posX + j, posY + i));
                    }
                }
            }
        }

        return pointList;
    }
}