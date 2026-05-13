package com.cometproject.api.events;

import java.util.function.Consumer;

/**
 * Base module event subscription.
 *
 * @param <T> the argument type consumed by this event listener.
 */
public abstract class Event<T extends EventArgs> {

    private final Consumer<T> callback;

    /**
     * Creates an event listener wrapper.
     *
     * @param callback the listener callback to invoke when the event is published.
     */
    public Event(Consumer<T> callback) {
        this.callback = callback;
    }

    /**
     * Invokes the registered listener callback.
     *
     * @param args the event arguments.
     */
    public void consume(T args) {
        this.callback.accept(args);
    }

    /**
     * Indicates whether this listener should be invoked asynchronously.
     *
     * @return true when the listener should run on the async event executor.
     */
    public boolean isAsync() {
        return false;
    }
}
