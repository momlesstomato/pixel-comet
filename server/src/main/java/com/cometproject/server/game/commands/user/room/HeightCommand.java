package com.cometproject.server.game.commands.user.room;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes height command behavior for the Comet subsystem.
 */
public class HeightCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        double height;

        if (params.length != 1){
            client.getPlayer().getEntity().setzok = false;
            sendNotif("Setz Apagado", client);
            client.flush();
            return;
        }

        try {
            height = Double.parseDouble(params[0]);
        } catch (Exception e) {
            height = -1;
        }

        if (height < -100 || height > 100) {
            sendNotif(Locale.get("command.height.invalid"), client);
            client.getPlayer().getEntity().setzok = false;
            client.flush();
            return;
        }

        client.getPlayer().setItemPlacementHeight(height);
        client.getPlayer().getEntity().setzok = true;
        client.flush();
        sendNotif(Locale.get("command.height.set").replace("%height%", "" + height), client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "height_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.height.param", "%height%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.height.description");
    }
}

