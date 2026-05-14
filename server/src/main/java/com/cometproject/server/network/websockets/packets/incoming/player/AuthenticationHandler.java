package com.cometproject.server.network.websockets.packets.incoming.player;

import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.cometproject.server.network.websockets.packets.incoming.AbstractWebSocketHandler;

/**
 * Describes authentication handler behavior for the networking subsystem.
 */
public class AuthenticationHandler extends AbstractWebSocketHandler<AuthenticationHandler.AuthenticationData> {

    /**
     * Creates a authentication handler instance for the networking subsystem.
     */
    public AuthenticationHandler() {
        super(AuthenticationData.class);
    }

    /**
     * Executes handle for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param authenticationData Authentication data supplied by the caller.
     */
    @Override
    public void handle(WebSocketClientConnection ctx, AuthenticationData authenticationData) {
        if(authenticationData.ssoTicket.isEmpty() || !isNumeric(authenticationData.ssoTicket))
            return;

        Session s = NetworkManager.getInstance().getSessions().getByPlayerId(Integer.parseInt(authenticationData.ssoTicket));

        if(s == null)
            return;

        if(s.getPlayer().antiSpam(getClass().getName(), 0.5))
            return;

        System.out.print("[WS] - Assigned context handler for user " + s.getPlayer().getData().getUsername() + ".\n");

        s.setWsChannel(ctx);

    }


    class AuthenticationData {
        private String ssoTicket;
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
}
