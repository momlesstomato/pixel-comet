package com.cometproject.api.events;

/**
 * Marks an event as cancellable by plugin listeners.
 */
public interface Cancellable {
    /**
     * Returns whether a listener cancelled the event.
     *
     * @return true when the publisher should stop the requested action.
     */
    boolean isCancelled();

    /**
     * Updates whether the event is cancelled.
     *
     * @param cancelled true when the publisher should stop the requested action.
     */
    void setCancelled(boolean cancelled);
}
