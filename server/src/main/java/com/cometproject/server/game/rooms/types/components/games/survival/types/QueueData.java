package com.cometproject.server.game.rooms.types.components.games.survival.types;

/**
 * Carries queue data data for the room processing subsystem.
 */
public class QueueData {
    public int playerId;
    public String username;
    public String figure;

    /**
     * Creates a queue data instance for the room processing subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param username Username supplied by the caller.
     * @param figure Figure supplied by the caller.
     */
    public QueueData(int playerId, String username, String figure){
        this.playerId = playerId;
        this.username = username;
        this.figure = figure;
    }
}
