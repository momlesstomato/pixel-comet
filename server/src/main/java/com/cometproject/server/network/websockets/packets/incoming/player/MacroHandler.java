package com.cometproject.server.network.websockets.packets.incoming.player;

import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes macro handler behavior for the networking subsystem.
 */
public class MacroHandler extends AbstractWebSocketHandler<MacroHandler.ASMData> {

    /**
     * Creates a macro handler instance for the networking subsystem.
     */
    public MacroHandler() {
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
        /*
        Session s = NetworkManager.getInstance().getSessions().getByPlayerId(Integer.parseInt(eventData.session));

        if(s != null && s.getPlayer() != null){

            switch (eventData.macro){
                case "navigator":
                    s.send(new MassEventMessageComposer("navigator/search/"));
                    break;
                case "inventory":
                    s.send(new MassEventMessageComposer("inventory/open/furni"));
                    break;
                case "catalog":
                    s.send(new MassEventMessageComposer("catalog/open"));
                    break;
            }
        }
        */

    }


    class ASMData {
        private String session;
        private String macro;
    }
}
