package com.cometproject.storage.mysql.repositories;

import com.cometproject.storage.api.repositories.IPhotoRepository;
import com.cometproject.storage.mysql.MySQLConnectionProvider;

/**
 * Persists and loads my SQL photo data for the MySQL storage subsystem.
 */
public class MySQLPhotoRepository extends MySQLRepository implements IPhotoRepository {

    /**
     * Creates a my SQL photo repository instance for the MySQL storage subsystem.
     *
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public MySQLPhotoRepository(MySQLConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    /**
     * Persists photo for this MySQL storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     * @param photoId Photo id supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    @Override
    public void savePhoto(int playerId, int roomId, String photoId, int timestamp) {
        update("INSERT into player_photos (player_id, room_id, photo, timestamp) VALUES(?, ?, ?, ?);", playerId, roomId, photoId, timestamp);
    }
}
