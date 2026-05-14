package com.cometproject.server.game.commands.staff.bundles;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;

/**
 * Describes VIP command behavior for the Comet subsystem.
 */
public class VIPCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) return;

        final String username = params[0];
        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if(session != null && session.getPlayer() != null) {
            session.send(new NotificationMessageComposer("vip_bundle", Locale.getOrDefault("vip.acquired", "Acabas de recibir el lote VIP, disfruta de tus 180 Rubíes para adquirir la subscripción. Haz click aquí para ir a la tienda."), "catalog/open/club_buy"));
            session.getPlayer().getData().increaseCurrency(
                    CometBootstrap.resolve(ICurrencyService.class).currencyCodeForUseCase(CurrencyUseCases.STAFF_BUNDLE_PRIMARY),
                    240);
            client.getPlayer().sendBalance();
            client.getPlayer().getData().save();
        }

    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "vipbundle_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.vipbundle.description", "Da un pack de VIP al usuario seleccionado.");
    }
}
