package com.cometproject.server.network.messages.incoming.performance;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.misc.PingMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the request latency test message event published by the network message subsystem.
 */
public class RequestLatencyTestMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.setLastPing(Comet.getTime());
        client.send(new PingMessageComposer());
    }
}
