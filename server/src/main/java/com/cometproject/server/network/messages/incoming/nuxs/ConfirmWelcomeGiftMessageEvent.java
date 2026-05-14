package com.cometproject.server.network.messages.incoming.nuxs;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;

/**
 * Represents the confirm welcome gift message event published by the network message subsystem.
 */
public class ConfirmWelcomeGiftMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int amount = msg.readInt();
        try {
            final String currencyCode = CometBootstrap.resolve(ICurrencyService.class)
                    .currencyCodeForUseCase(CurrencyUseCases.CASINO_BET);

            if (amount < 6 || client.getPlayer().getData().getCurrencyBalance(currencyCode) < amount) {
                client.getPlayer().sendBubble("inters", Locale.getOrDefault("command.setbet.minimum", "El mínimo de apuesta son 6 de Fichas, si dispones de ellos vuelve a hacer click, para conseguir Koins debes ganar algun evento o conseguirlos en el calendario."));
                return;
            } else if (amount > 100) {
                client.getPlayer().sendBubble("inters", Locale.getOrDefault("command.setbet.limit", "No puedes apostar más de 50 de Fichas, se te ha asignado una apuesta de 50."));
                amount = 100;
            }

            client.getPlayer().getEntity().setBetAmount(amount);
            client.getPlayer().sendBubble("inters", Locale.getOrDefault("command.setbet.set", "Has colocado tus apuestas en %s Fichas.").replace("%s", amount + ""));
        } catch (Exception e) {
            client.getPlayer().sendBubble("inters", Locale.getOrDefault("command.setbet.invalid", "Por favor, introduce únicamente valores numéricos"));
        }
    }
}
