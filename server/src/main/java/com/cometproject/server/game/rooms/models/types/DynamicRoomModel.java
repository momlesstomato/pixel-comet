package com.cometproject.server.game.rooms.models.types;

import com.cometproject.api.game.rooms.models.InvalidModelException;
import com.cometproject.api.game.rooms.models.RoomModelData;
import com.cometproject.server.game.rooms.models.RoomModel;


/**
 * Describes dynamic room model behavior for the room subsystem.
 */
public class DynamicRoomModel extends RoomModel {
    /**
     * Creates a dynamic room model instance for the room subsystem.
     *
     * @param name Name supplied by the caller.
     * @param heightmap Heightmap supplied by the caller.
     * @param doorX Door x supplied by the caller.
     * @param doorY Door y supplied by the caller.
     * @param doorRotation Door rotation supplied by the caller.
     * @param wallHeight Wall height supplied by the caller.
     * @throws InvalidModelException When the operation cannot complete.
     */
    public DynamicRoomModel(String name, String heightmap, int doorX, int doorY, int doorRotation, int wallHeight) throws InvalidModelException {
        super(name, heightmap, doorX, doorY, doorRotation, wallHeight);
    }

    /**
     * Creates a dynamic room model instance for the room subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws InvalidModelException When the operation cannot complete.
     */
    public DynamicRoomModel(RoomModelData data) throws InvalidModelException {
        super(data.getName(), data.getHeightmap(), data.getDoorX(), data.getDoorY(), data.getDoorRotation(), data.getWallHeight());
    }

    /**
     * Executes create for this room contract.
     *
     * @param name Name supplied by the caller.
     * @param heightmap Heightmap supplied by the caller.
     * @param doorX Door x supplied by the caller.
     * @param doorY Door y supplied by the caller.
     * @param doorRotation Door rotation supplied by the caller.
     * @param wallHeight Wall height supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static DynamicRoomModel create(String name, String heightmap, int doorX, int doorY, int doorRotation, int wallHeight) {
        try {
            return new DynamicRoomModel(name, heightmap, doorX, doorY, doorRotation, wallHeight);
        } catch (InvalidModelException e) {
            return null;
        }
    }
}
