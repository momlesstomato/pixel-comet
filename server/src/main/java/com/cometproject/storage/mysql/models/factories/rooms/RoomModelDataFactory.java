package com.cometproject.storage.mysql.models.factories.rooms;

import com.cometproject.api.game.rooms.models.CustomFloorMapData;
import com.cometproject.api.game.rooms.models.RoomModelData;

/**
 * Creates room model data instances for the MySQL storage subsystem.
 */
public class RoomModelDataFactory {

    public  static final RoomModelDataFactory instance = new RoomModelDataFactory();

    /**
     * Creates data for this MySQL storage contract.
     *
     * @param name Name supplied by the caller.
     * @param heightmap Heightmap supplied by the caller.
     * @param doorX Door x supplied by the caller.
     * @param doorY Door y supplied by the caller.
     * @param doorRotation Door rotation supplied by the caller.
     * @return Value exposed by the contract.
     */
    public RoomModelData createData(String name, String heightmap, int doorX, int doorY, int doorRotation) {
        return new RoomModelData(name, heightmap, doorX, doorY, doorRotation, -1);
    }

    /**
     * Creates data for this MySQL storage contract.
     *
     * @param customFloorData Custom floor data supplied by the caller.
     * @return Value exposed by the contract.
     */
    public RoomModelData createData(CustomFloorMapData customFloorData) {
        return new RoomModelData("dynamic_heightmap", customFloorData.getModelData(), customFloorData.getDoorX(),
                customFloorData.getDoorY(), customFloorData.getDoorRotation(), customFloorData.getWallHeight());
    }

}
