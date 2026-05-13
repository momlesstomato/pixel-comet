package com.cometproject.server.storage;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.database.DatabaseConfiguration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.storage.cache.CacheManager;
import com.cometproject.server.storage.migration.FlywayMigrationService;
import com.cometproject.storage.api.migration.IMigrationService;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import com.cometproject.storage.mysql.MySQLStorageInitializer;
import com.cometproject.storage.mysql.connections.HikariConnectionProvider;
import com.google.inject.Inject;

public class StorageManager implements Startable {
    private final HikariConnectionProvider hikariConnectionProvider;
    private final IPlayerRepository playerRepository;

    /**
     * Creates the storage manager with shared database collaborators.
     *
     * @param hikariConnectionProvider the server-wide Hikari connection provider.
     * @param playerRepository         the player repository exposed through storage context.
     */
    @Inject
    public StorageManager(
            final HikariConnectionProvider hikariConnectionProvider,
            final IPlayerRepository playerRepository) {
        this.hikariConnectionProvider = hikariConnectionProvider;
        this.playerRepository = playerRepository;
    }

    public static StorageManager getInstance() {
        return CometBootstrap.resolve(StorageManager.class);
    }

    @Override
    public void start() {
        final MySQLStorageInitializer initializer = new MySQLStorageInitializer(
            hikariConnectionProvider,
            this.playerRepository);
        final StorageContext storageContext = new StorageContext();
        final boolean seedEnabled = Boolean.parseBoolean(
            Configuration.currentConfig().getOrDefault(
                DatabaseConfiguration.SEED_ENABLED,
                DatabaseConfiguration.defaults().get(DatabaseConfiguration.SEED_ENABLED)));
        final IMigrationService migrationService = new FlywayMigrationService(
            this.hikariConnectionProvider.getDataSource(),
            seedEnabled);

        migrationService.migrate();

        initializer.setup(storageContext);

        StorageContext.setCurrentContext(storageContext);

        CacheManager.getInstance().start();
        SqlHelper.init(hikariConnectionProvider);
    }

    @Override
    public void stop() {
        CacheManager.getInstance().stop();
        this.hikariConnectionProvider.shutdown();
    }
}
