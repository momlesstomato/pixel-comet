package com.cometproject.server.game.rooms.bundles;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.rooms.bundles.types.RoomBundle;
import com.cometproject.server.storage.queries.rooms.BundleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages room bundle runtime state for the room subsystem.
 */
public class RoomBundleManager implements Startable {
    private static Logger LOGGER = LoggerFactory.getLogger(RoomBundleManager.class.getName());

    private Map<String, RoomBundle> bundles;

    /**
     * Creates a room bundle manager instance for the room subsystem.
     */
    public RoomBundleManager() {
        this.bundles = new ConcurrentHashMap<>();
    }

    /**
     * Returns the instance for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public static RoomBundleManager getInstance() {
        return CometBootstrap.resolve(RoomBundleManager.class);
    }

    /**
     * Starts this room component.
     */
    @Override
    public void start() {
        if (this.bundles.size() != 0) {
            this.bundles.clear();
        }

        BundleDao.loadActiveBundles(this.bundles);
        LOGGER.info("Loaded " + this.bundles.size() + " active room bundles");

        LOGGER.info("RoomBundleManager initialized");
    }

    /**
     * Adds bundle to this room contract.
     *
     * @param bundle Bundle supplied by the caller.
     */
    public void addBundle(RoomBundle bundle) {
        if (this.bundles.containsKey(bundle.getAlias())) {
            this.bundles.replace(bundle.getAlias(), bundle);
        } else {
            this.bundles.put(bundle.getAlias(), bundle);
        }
    }

    /**
     * Returns the bundle for this room contract.
     *
     * @param alias Alias supplied by the caller.
     * @return Value exposed by the contract.
     */
    public RoomBundle getBundle(String alias) {
        return this.bundles.get(alias);
    }
}
