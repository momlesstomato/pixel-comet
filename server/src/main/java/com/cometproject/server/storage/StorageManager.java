package com.cometproject.server.storage;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.storage.cache.CacheManager;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.mysql.MySQLStorageInitializer;
import com.cometproject.storage.mysql.connections.HikariConnectionProvider;

public class StorageManager implements Startable {
    private final HikariConnectionProvider hikariConnectionProvider;

    public StorageManager() {
        hikariConnectionProvider = new HikariConnectionProvider();
    }

    public static StorageManager getInstance() {
        return CometBootstrap.resolve(StorageManager.class);
    }

    @Override
    public void start() {
        final MySQLStorageInitializer initializer = new MySQLStorageInitializer(hikariConnectionProvider);
        final StorageContext storageContext = new StorageContext();

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
