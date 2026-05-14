package com.cometproject.server.storage.cache.subscribers;

import com.cometproject.server.game.navigator.NavigatorManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Describes refresh data subscriber behavior for the storage subsystem.
 */
public class RefreshDataSubscriber implements ISubscriber {
    private Jedis jedis = null;

    /**
     * Updates the jedis for this storage contract.
     *
     * @param jedis Jedis supplied by the caller.
     */
    @Override
    public void setJedis(JedisPool jedis) {
        this.jedis = jedis.getResource();
    }

    /**
     * Executes subscribe for this storage contract.
     */
    @Override
    public void subscribe() {
        this.jedis.subscribe(new JedisPubSub() {
            /**
             * Handles the message callback for this storage contract.
             *
             * @param channel Channel supplied by the caller.
             * @param message Message supplied by the caller.
             */
            @Override
            public void onMessage(String channel, String message) {
                handleMessage(message);
            }
        }, getChannel());
    }

    /**
     * Handles message for this storage contract.
     *
     * @param message Message supplied by the caller.
     */
    @Override
    public void handleMessage(String message) {
        switch (message) {
            case "navigator":
                NavigatorManager.getInstance().loadCategories();
                NavigatorManager.getInstance().loadPublicRooms();
                NavigatorManager.getInstance().loadStaffPicks();
        }
    }

    /**
     * Returns the channel for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getChannel() {
        return "comet.refresh.data";
    }
}
