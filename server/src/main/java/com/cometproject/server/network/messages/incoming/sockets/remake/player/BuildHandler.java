package com.cometproject.server.network.messages.incoming.sockets.remake.player;

import com.cometproject.server.network.sessions.Session;
import com.google.gson.JsonObject;

/**
 * Describes build handler behavior for the network message subsystem.
 */
public class BuildHandler {
    /**
     * Creates a build handler instance for the network message subsystem.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public BuildHandler(Session client, JsonObject msg) {
        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null)
            return;

        if (msg.get("type").getAsString() != null || !msg.get("type").getAsString().equals("") || msg.get("value").getAsString() != null || !msg.get("value").getAsString().equals("")) {
            String type = msg.get("type").getAsString();
            String value = msg.get("value").getAsString();

            switch (type){
                case "rotation":
                    client.getPlayer().setItemPlacementRotation(Integer.parseInt(value));
                    break;
                case "hauteur":
                    client.getPlayer().setItemPlacementHeight(Double.parseDouble(value));
                    break;
                case "etat":
                    client.getPlayer().setItemPlacementState(Integer.parseInt(value));
                    break;
            }
        }
    }

    /**
     * Indicates whether numeric applies to this network message contract.
     *
     * @param str Str supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}