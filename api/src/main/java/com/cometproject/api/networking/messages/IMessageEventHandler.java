package com.cometproject.api.networking.messages;

/**
 * Defines the i message event handler contract for the networking subsystem.
 */
public interface IMessageEventHandler {
    /**
     * Executes the handle operation for this networking contract.
     *
     * @param eventData Event data value supplied by the caller.
     * @throws Exception When the implementation cannot complete the operation.
     */
    void handle(IMessageEvent eventData) throws Exception;
}
