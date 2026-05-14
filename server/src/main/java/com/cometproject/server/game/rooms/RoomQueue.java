package com.cometproject.server.game.rooms;

import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.queue.RoomQueueStatusMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Describes room queue behavior for the room subsystem.
 */
public class RoomQueue {

    private static RoomQueue instance;

    private final Map<Integer, List<Integer>> roomQueues;

    /**
     * Creates a room queue instance for the room subsystem.
     */
    public RoomQueue() {
        this.roomQueues = Maps.newConcurrentMap();

        this.roomQueues.put(1, new ArrayList<>());
    }

    /**
     * Returns the instance for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public static RoomQueue getInstance() {
        if (instance == null) {
            instance = new RoomQueue();
        }

        return instance;
    }

    /**
     * Indicates whether this room contract has queue.
     *
     * @param roomId Room identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasQueue(int roomId) {
        return this.roomQueues.containsKey(roomId);
    }

    /**
     * Returns the queue for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public List<Integer> getQueue(int roomId) {
        return this.roomQueues.get(roomId);
    }

    /**
     * Adds queue to this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param startingPlayer Starting player supplied by the caller.
     */
    public void addQueue(int roomId, int startingPlayer) {
        this.roomQueues.put(roomId, startingPlayer == 0 ? new ArrayList<>() : new ArrayList<>(startingPlayer));
    }

    /**
     * Removes queue from this room contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void removeQueue(int roomId) {
        this.roomQueues.remove(roomId);
    }

    /**
     * Removes player from queue from this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     */
    public void removePlayerFromQueue(int roomId, Integer playerId) {
        if (this.hasQueue(roomId)) {
            this.roomQueues.get(roomId).remove(playerId);

            for (int player : this.roomQueues.get(roomId)) {
                Session session = NetworkManager.getInstance().getSessions().getByPlayerId(player);

                if (session != null) {
                    session.send(new RoomQueueStatusMessageComposer(this.getQueueCount(roomId, player)));
                }
            }
        }
    }

    /**
     * Adds player to queue to this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     */
    public void addPlayerToQueue(int roomId, int playerId) {
        if (!this.hasQueue(roomId)) {
            return;
        }

        this.getQueue(roomId).add(playerId);
    }

    /**
     * Returns the next player for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public int getNextPlayer(int roomId) {
        if (!this.hasQueue(roomId)) {
            return 0;
        }

        return this.getQueue(roomId).get(0);
    }

    /**
     * Returns the queue count for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public int getQueueCount(int roomId, int playerId) {
        if (!this.hasQueue(roomId)) {
            return 0;
        }

        int size = 0;

        for (int player : this.getQueue(roomId)) {
            if (player == playerId) {
                break;
            }

            size++;
        }

        return size;
    }
}
