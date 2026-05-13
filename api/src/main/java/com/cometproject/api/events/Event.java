package com.cometproject.api.events;

/**
 * Base payload object for module events.
 *
 * <p>Events carry their own data and are dispatched by their concrete class,
 * similar to Bukkit-style plugin events. This avoids pairing a separate event
 * wrapper with an argument object at runtime.
 */
public abstract class Event {
    private final boolean async;

    /**
     * Creates a synchronous event.
     */
    protected Event() {
        this(false);
    }

    /**
     * Creates an event with an explicit async dispatch hint.
     *
     * @param async true when listeners may be dispatched on the async event executor.
     */
    protected Event(final boolean async) {
        this.async = async;
    }

    /**
     * Indicates whether listeners may run asynchronously.
     *
     * @return true when the event allows async listener dispatch.
     */
    public boolean isAsync() {
        return this.async;
    }
}
