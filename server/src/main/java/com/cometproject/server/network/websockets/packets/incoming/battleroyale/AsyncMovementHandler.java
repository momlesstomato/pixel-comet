package com.cometproject.server.network.websockets.packets.incoming.battleroyale;

import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes async movement handler behavior for the networking subsystem.
 */
public class AsyncMovementHandler extends AbstractWebSocketHandler<AsyncMovementHandler.ASMData> {

    /**
     * Creates a async movement handler instance for the networking subsystem.
     */
    public AsyncMovementHandler() {
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

        if(s != null && s.getPlayer() != null && s.getPlayer().getEntity() != null){
            if(s.getPlayer().antiSpam(getClass().getName(), 0.5))
                return;

            PlayerEntity player = s.getPlayer().getEntity();

            if (player.hasAttribute("tpencours")) {
                return;
            }

            if (player.hasAttribute("warp")) {
                return;
            }

            if (player.hasAttribute(("tptpencours"))) {
                return;
            }

            switch (eventData.direction){
                case 37:
                    player.moveTo(player.getPosition().getX() - 1, player.getPosition().getY());
                    break;
                case 38:
                    player.moveTo(player.getPosition().getX(), player.getPosition().getY() - 1);
                    break;
                case 39:
                    player.moveTo(player.getPosition().getX() + 1, player.getPosition().getY());
                    break;
                case 40:
                    player.moveTo(player.getPosition().getX(), player.getPosition().getY() + 1);
                    break;
            }
        }

    }

    class ASMData {
        private String session;
        private Integer direction;
    }
}
