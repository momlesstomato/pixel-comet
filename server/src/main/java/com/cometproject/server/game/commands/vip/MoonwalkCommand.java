package com.cometproject.server.game.commands.vip;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes moonwalk command behavior for the Comet subsystem.
 */
public class MoonwalkCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer().getEntity().isMoonwalking()) {
            client.getPlayer().getEntity().setIsMoonwalking(false);

            sendNotif(Locale.get("command.moonwalk.disabled"), client);
            return;
        }

        if (client.getPlayer().getEntity().getMountedEntity() != null) {
            return;
        }

        client.getPlayer().getEntity().setIsMoonwalking(true);

        sendNotif(Locale.get("command.moonwalk.enabled"), client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "moonwalk_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.moonwalk.description");
    }
}
