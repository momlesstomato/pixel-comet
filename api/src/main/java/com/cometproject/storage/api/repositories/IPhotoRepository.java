package com.cometproject.storage.api.repositories;

/**
 * Defines the i photo repository contract for the storage subsystem.
 */
public interface IPhotoRepository {
    /**
     * Persists photo data for this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param roomId Room id value supplied by the caller.
     * @param photoId Photo id value supplied by the caller.
     * @param timestamp Timestamp value supplied by the caller.
     */
    void savePhoto(int playerId, int roomId, String photoId, int timestamp);
}
