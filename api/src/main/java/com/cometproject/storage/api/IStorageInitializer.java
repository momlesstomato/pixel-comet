package com.cometproject.storage.api;

/**
 * Defines the i storage initializer contract for the storage subsystem.
 */
public interface IStorageInitializer {

    /**
     * Updates the up value for this storage contract.
     *
     * @param storageContext Storage context value supplied by the caller.
     */
    void setup(StorageContext storageContext);

}
