package com.cometproject.server.game.commands.vip;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes fast walk command behavior for the Comet subsystem.
 */
public class FastWalkCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        client.getPlayer().getEntity().toggleFastWalk();

        if (client.getPlayer().getEntity().isFastWalkEnabled()) {
            sendWhisper(Locale.get("command.fastwalk.enabled"), client);
        } else {
            sendWhisper(Locale.get("command.fastwalk.disabled"), client);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "fastwalk_command";
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
        return Locale.get("command.fastwalk.description");
    }

    /**
     * Indicates whether this Comet contract can disable.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean canDisable() {
        return true;
    }
}
