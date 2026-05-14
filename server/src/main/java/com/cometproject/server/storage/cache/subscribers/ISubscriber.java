package com.cometproject.server.storage.cache.subscribers;

import redis.clients.jedis.JedisPool;

/**
 * Defines the i subscriber contract for the storage subsystem.
 */
public interface ISubscriber {
    /**
     * Updates the jedis for this Comet contract.
     *
     * @param jedis Jedis supplied by the caller.
     */
    void setJedis(JedisPool jedis);

    /**
     * Executes subscribe for this Comet contract.
     */
    void subscribe();

    /**
     * Executes handle message for this Comet contract.
     *
     * @param message Message supplied by the caller.
     */
    void handleMessage(String message);

    /**
     * Returns the channel for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getChannel();
}
