package com.cometproject.server.network.messages.incoming;

import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Defines the event contract for the network message subsystem.
 */
public interface Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    void handle(Session client, MessageEvent msg) throws Exception;
}
