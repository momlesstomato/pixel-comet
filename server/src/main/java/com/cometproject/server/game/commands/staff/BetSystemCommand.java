package com.cometproject.server.game.commands.staff;

import com.cometproject.api.config.CometSettings;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes bet system command behavior for the Comet subsystem.
 */
public class BetSystemCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (CometSettings.casinoFreeRolls) {
            CometSettings.setCasinoFreeRolls(false);
            sendNotif(Locale.getOrDefault("command.betsystem.disabled", "Acabas de desactivar las apuestas gratuitas."), client);
        } else {
            CometSettings.setCasinoFreeRolls(true);
            sendNotif(Locale.getOrDefault("command.betsystem.enabled", "Acabas de activar las apuestas gratuitas."), client);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "betsystem_command";
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
        return Locale.get("command.betsystem.description");
    }
}
