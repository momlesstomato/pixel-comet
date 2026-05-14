package com.cometproject.server.game.players.login.queue;

import com.cometproject.server.tasks.CometThreadManager;


/**
 * Describes static player queue behavior for the player subsystem.
 */
public class StaticPlayerQueue {
    private static PlayerLoginQueueManager mgr;

    static {

    }

    /**
     * Executes init for this player contract.
     *
     * @param threadManagement Thread management supplied by the caller.
     */
    public static void init(CometThreadManager threadManagement) {
        mgr = new PlayerLoginQueueManager(true, threadManagement);
    }

    /**
     * Returns the queue manager for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public static PlayerLoginQueueManager getQueueManager() {
        return mgr;
    }
}
