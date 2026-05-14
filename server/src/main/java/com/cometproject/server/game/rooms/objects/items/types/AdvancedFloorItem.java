package com.cometproject.server.game.rooms.objects.items.types;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.state.FloorItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Describes advanced floor item behavior for the room subsystem.
 */
public abstract class AdvancedFloorItem<T extends FloorItemEvent> extends RoomItemFloor {
    private final Set<T> itemEvents = new ConcurrentHashSet<T>();

    /**
     * Creates a advanced floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public AdvancedFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the tick callback for this room contract.
     */
    @Override
    public void onTick() {
        final Set<T> finishedEvents = new HashSet<T>();

        for (T itemEvent : itemEvents) {
            itemEvent.incrementTicks();

            if (itemEvent.isFinished()) {
                finishedEvents.add(itemEvent);
            }
        }

        for (T finishedEvent : finishedEvents) {
            this.itemEvents.remove(finishedEvent);

            finishedEvent.onCompletion(this);

            if (finishedEvent.isInteractiveEvent()) {
                this.onEventComplete(finishedEvent);
            }
        }

        finishedEvents.clear();
    }

    /**
     * Executes queue event for this room contract.
     *
     * @param floorItemEvent Floor item event supplied by the caller.
     */
    public void queueEvent(final T floorItemEvent) {
        if (this.getMaxEvents() <= this.itemEvents.size()) {
            return;
        }

        this.itemEvents.add(floorItemEvent);
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    public abstract void onEventComplete(T event);

    /**
     * Returns the max events for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMaxEvents() {
        return 5000;
    }
}
