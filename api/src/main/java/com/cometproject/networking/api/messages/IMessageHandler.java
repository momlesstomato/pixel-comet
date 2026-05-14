package com.cometproject.networking.api.messages;

import com.cometproject.api.networking.messages.IMessageEvent;
import com.cometproject.networking.api.sessions.INetSession;

/**
 * Defines the i message handler contract for the networking subsystem.
 */
public interface IMessageHandler<T extends INetSession> {
    /**
     * Executes the handle message operation for this networking contract.
     *
     * @param messageEvent Message event value supplied by the caller.
     * @param session Session value supplied by the caller.
     */
    void handleMessage(final IMessageEvent messageEvent, T session);
}
