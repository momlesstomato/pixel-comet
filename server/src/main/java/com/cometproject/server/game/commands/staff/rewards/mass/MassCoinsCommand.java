package com.cometproject.server.game.commands.staff.rewards.mass;

import com.cometproject.server.config.Locale;
import com.cometproject.storage.api.services.ICurrencyService;


public class MassCoinsCommand extends MassCurrencyCommand {
    @Override
    public String getPermission() {
        return "masscoins_command";
    }

    @Override
    public String getParameter() {
        return "";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.masscoins.description");
    }

    /**
     * Uses credits as the default because this is the protocol-special credit mass command.
     *
     * @param currencyService the currency service.
     * @return the credits currency code.
     */
    @Override
    protected String defaultCurrencyCode(final ICurrencyService currencyService) {
        return "credits";
    }
}
