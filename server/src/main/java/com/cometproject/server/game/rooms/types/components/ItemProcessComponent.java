package com.cometproject.server.game.rooms.types.components;

import com.cometproject.api.game.rooms.objects.IRoomItemData;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.objects.entities.WiredTriggerExecutor;
import com.cometproject.server.game.rooms.objects.items.RoomItem;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.objects.items.types.floor.RollerFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerPeriodically;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.tasks.CometTask;
import com.cometproject.server.tasks.CometThreadManager;
import com.cometproject.server.utilities.TimeSpan;
import com.cometproject.storage.api.StorageContext;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * Owns item process behavior inside the room processing subsystem.
 */
public class ItemProcessComponent implements CometTask {
    private static final int INTERVAL = 500;
    private static final int FLAG = 600;
    private final Room room;
    private final Logger LOGGER;

    private ScheduledFuture future;
    private ScheduledFuture saveFuture;

    private final Queue<RoomItem> saveQueue = Queues.newConcurrentLinkedQueue();

    private boolean active = false;

    /**
     * Creates a item process component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public ItemProcessComponent(Room room) {
        this.room = room;

        LOGGER = LoggerFactory.getLogger("Item Process [" + room.getData().getName() + "]");
    }

    /**
     * Starts this room processing component.
     */
    public void start() {
        if (Room.useCycleForItems) {
            this.active = true;
            return;
        }

        if (this.future != null && this.active) {
            stop();
        }

        this.active = true;

        this.future = CometThreadManager.getInstance().executePeriodic(this, 0, INTERVAL, TimeUnit.MILLISECONDS);
        this.saveFuture = CometThreadManager.getInstance().executePeriodic(this::saveQueueTick, 1000, 1000, TimeUnit.MILLISECONDS);

        LOGGER.debug("Processing started");
    }

    private void saveQueueTick() {
        RoomItem roomItem;

        final Set<IRoomItemData> items = Sets.newHashSet();

        while((roomItem = this.saveQueue.poll()) != null) {
            items.add(roomItem.getItemData());
        }

        StorageContext.getCurrentContext().getRoomItemRepository().saveItemBatch(items);
    }

    /**
     * Stops this room processing component.
     */
    public void stop() {
        if (Room.useCycleForItems) {
            this.active = false;
            return;
        }

        if (this.future != null) {
            this.active = false;

            this.future.cancel(false);
            this.saveFuture.cancel(false);

            this.saveQueueTick();

            LOGGER.debug("Processing stopped");
        }
    }

    /**
     * Indicates whether active applies to this room processing contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Processes tick for this room processing contract.
     */
    public void processTick() {
        if (!this.active) {
            return;
        }

        long timeStart = System.currentTimeMillis();

        if (this.getRoom().getEntities().realPlayerCount() == 0) return;

        final Set<String> positionsWithPeriodicTrigger = new HashSet<>();

        for (RoomItemFloor item : this.getRoom().getItems().getFloorItems().values()) {
            try {
                if (item != null && item.requiresTick() || item instanceof RollerFloorItem) {
                    if (item instanceof WiredTriggerPeriodically) {
                        final String posStr = item.getPosition().getX() + "_" + item.getPosition().getY();

                        if (positionsWithPeriodicTrigger.contains(posStr)) {
                            continue;
                        } else {
                            positionsWithPeriodicTrigger.add(posStr);
                        }
                    }

                    if (item.isStateSwitched()) {
                        item.restoreState();
                    }

                    item.tick();
                }
            } catch (Exception e) {
                this.handleException(item, e);
            }
        }

        for (RoomItemWall item : this.getRoom().getItems().getWallItems().values()) {
            try {
                if (item != null && item.requiresTick()) {
                    item.tick();
                }
            } catch (Exception e) {
                this.handleException(item, e);
            }
        }

        TimeSpan span = new TimeSpan(timeStart, System.currentTimeMillis());

        if (span.toMilliseconds() > FLAG && Comet.isDebugging) {
            LOGGER.warn("ItemProcessComponent process took: " + span.toMilliseconds() + "ms to execute.");
        }
    }

    /**
     * Runs this room processing task.
     */
    @Override
    public void run() {
        this.processTick();
    }

    /**
     * Executes queue action for this room processing contract.
     *
     * @param action Action supplied by the caller.
     */
    public void queueAction(WiredTriggerExecutor action) {
        // TODO: monitor this
        CometThreadManager.getInstance().executeOnce(action);
    }

    /**
     * Persists item for this room processing contract.
     *
     * @param roomItem Room item supplied by the caller.
     */
    public void saveItem(RoomItem roomItem) {
        this.saveQueue.remove(roomItem);

        this.saveQueue.add(roomItem);
    }

    /**
     * Handles exception for this room processing contract.
     *
     * @param item Item supplied by the caller.
     * @param e E supplied by the caller.
     */
    protected void handleException(RoomItem item, Exception e) {
        LOGGER.error("Error while processing item: " + item.getId() + " (" + item.getClass().getSimpleName() + ")", e);
    }

    /**
     * Returns the room for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Room getRoom() {
        return this.room;
    }

}
