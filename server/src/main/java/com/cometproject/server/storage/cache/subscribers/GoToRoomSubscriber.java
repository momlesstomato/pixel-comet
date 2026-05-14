package com.cometproject.server.storage.cache.subscribers;

import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Describes go to room subscriber behavior for the storage subsystem.
 */
public class GoToRoomSubscriber implements ISubscriber {
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
        Data data = new Gson().fromJson(message, Data.class);

        if (data == null || data.getRoomId() == null || data.getRoomId() == 0 ||
                data.getUsername() == null || data.getUsername().equals(""))
            return;

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(data.getUsername());

        if (session == null)
            return;

        session.send(new RoomForwardMessageComposer(data.getRoomId()));
    }

    /**
     * Returns the channel for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getChannel() {
        return "comet.goto.room";
    }

    private class Data {
        private String username;
        private Integer roomId;

        /**
         * Executes data for this storage contract.
         *
         * @param username Username supplied by the caller.
         * @param roomId Room identifier used by the operation.
         */
        public Data(String username, Integer roomId) {
            this.username = username;
            this.roomId = roomId;
        }

        /**
         * Returns the username for this storage contract.
         *
         * @return Value exposed by the contract.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns the room id for this storage contract.
         *
         * @return Value exposed by the contract.
         */
        public Integer getRoomId() {
            return roomId;
        }
    }
}
