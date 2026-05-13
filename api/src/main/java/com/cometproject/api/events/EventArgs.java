package com.cometproject.api.events;

/**
 * Base argument object passed to module event listeners.
 *
 * <p>Event arguments are mutable only when a concrete event explicitly documents
 * which fields listeners may change. The cancellation flag is shared by
 * synchronous cancellable events and is inspected by {@link EventHandler}.
 */
public abstract class EventArgs {

    private boolean cancelled = false;

    /**
     * Updates whether the event has been cancelled by a listener.
     *
     * @param cancelled true when the publisher should stop the requested action.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns whether a listener cancelled the event.
     *
     * @return true when the requested action should be stopped.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }
}
