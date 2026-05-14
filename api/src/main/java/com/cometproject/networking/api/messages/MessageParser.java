package com.cometproject.networking.api.messages;

import com.cometproject.api.networking.messages.IMessageEvent;

/**
 * Describes message parser behavior for the networking subsystem.
 */
public abstract class MessageParser {
    private boolean hasError = false;

    /**
     * Indicates whether this networking contract has error.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    public boolean hasError() {
        return this.hasError;
    }

    /**
     * Updates the has error for this networking contract.
     *
     * @param error Error supplied by the caller.
     */
    public void setHasError(boolean error) {
        this.hasError = error;
    }

    /**
     * Executes the parse operation for this networking contract.
     *
     * @param event Event value supplied by the caller.
     */
    public abstract void parse(IMessageEvent event);

    /**
     * Executes the flush operation for this networking contract.
     */
    public void flush() {
        // Override if we need to dispose anything we previously allocated
    }
}
