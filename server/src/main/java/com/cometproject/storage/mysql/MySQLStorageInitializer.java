package com.cometproject.storage.mysql;

import com.cometproject.game.items.inventory.InventoryItemFactory;
import com.cometproject.storage.api.IStorageInitializer;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import com.cometproject.storage.mysql.models.factories.GroupDataFactory;
import com.cometproject.storage.mysql.models.factories.GroupForumMessageFactory;
import com.cometproject.storage.mysql.models.factories.GroupForumSettingsFactory;
import com.cometproject.storage.mysql.models.factories.GroupMemberFactory;
import com.cometproject.storage.mysql.models.factories.rooms.RoomDataFactory;
import com.cometproject.storage.mysql.models.factories.rooms.RoomModelDataFactory;
import com.cometproject.storage.mysql.repositories.*;

/**
 * Describes my SQL storage initializer behavior for the MySQL storage subsystem.
 */
public class MySQLStorageInitializer implements IStorageInitializer {

    private final MySQLConnectionProvider connectionProvider;
    private final IPlayerRepository playerRepository;

    /**
     * Creates the storage initializer with JDBC and JOOQ-backed repositories.
     *
     * @param connectionProvider the legacy JDBC connection provider.
     * @param playerRepository   the player repository to expose through storage context.
     */
    public MySQLStorageInitializer(
            MySQLConnectionProvider connectionProvider,
            IPlayerRepository playerRepository) {
        this.connectionProvider = connectionProvider;
        this.playerRepository = playerRepository;

        // Enables creation of MySQL repositories from outside the MySQL module :-)
        MySQLStorageContext.setCurrentContext(new MySQLStorageContext(connectionProvider));
    }

    /**
     * Updates the up for this MySQL storage contract.
     *
     * @param storageContext Storage context supplied by the caller.
     */
    @Override
    public void setup(StorageContext storageContext) {
        storageContext.setGroupRepository(new MySQLGroupRepository(new GroupDataFactory(), connectionProvider));
        storageContext.setGroupMemberRepository(new MySQLGroupMemberRepository(new GroupMemberFactory(), connectionProvider));
        storageContext.setGroupForumRepository(new MySQLGroupForumRepository(new GroupForumSettingsFactory(), new GroupForumMessageFactory(), connectionProvider));

        storageContext.setInventoryRepository(new MySQLInventoryRepository(new InventoryItemFactory(), connectionProvider));
        storageContext.setRoomItemRepository(new MySQLRoomItemRepository(connectionProvider));
        storageContext.setRoomRepository(new MySQLRoomRepository(new RoomDataFactory(), new RoomModelDataFactory(), connectionProvider));
        storageContext.setRewardRepository(new MySQLRewardRepository(connectionProvider));
        storageContext.setPhotoRepository(new MySQLPhotoRepository(connectionProvider));
        storageContext.setPlayerRepository(this.playerRepository);
    }
}
