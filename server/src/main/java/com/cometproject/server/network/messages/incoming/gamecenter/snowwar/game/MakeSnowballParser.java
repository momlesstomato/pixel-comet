package com.cometproject.server.network.messages.incoming.gamecenter.snowwar.game;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Describes make snowball parser behavior for the network message subsystem.
 */
public class MakeSnowballParser implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    public void handle(Session client, MessageEvent msg) throws Exception {
        msg.readInt();
        msg.readInt();
        client.snowWarPlayerData.makeSnowBall();
    }
}