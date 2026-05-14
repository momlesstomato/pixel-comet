package com.cometproject.server.network.websockets.packets.incoming.player;

import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes builder sync handler behavior for the networking subsystem.
 */
public class BuilderSyncHandler extends AbstractWebSocketHandler<BuilderSyncHandler.ASMData> {

    /**
     * Creates a builder sync handler instance for the networking subsystem.
     */
    public BuilderSyncHandler() {
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
        if(!isNumeric(eventData.value))
            return;

        Session s = NetworkManager.getInstance().getSessions().getByPlayerId(Integer.parseInt(eventData.session));

        if(s != null && s.getPlayer() != null && s.getPlayer().getEntity() != null){
            if(s.getPlayer().antiSpam(getClass().getName(), 0.5))
                return;

            PlayerEntity player = s.getPlayer().getEntity();

            switch (eventData.type){
                case "rotation":
                    player.getPlayer().setItemPlacementRotation(Integer.parseInt(eventData.value));
                    break;
                case "hauteur":
                    player.getPlayer().setItemPlacementHeight(Double.parseDouble(eventData.value));
                    break;
                case "etat":
                    player.getPlayer().setItemPlacementState(Integer.parseInt(eventData.value));
                    break;
            }

            //s.send(new NotificationMessageComposer("newuser", "Socket type: " + eventData.type + "\nSocket value: " + eventData.value, ""));
        }

    }

    /**
     * Indicates whether numeric applies to this networking contract.
     *
     * @param str Str supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    class ASMData {
        private String session;
        private String type;
        private String value;
    }
}
