package com.cometproject.server.game.commands.staff.rewards.mass;

import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;
import org.apache.commons.lang3.StringUtils;


/**
 * Describes mass currency command behavior for the Comet subsystem.
 */
public abstract class MassCurrencyCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1 || params[0].isEmpty() || !StringUtils.isNumeric(params[0]))
            return;

        final int amount = Integer.parseInt(params[0]);
        final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
        final String currencyCode = this.currencyCode(currencyService, params);
        final ICurrencyDefinition definition = currencyService.definition(currencyCode);
        final String displayName = StringUtils.defaultIfBlank(definition.getNounPlural(), definition.getDisplayName());
        final CurrencySource source = new CurrencySource(
                "staff",
                Integer.toString(client.getPlayer().getId()),
                "chat_command",
                this.getPermission(),
                "Staff mass currency adjustment");

        for (ISession session : NetworkManager.getInstance().getSessions().getSessions().values()) {
            try {
                if(session == null || session.getPlayer() == null)
                    continue;

                final CurrencyMovementResult result = currencyService.add(
                        session.getPlayer().getId(),
                        currencyCode,
                        amount,
                        source);

                session.send(new NotificationMessageComposer(
                        StringUtils.defaultIfBlank(definition.getIconKey(), "currency"),
                        Locale.getOrDefault("command.currency.successmessage", "Your %currency_name% balance changed by %amount%.")
                                .replace("%amount%", Long.toString(result.getDelta()))
                                .replace("%currency_name%", displayName)
                ));

                session.getPlayer().sendBalance();
            } catch (Exception ignored) {

            }
        }

        this.logDesc = "Staff %s adjusted all online players by %n %c"
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%n", Integer.toString(amount))
                .replace("%c", displayName);
    }

    /**
     * Indicates whether async applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAsync() {
        return true;
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription(){
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable(){
        return true;
    }

    private String currencyCode(final ICurrencyService currencyService, final String[] params) {
        if (params.length < 2 || StringUtils.isBlank(params[1])) {
            return this.defaultCurrencyCode(currencyService);
        }

        if (StringUtils.isNumeric(params[1])) {
            return currencyService.currencyCodeForProtocolId(Integer.parseInt(params[1]));
        }

        return currencyService.resolveCurrencyCode(params[1]);
    }

    /**
     * Returns the default currency used when the command omits a code.
     *
     * @param currencyService the currency service.
     * @return the default currency code.
     */
    protected String defaultCurrencyCode(final ICurrencyService currencyService) {
        return currencyService.firstNonCreditCurrencyCode();
    }
}
