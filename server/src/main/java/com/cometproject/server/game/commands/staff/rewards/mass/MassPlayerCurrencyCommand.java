package com.cometproject.server.game.commands.staff.rewards.mass;

import com.cometproject.server.config.Locale;

/**
 * Staff command for granting a configured player currency to all online players.
 */
public final class MassPlayerCurrencyCommand extends MassCurrencyCommand {
    /**
     * Returns the permission key.
     *
     * @return the permission key.
     */
    @Override
    public String getPermission() {
        return "mass_currency_command";
    }

    /**
     * Returns the parameter hint.
     *
     * @return the parameter hint.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.masscurrency.parameter", "%amount% %currency_code%");
    }

    /**
     * Returns the command description.
     *
     * @return the command description.
     */
    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.masscurrency.description", "Grant a configured currency to all online players.");
    }
}
