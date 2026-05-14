package com.cometproject.game.rooms.models;

import com.cometproject.api.game.rooms.models.IRoomModel;
import com.cometproject.api.game.rooms.models.RoomModelData;
import com.cometproject.api.game.rooms.models.RoomTileState;

/**
 * Describes room model behavior for the room subsystem.
 */
public class RoomModel implements IRoomModel {
    private static final char[] characters = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

    private final RoomModelData roomModelData;
    private final RoomTileState[][] squareStates;
    private final int[][] squareHeights;
    private final String roomMap;
    private final int doorZ;

    private final int sizeX;
    private final int sizeY;

    private String relativeHeightmap;

    /**
     * Creates a room model instance for the room subsystem.
     *
     * @param roomModelData Room model data supplied by the caller.
     * @param squareStates Square states supplied by the caller.
     * @param map Map supplied by the caller.
     * @param squareHeights Square heights supplied by the caller.
     * @param doorZ Door z supplied by the caller.
     */
    public RoomModel(RoomModelData roomModelData, RoomTileState[][] squareStates, String map, int[][] squareHeights, int doorZ) {
        this.roomModelData = roomModelData;
        this.squareStates = squareStates;
        this.squareHeights = squareHeights;
        this.roomMap = map;
        this.doorZ = doorZ;

        this.sizeY = this.squareStates[0].length;
        this.sizeX = this.squareStates.length;
    }

    /**
     * Returns the relative heightmap for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getRelativeHeightmap() {
        if (this.relativeHeightmap != null) {
            return this.relativeHeightmap;
        }

        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < this.sizeY; y++) {
            for (int x = 0; x < this.sizeX; x++) {
                if (this.getSquareState()[x][y] == RoomTileState.INVALID) {
                    builder.append("x");
                } else {
                    builder.append(characters[(int) Math.floor(this.getSquareHeight()[x][y] + 0.5d)]);
                }
            }

            builder.append((char) 13);
        }

        this.relativeHeightmap = builder.toString();
        return this.relativeHeightmap;
    }

    /**
     * Returns the id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getId() {
        return this.getRoomModelData().getName();
    }

    /**
     * Returns the room model data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomModelData getRoomModelData() {
        return roomModelData;
    }

    /**
     * Returns the square state for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomTileState[][] getSquareState() {
        return squareStates;
    }

    /**
     * Returns the square height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int[][] getSquareHeight() {
        return squareHeights;
    }

    /**
     * Returns the map for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getMap() {
        return this.roomMap;
    }

    /**
     * Returns the door x for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDoorX() {
        return this.getRoomModelData().getDoorX();
    }

    /**
     * Returns the door y for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDoorY() {
        return this.getRoomModelData().getDoorY();
    }

    /**
     * Returns the door z for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDoorZ() {
        return this.doorZ;
    }

    /**
     * Returns the size x for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getSizeX() {
        return this.sizeX;
    }

    /**
     * Returns the size y for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getSizeY() {
        return this.sizeY;
    }

    /**
     * Returns the door rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDoorRotation() {
        return this.getRoomModelData().getDoorRotation();
    }
}
