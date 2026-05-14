package com.cometproject.server.network.messages.incoming.gamecenter;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.gamecenter.GameCenterAchievementsConfigurationComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get game leaderboards event published by the network message subsystem.
 */
public class GetGameLeaderboardsEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int gameId = msg.readInt();

        client.send(new GameCenterAchievementsConfigurationComposer(gameId, client.getPlayer().getAchievements()));
        //client.send(new Game2WeeklyLeaderboardParser(gameId, client.getPlayer().getData().getId()));
    }
}
