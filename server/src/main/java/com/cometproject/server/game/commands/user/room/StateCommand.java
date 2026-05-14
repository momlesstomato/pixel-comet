package com.cometproject.server.game.commands.user.room;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes state command behavior for the Comet subsystem.
 */
public class StateCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        int state;

        try {
            state = Integer.parseInt(params[0]);
        } catch (Exception e) {
            state = -1;
        }

        if (state < -1 || state > 100) {
            sendNotif(Locale.get("command.height.invalid"), client);
            return;
        }

        client.getPlayer().setItemPlacementState(state);
        sendNotif(Locale.get("command.height.set").replace("%height%", "" + state), client);
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

