package com.cometproject.server.game.rooms.models.types;

import com.cometproject.server.game.rooms.models.RoomModel;

import java.sql.ResultSet;


/**
 * Describes static room model behavior for the room subsystem.
 */
public class StaticRoomModel extends RoomModel {
    /**
     * Creates a static room model instance for the room subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws Exception When the operation cannot complete.
     */
    public StaticRoomModel(ResultSet data) throws Exception {
        super(data.getString("id"), data.getString("heightmap"), data.getInt("door_x"), data.getInt("door_y"), data.getInt("door_dir"), -1);
    }
}
