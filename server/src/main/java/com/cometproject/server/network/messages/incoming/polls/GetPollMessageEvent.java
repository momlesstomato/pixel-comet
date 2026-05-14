package com.cometproject.server.network.messages.incoming.polls;

import com.cometproject.server.game.polls.PollManager;
import com.cometproject.server.game.polls.types.Poll;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.polls.PollMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get poll message event published by the network message subsystem.
 */
public class GetPollMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int pollId = msg.readInt();

        Poll poll = PollManager.getInstance().getPollbyId(pollId);

        if (poll == null) {
            return;
        }

        client.send(new PollMessageComposer(poll));
    }
}