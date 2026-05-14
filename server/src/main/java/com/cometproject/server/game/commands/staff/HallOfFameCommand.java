package com.cometproject.server.game.commands.staff;

import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes hall of fame command behavior for the Comet subsystem.
 */
public class HallOfFameCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        final String username = params[0];
        final String badgeCode = params[1];

        if (client.getPlayer() != null) {
            // Check if they already have the badge
        } else {
            // Disconnect
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return null;
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return null;
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return null;
    }
}
