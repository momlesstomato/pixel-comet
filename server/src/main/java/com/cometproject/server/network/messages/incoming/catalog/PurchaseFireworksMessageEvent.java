package com.cometproject.server.network.messages.incoming.catalog;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.items.FireworkDataChargesMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.purse.UpdateActivityPointsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;

public class PurchaseFireworksMessageEvent
implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int spriteId = msg.readInt();
        int type = msg.readInt();
        int pixelCost = 5;
        int fireworkIncrement = 10;
        final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
        final String currencyCode = currencyService.currencyCodeForUseCase(CurrencyUseCases.FIREWORKS_PURCHASE);
        final ICurrencyDefinition definition = currencyService.definition(currencyCode);
        final int protocolCurrencyId = definition.getProtocolCurrencyId().orElse(0);

        if (client.getPlayer().getData().getCurrencyBalance(currencyCode) >= pixelCost) {
            if (client.getPlayer().getData().getCurrencyBalance(currencyCode) < pixelCost) {
                client.send(new AlertMessageComposer(Locale.get("catalog.error.notenough")));
                return;
            }
            client.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_75, fireworkIncrement);
            client.getPlayer().getData().decreaseCurrency(currencyCode, pixelCost);
            client.send(new UpdateActivityPointsMessageComposer(client.getPlayer().getData().getCurrencyBalance(currencyCode), -pixelCost, protocolCurrencyId));
            client.getPlayer().composeCurrenciesBalance();
            client.getPlayer().getData().save();
            client.getPlayer().getStats().incrementFireworks(fireworkIncrement);
            client.getPlayer().getStats().saveFireworks();
            client.getPlayer().getSession().send(new FireworkDataChargesMessageComposer(spriteId, client.getPlayer().getStats().getFireworks()));
        }
    }
}
