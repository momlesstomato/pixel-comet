package com.cometproject.server.network.websockets.packets.incoming.battleroyale;

import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalQueue;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes battle royale leave queue handler behavior for the networking subsystem.
 */
public class BattleRoyaleLeaveQueueHandler extends AbstractWebSocketHandler<BattleRoyaleLeaveQueueHandler.ASMData> {

    /**
     * Creates a battle royale leave queue handler instance for the networking subsystem.
     */
    public BattleRoyaleLeaveQueueHandler() {
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
        int playerId = Integer.parseInt(eventData.session);
        int roomId = Integer.parseInt(eventData.roomId);
        Session s = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if(s != null){
            if(s.getPlayer().antiSpam(getClass().getName(), 0.5))
                return;

            if(SurvivalQueue.getInstance().playerHasQueue(roomId, playerId)){
                SurvivalQueue.getInstance().removePlayerFromQueue(roomId, playerId, s.getPlayer().getQueueData());
            }
        }

    }

    class ASMData {
        private String session;
        private String roomId;
    }
}
