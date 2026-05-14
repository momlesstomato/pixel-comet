package com.cometproject.game.rooms.services;

import com.cometproject.api.caching.Cache;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.IRoomService;
import com.cometproject.storage.api.data.Data;
import com.cometproject.storage.api.repositories.IRoomRepository;

/**
 * Coordinates room behavior for the room subsystem.
 */
public class RoomService implements IRoomService {

    private final IRoomRepository roomRepository;
    private final Cache<Integer, IRoomData> roomDataCache;

    /**
     * Creates a room service instance for the room subsystem.
     *
     * @param roomRepository Room repository supplied by the caller.
     * @param roomDataCache Room data cache supplied by the caller.
     */
    public RoomService(final IRoomRepository roomRepository, final Cache<Integer, IRoomData> roomDataCache) {
        this.roomRepository = roomRepository;
        this.roomDataCache = roomDataCache;
    }

    /**
     * Returns the room data for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    @Override
    public IRoomData getRoomData(int roomId) {
        if(roomId == 0) {
            return null;
        }

        Data<IRoomData> roomData = Data.createEmpty();

        if (this.roomDataCache.contains(roomId)) {
            return this.roomDataCache.get(roomId);
        }

        this.roomRepository.getRoomDataById(roomId, roomData::set);

        if(roomData.has()) {
            this.roomDataCache.add(roomId, roomData.get());
            return roomData.get();
        }

        return null;
    }

    /**
     * Persists room data for this room contract.
     *
     * @param roomData Room data supplied by the caller.
     */
    @Override
    public void saveRoomData(IRoomData roomData) {
        this.roomRepository.updateRoom(roomData);
    }
}
