package com.cometproject.server.network.websockets.packets.incoming.minigames;

import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.nuxs.NuxGiftSelectionViewMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes accept minigame suggestion handler behavior for the networking subsystem.
 */
public class AcceptMinigameSuggestionHandler extends AbstractWebSocketHandler<AcceptMinigameSuggestionHandler.ASMData> {

    /**
     * Creates a accept minigame suggestion handler instance for the networking subsystem.
     */
    public AcceptMinigameSuggestionHandler() {
        super(ASMData.class);
    }

    /**
     * Executes handle for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param eventData Event data supplied by the caller.
     */
    @Override
    public void handle(WebSocketClientConnection ctx, ASMData eventData) {
        Session s = NetworkManager.getInstance().getSessions().getByPlayerId(Integer.parseInt(eventData.session));
        String enemyName = eventData.username;

        if(s == null || s.getPlayer() == null || enemyName.equals("")) {
            return;
        }

        if(s.getPlayer().antiSpam(getClass().getName(), 0.5))
            return;

        s.getPlayer().setRPSRival(enemyName);
        Session enemy = NetworkManager.getInstance().getSessions().getByPlayerUsername(enemyName);

        if(enemy == null || enemy.getPlayer() == null || enemy.getPlayer().getRPSRival().equals("")){
            s.getPlayer().resetRPS();
            s.getPlayer().sendBubble("games", "Tu enemigo no está disponible en estos momentos.");
            return;
        }

        if (enemy.getPlayer().getRPSRival().contains(s.getPlayer().getData().getUsername()) && s.getPlayer().getRPSRival().contains(enemyName)){
            s.send(new NuxGiftSelectionViewMessageComposer(4));
            enemy.send(new NuxGiftSelectionViewMessageComposer(4));
        }
    }

    class ASMData {
        private String username;
        private String session;
    }
}
