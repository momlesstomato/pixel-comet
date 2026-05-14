package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.models.RoomModelData;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Defines the i room repository contract for the storage subsystem.
 */
public interface IRoomRepository {

    /**
     * Returns the all models associated with this storage contract.
     *
     * @param modelConsumer Model consumer value supplied by the caller.
     */
    void getAllModels(Consumer<Map<String, RoomModelData>> modelConsumer);

    /**
     * Returns the room data by id associated with this storage contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @param dataConsumer Data consumer value supplied by the caller.
     */
    void getRoomDataById(int roomId, Consumer<IRoomData> dataConsumer);
//
//    void getRoomsByPlayerId(int playerId, Consumer<Map<Integer, IRoomData>> dataConsumer);
//
//    void getRoomsWithRightsByPlayerId(int playerId, Consumer<Map<Integer, IRoomData>> dataConsumer);
//
//    void getRoomsByQuery(String query, Consumer<List<IRoomData>> dataConsumer);
//
//    void createRoom(IRoomData data);
//
    /**
     * Updates room data for this storage contract.
     *
     * @param data Data value supplied by the caller.
     */
    void updateRoom(IRoomData data);
//
//    void deleteRoom(int id);


}
