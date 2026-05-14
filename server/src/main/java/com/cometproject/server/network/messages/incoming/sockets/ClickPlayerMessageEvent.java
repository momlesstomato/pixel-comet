package com.cometproject.server.network.messages.incoming.sockets;

import com.cometproject.server.game.rooms.types.components.games.RoomGame;
import com.cometproject.server.game.rooms.types.components.games.survival.SurvivalGame;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalPlayer;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the click player message event published by the network message subsystem.
 */
public class ClickPlayerMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if(client.getPlayer().getEntity() == null)
            return;

        int userId = msg.readInt();

        if(client.getPlayer().getEntity().isSurvivalMode()) {
            Session player = NetworkManager.getInstance().getSessions().getByPlayerId(userId);
            if(player == null || player.getPlayer() == null || player.getPlayer().getEntity() == null)
                return;

            final RoomGame game = client.getPlayer().getEntity().getRoom().getGame().getInstance();

            if (!(game instanceof SurvivalGame))
                return;

            final SurvivalGame survivalGame = (SurvivalGame) game;
            final SurvivalPlayer survivalPlayer = survivalGame.survivalPlayer(client.getPlayer().getEntity().getPlayerId());

            if(survivalPlayer == null)
                return;

            if (survivalPlayer.getBullets() < 1) return;

            final SurvivalPlayer survivalEnemy = survivalGame.survivalPlayer(userId);

            if(survivalEnemy == null)
                return;

            survivalGame.playerShots(survivalPlayer, survivalEnemy);
        }
    }
}
