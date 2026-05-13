package com.cometproject.server.game.commands.staff.rewards;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;
import org.apache.commons.lang3.StringUtils;

/**
 * Staff command for adding or removing any configured non-credit currency.
 */
public final class CurrencyCommand extends ChatCommand {
    private String logDesc = "";

    /**
     * Applies a inventory adjustment to the requested player.
     *
     * @param client the staff session executing the command.
     * @param params command parameters: username, signed amount, optional currency code or protocol id.
     */
    @Override
    public void execute(final Session client, final String[] params) {
        if (params.length < 2 || !StringUtils.isNumeric(StringUtils.removeStart(params[1], "-"))) {
            return;
        }

        final Session targetSession = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);
        if (targetSession == null || targetSession.getPlayer() == null) {
            return;
        }

        final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
        final long amount = Long.parseLong(params[1]);
        final String currencyCode = this.currencyCode(currencyService, params);
        final CurrencySource source = new CurrencySource(
                "staff",
                Integer.toString(client.getPlayer().getId()),
                "chat_command",
                this.getPermission(),
                "Staff currency adjustment");

        final CurrencyMovementResult result = amount < 0
                ? currencyService.remove(targetSession.getPlayer().getId(), currencyCode, Math.abs(amount), source)
                : currencyService.add(targetSession.getPlayer().getId(), currencyCode, amount, source);
        final ICurrencyDefinition definition = currencyService.definition(result.getCurrencyCode());
        final String displayName = this.displayName(definition);

        targetSession.send(new AdvancedAlertMessageComposer(
                Locale.getOrDefault("command.currency.successtitle", "Currency updated"),
                Locale.getOrDefault("command.currency.successmessage", "Your %currency_name% balance changed by %amount%.")
                        .replace("%amount%", Long.toString(result.getDelta()))
                        .replace("%currency_name%", displayName)));
        targetSession.send(targetSession.getPlayer().composeCurrenciesBalance());

        this.logDesc = "Staff %s adjusted %u by %c %l"
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%u", targetSession.getPlayer().getData().getUsername())
                .replace("%c", Long.toString(result.getDelta()))
                .replace("%l", displayName);
    }

    /**
     * Returns the permission key for this command.
     *
     * @return the permission key.
     */
    @Override
    public String getPermission() {
        return "currency_command";
    }

    /**
     * Returns the user-facing parameter hint.
     *
     * @return the parameter hint.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.currency.parameter", "%username% %amount% %currency_code%");
    }

    /**
     * Returns the user-facing command description.
     *
     * @return the command description.
     */
    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.currency.description", "Adjust a configured currency balance.");
    }

    /**
     * Returns the audit log description for the last execution.
     *
     * @return the log description.
     */
    @Override
    public String getLoggableDescription() {
        return this.logDesc;
    }

    /**
     * Returns whether command executions should be logged.
     *
     * @return true because staff currency changes are auditable.
     */
    @Override
    public boolean Loggable() {
        return true;
    }

    private String currencyCode(final ICurrencyService currencyService, final String[] params) {
        if (params.length < 3 || StringUtils.isBlank(params[2])) {
            return currencyService.firstNonCreditCurrencyCode();
        }

        if (StringUtils.isNumeric(params[2])) {
            return currencyService.currencyCodeForProtocolId(Integer.parseInt(params[2]));
        }

        return currencyService.resolveCurrencyCode(params[2]);
    }

    private String displayName(final ICurrencyDefinition definition) {
        if (StringUtils.isNotBlank(definition.getNounPlural())) {
            return definition.getNounPlural();
        }

        return definition.getDisplayName();
    }
}
