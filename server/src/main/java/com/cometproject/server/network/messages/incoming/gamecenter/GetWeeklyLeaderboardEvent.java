package com.cometproject.server.network.messages.incoming.gamecenter;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.gamecenter.LastWeekLeaderboardComposer;
import com.cometproject.server.network.messages.outgoing.gamecenter.WeeklyLeaderboardComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get weekly leaderboard event published by the network message subsystem.
 */
public class GetWeeklyLeaderboardEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int gameId = msg.readInt();


            client.send(new WeeklyLeaderboardComposer(gameId));
            client.send(new LastWeekLeaderboardComposer(gameId));
            //client.send(new LuckyLosersComposer(gameId));
    }
}
