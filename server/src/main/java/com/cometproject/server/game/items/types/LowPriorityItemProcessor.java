package com.cometproject.server.game.items.types;

import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.tasks.CometTask;
import com.cometproject.server.tasks.CometThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Describes low priority item processor behavior for the item subsystem.
 */
public class LowPriorityItemProcessor implements CometTask {
    private static final int processTime = 25;
    private static LowPriorityItemProcessor instance;
    private final Logger LOGGER = LoggerFactory.getLogger(LowPriorityItemProcessor.class.getName());
    private List<RoomItemFloor> itemsToProcess;

    /**
     * Creates a low priority item processor instance for the item subsystem.
     */
    public LowPriorityItemProcessor() {
        this.itemsToProcess = new CopyOnWriteArrayList<>();

        CometThreadManager.getInstance().executePeriodic(this, processTime, processTime, TimeUnit.MILLISECONDS);
    }

    /**
     * Returns the instance for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public static LowPriorityItemProcessor getInstance() {
        if (instance == null) {
            instance = new LowPriorityItemProcessor();
        }

        return instance;
    }

    /**
     * Returns the process time for this item contract.
     *
     * @param time Time supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getProcessTime(double time) {
        long realTime = Math.round(time * 1000 / processTime);

        if (realTime < 1) {
            realTime = 1;
        }

        return (int) realTime;
    }

    /**
     * Runs this item task.
     */
    @Override
    public void run() {
        List<RoomItemFloor> itemsToRemove = new ArrayList<>();

        for (RoomItemFloor roomItemFloor : itemsToProcess) {
            try {
                if (roomItemFloor.requiresTick()) {
                    roomItemFloor.tick();
                } else {
                    itemsToRemove.add(roomItemFloor);
                }
            } catch (Exception e) {
                LOGGER.error("Error while processing item " + roomItemFloor.getId() + " / " + roomItemFloor.getClass().getSimpleName(), e);
            }
        }

        this.itemsToProcess.removeAll(itemsToRemove);

        itemsToRemove.clear();
    }

    /**
     * Executes submit for this item contract.
     *
     * @param floorItem Floor item supplied by the caller.
     */
    public void submit(RoomItemFloor floorItem) {
        this.itemsToProcess.add(floorItem);
    }
}
