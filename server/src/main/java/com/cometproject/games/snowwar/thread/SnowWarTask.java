package com.cometproject.games.snowwar.thread;

import com.cometproject.games.snowwar.SnowPlayerQueue;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.games.snowwar.tasks.*;

/**
 * Describes snow war task behavior for the Snow War game subsystem.
 */
public class SnowWarTask extends GameTask {
    public SnowWarRoom room;

    /**
     * Adds task to this Snow War game contract.
     *
     * @param task Task supplied by the caller.
     * @param initDelay Init delay supplied by the caller.
     * @param repeatDelay Repeat delay supplied by the caller.
     */
    public static void addTask(GameTask task, int initDelay, int repeatDelay) {
        WorkerTasks.addTask(task, initDelay, repeatDelay, WorkerTasks.SnowWarTasks);
    }

    /**
     * Creates a snow war task instance for the Snow War game subsystem.
     *
     * @param snowRoom Snow room supplied by the caller.
     */
    public SnowWarTask(SnowWarRoom snowRoom) {
        this.room = snowRoom;
    }

    /**
     * Runs this Snow War game task.
     */
    public void run() {
        try {
            // TODO: Add RoomStatus 7 for RematchView
            if (this.room.STATUS == 6) {
                this.future.cancel(false);
                SnowArenaEnd.exec(this.room);
                return;
            }
            if (this.room.STATUS == 5) {
                SnowArenaRun.exec(this.room);
                return;
            }
            if (this.room.STATUS == 4) {
                SnowStageRun.exec(this.room);
                this.room.STATUS = 5;
                return;
            }
            if (this.room.STATUS == 3) {
                SnowStageStarting.exec(this.room);
                this.room.STATUS = 4;
                addTask(this, 6000, 150);
                return;
            }
            if (this.room.STATUS == 2) {
                SnowStageLoading.exec(this.room);
                if (this.room.STATUS == 3) {
                    this.future.cancel(false);
                    addTask(this, 6000, 0);
                }
                return;
            }
            if (this.room.STATUS == 1 && this.room.TimeToStart-- == 0) {
                this.future.cancel(false);
                SnowPlayerQueue.roomLoaded(this.room);
                this.room.STATUS = 2;
                addTask(this, 100, 200);
            }
        } catch (Exception ex) {
            this.future.cancel(false);
            ex.printStackTrace();
            System.out.println("SnowEngine " + ex);
        }
    }
}