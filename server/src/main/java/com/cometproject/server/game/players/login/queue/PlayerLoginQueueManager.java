package com.cometproject.server.game.players.login.queue;

import com.cometproject.server.tasks.CometThreadManager;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * Manages player login queue runtime state for the player subsystem.
 */
public class PlayerLoginQueueManager {
    private static final int WAIT_TIME = 100;

    private final PlayerLoginQueue loginQueue;
    private ScheduledFuture future;

    /**
     * Creates a player login queue manager instance for the player subsystem.
     *
     * @param autoStart Auto start supplied by the caller.
     * @param threadMgr Thread mgr supplied by the caller.
     */
    public PlayerLoginQueueManager(boolean autoStart, CometThreadManager threadMgr) {
        this.loginQueue = new PlayerLoginQueue();
        if (autoStart) {
            this.start(threadMgr);
        }
    }

    private void start(CometThreadManager threadMgr) {
        if (this.future != null) {
            return;
        }
        this.future = threadMgr.executePeriodic(this.loginQueue, WAIT_TIME, WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * Executes queue for this player contract.
     *
     * @param entry Entry supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean queue(PlayerLoginQueueEntry entry) {
        return this.loginQueue.queue(entry);
    }
}
