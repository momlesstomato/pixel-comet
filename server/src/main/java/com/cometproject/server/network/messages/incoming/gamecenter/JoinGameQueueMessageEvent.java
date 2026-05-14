package com.cometproject.server.network.messages.incoming.gamecenter;

import java.util.UUID;

import com.cometproject.games.snowwar.SnowPlayerQueue;
import com.cometproject.server.composers.gamecenter.GameStatusMessageComposer;
import com.cometproject.server.composers.gamecenter.LoadGameMessageComposer;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the join game queue message event published by the network message subsystem.
 */
public class JoinGameQueueMessageEvent implements Event {
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
        client.getPlayer().sendBubble("", "GameID: " + gameId);
        switch (gameId){
            case 1:
                final UUID sessionId = UUID.randomUUID();
                final String authToken = client.getPlayer().getId() + sessionId.toString();

                PlayerManager.getInstance().createAuthToken(client.getPlayer().getId(), authToken);
                client.registerAuthToken(authToken);

                client.send(new LoadGameMessageComposer(gameId, "http://localhost/url2/swf/games/gamecenter_basejump/BaseJump.swf", authToken, "localhost", "30010", "30010", "http://localhost/url2/swf/games/gamecenter_basejump/BasicAssets.swf"));
                break;
            case 2:
                SnowPlayerQueue.addPlayerInQueue(client);
                break;
            case 3:
                break;
        }

        client.send(new GameStatusMessageComposer(gameId, 0));
    }
}
