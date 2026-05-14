package com.cometproject.server.game.rooms.models;

import com.cometproject.api.game.rooms.models.InvalidModelException;
import com.cometproject.api.game.rooms.models.IRoomModel;
import com.cometproject.api.game.rooms.models.RoomModelData;
import com.cometproject.api.game.rooms.models.RoomTileState;
import com.cometproject.api.utilities.ModelUtils;
import org.slf4j.LoggerFactory;


/**
 * Describes room model behavior for the room subsystem.
 */
public abstract class RoomModel implements IRoomModel {
    private static final char[] HEIGHT_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

    private String name;
    private String heightmap;
    private String map = "";
    private int doorX;
    private int doorY;
    private int doorZ;
    private int doorRotation;
    private int mapSizeX;
    private int mapSizeY;
    private int[][] squareHeight;
    private RoomTileState[][] squareState;
    private int wallHeight;

    /**
     * Creates a room model instance for the room subsystem.
     *
     * @param name Name supplied by the caller.
     * @param heightmap Heightmap supplied by the caller.
     * @param doorX Door x supplied by the caller.
     * @param doorY Door y supplied by the caller.
     * @param doorRotation Door rotation supplied by the caller.
     * @param wallHeight Wall height supplied by the caller.
     * @throws InvalidModelException When the operation cannot complete.
     */
    public RoomModel(String name, String heightmap, int doorX, int doorY, int doorRotation, int wallHeight) throws InvalidModelException {
        this.name = name;
        this.heightmap = heightmap;
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorRotation = doorRotation;
        this.wallHeight = wallHeight;

        String[] axes = heightmap.split("\r");

        if (axes.length == 0) throw new InvalidModelException();

        this.mapSizeX = axes[0].length();
        this.mapSizeY = axes.length;
        this.squareHeight = new int[mapSizeX][mapSizeY];
        this.squareState = new RoomTileState[mapSizeX][mapSizeY];

        int maxTileHeight = 0;

        try {
            for (int y = 0; y < mapSizeY; y++) {
                char[] line = axes[y].replace("\r", "").replace("\n", "").toCharArray();

                int x = 0;
                for (char tile : line) {
                    if (x >= mapSizeX) {
                        throw new InvalidModelException();
                    }

                    String tileVal = String.valueOf(tile);

                    if (tileVal.equals("x")) {
                        squareState[x][y] = (x == doorX && y == doorY) ? RoomTileState.VALID : RoomTileState.INVALID;
                    } else {
                        squareState[x][y] = RoomTileState.VALID;
                        squareHeight[x][y] = ModelUtils.getHeight(tile);

                        if (squareHeight[x][y] > maxTileHeight) {
                            maxTileHeight = (int) Math.ceil(squareHeight[x][y]);
                        }
                    }

                    x++;
                }
            }

            this.doorZ = this.squareHeight[doorX][doorY];

            for (String mapLine : heightmap.split("\r\n")) {
                if (mapLine.isEmpty()) {
                    continue;
                }
                map += mapLine + (char) 13;
            }
        } catch (Exception e) {
            if (e instanceof InvalidModelException) {
                throw e;
            }

            LoggerFactory.getLogger(RoomModel.class.getName()).error("Failed to parse heightmap for model: " + this.name, e);
        }

        if (maxTileHeight >= 29) {
            this.wallHeight = 15;
        }
    }

    /**
     * Returns the id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getId() {
        return this.name;
    }

    /**
     * Returns the map for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMap() {
        return this.map;
    }

    /**
     * Returns the door x for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorX() {
        return this.doorX;
    }

    /**
     * Returns the door y for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorY() {
        return this.doorY;
    }

    /**
     * Returns the door z for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorZ() {
        return this.doorZ;
    }

    /**
     * Updates the door z for this room contract.
     *
     * @param doorZ Door z supplied by the caller.
     */
    public void setDoorZ(int doorZ) {
        this.doorZ = doorZ;
    }

    /**
     * Returns the door rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorRotation() {
        return this.doorRotation;
    }

    /**
     * Returns the size x for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSizeX() {
        return this.mapSizeX;
    }

    /**
     * Returns the size y for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSizeY() {
        return this.mapSizeY;
    }

    /**
     * Returns the square state for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomTileState[][] getSquareState() {
        return this.squareState;
    }

    /**
     * Returns the square height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int[][] getSquareHeight() {
        return this.squareHeight;
    }

    /**
     * Returns the wall height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getWallHeight() {
        return wallHeight;
    }

    /**
     * Returns the relative heightmap for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getRelativeHeightmap() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < this.getSizeY(); y++) {
            for (int x = 0; x < this.getSizeX(); x++) {
                if (this.squareState[x][y] == RoomTileState.INVALID) {
                    builder.append('x');
                } else {
                    builder.append(HEIGHT_CHARS[(int) Math.floor(this.squareHeight[x][y] + 0.5d)]);
                }
            }
            builder.append((char) 13);
        }
        return builder.toString();
    }

    /**
     * Returns the room model data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomModelData getRoomModelData() {
        return new RoomModelData(this.name, this.heightmap, this.doorX, this.doorY, this.doorRotation, this.wallHeight);
    }
}
