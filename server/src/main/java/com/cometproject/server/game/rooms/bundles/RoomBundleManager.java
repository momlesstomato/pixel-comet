package com.cometproject.server.game.rooms.bundles;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.rooms.bundles.types.RoomBundle;
import com.cometproject.server.storage.queries.rooms.BundleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomBundleManager implements Startable {
    private static Logger LOGGER = LoggerFactory.getLogger(RoomBundleManager.class.getName());

    private Map<String, RoomBundle> bundles;

    public RoomBundleManager() {
        this.bundles = new ConcurrentHashMap<>();
    }

    public static RoomBundleManager getInstance() {
        return CometBootstrap.resolve(RoomBundleManager.class);
    }

    @Override
    public void start() {
        if (this.bundles.size() != 0) {
            this.bundles.clear();
        }

        BundleDao.loadActiveBundles(this.bundles);
        LOGGER.info("Loaded " + this.bundles.size() + " active room bundles");

        LOGGER.info("RoomBundleManager initialized");
    }

    public void addBundle(RoomBundle bundle) {
        if (this.bundles.containsKey(bundle.getAlias())) {
            this.bundles.replace(bundle.getAlias(), bundle);
        } else {
            this.bundles.put(bundle.getAlias(), bundle);
        }
    }

    public RoomBundle getBundle(String alias) {
        return this.bundles.get(alias);
    }
}
