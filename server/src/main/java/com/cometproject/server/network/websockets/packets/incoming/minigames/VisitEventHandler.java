package com.cometproject.server.network.websockets.packets.incoming.minigames;

import com.cometproject.api.config.CometSettings;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes visit event handler behavior for the networking subsystem.
 */
public class VisitEventHandler extends AbstractWebSocketHandler<VisitEventHandler.EventData> {

    /**
     * Creates a visit event handler instance for the networking subsystem.
     */
    public VisitEventHandler() {
        super(EventData.class);
    }

    /**
     * Executes handle for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param eventData Event data supplied by the caller.
     */
    @Override
    public void handle(WebSocketClientConnection ctx, EventData eventData) {
        Session s = NetworkManager.getInstance().getSessions().getByPlayerId(Integer.parseInt(eventData.session));

        if(s != null) {
            s.send(new RoomForwardMessageComposer(CometSettings.currentEventRoom));
        }

    }

    class EventData {
        private String roomId;
        private String session;
    }
}
