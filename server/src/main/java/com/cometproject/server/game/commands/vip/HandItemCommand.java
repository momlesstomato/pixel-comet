package com.cometproject.server.game.commands.vip;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes hand item command behavior for the Comet subsystem.
 */
public class HandItemCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {

        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.handitem.none", "You have to type :drink %number%"), client);
            return;
        }

        try {
            int handItem = Integer.parseInt(params[0]);

            if (handItem > 0) {
                client.getPlayer().getEntity().carryItem(handItem, false);
            }
        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.handitem.invalid", "Please, use numbers only!"), client);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return Locale.get("handitem_command");
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.handitem.description");
    }
}
