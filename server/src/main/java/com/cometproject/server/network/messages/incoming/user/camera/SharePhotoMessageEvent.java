package com.cometproject.server.network.messages.incoming.user.camera;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.composers.camera.PurchasedPhotoMessageComposer;
import com.cometproject.server.composers.catalog.UnseenItemsMessageComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.purse.UpdateActivityPointsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.data.Data;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.common.collect.Sets;

/**
 * Represents the share photo message event published by the network message subsystem.
 */
public class SharePhotoMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String code = client.getPlayer().getLastPhoto();
        final long time = System.currentTimeMillis();
        final String photoUrl = CometSettings.cameraPhotoUrl.replace("%photoId%", code);

        final String itemExtraData = "{\"t\":" + time + ",\"u\":\"" + code + "\",\"n\":\"" +
                client.getPlayer().getData().getUsername() + "\",\"m\":\"\",\"s\":" + client.getPlayer().getId() + ",\"w\":\"" +
                photoUrl + "\"}";

        final Data<Long> itemIdData = Data.createEmpty();

        if (!CometSettings.playerInfiniteBalance) {
            int pixelCost = 3;
            final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
            final String currencyCode = currencyService.currencyCodeForUseCase(CurrencyUseCases.CAMERA_SHARE);
            final ICurrencyDefinition definition = currencyService.definition(currencyCode);
            final int protocolCurrencyId = definition.getProtocolCurrencyId().orElse(0);

            if(client.getPlayer().getData().getCurrencyBalance(currencyCode) < pixelCost) {
                client.send(new AlertMessageComposer(Locale.get("catalog.error.notenough")));
                return;
            }

            client.getPlayer().getData().decreaseCurrency(currencyCode, pixelCost);
            client.send(new UpdateActivityPointsMessageComposer(client.getPlayer().getData().getCurrencyBalance(currencyCode), -pixelCost, protocolCurrencyId));
            client.getPlayer().composeCurrenciesBalance();
            client.getPlayer().getData().save();
        }

        StorageContext.getCurrentContext().getRoomItemRepository().createItem(client.getPlayer().getId(), CometSettings.cameraPhotoItemId, itemExtraData, itemIdData::set);

        final PlayerItem playerItem = new InventoryItem(itemIdData.get(), CometSettings.cameraPhotoItemId, itemExtraData);

        client.getPlayer().getInventory().addItem(playerItem);

        client.send(new NotificationMessageComposer("generic", Locale.getOrDefault("camera.photoTaken", "You successfully took a photo!")));
        client.send(new UpdateInventoryMessageComposer());

        client.send(new UnseenItemsMessageComposer(Sets.newHashSet(playerItem), ItemManager.getInstance()));
        client.send(new PurchasedPhotoMessageComposer());

        client.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_12, 1);
        StorageContext.getCurrentContext().getPhotoRepository().savePhoto(client.getPlayer().getId(), client.getPlayer().getEntity().getRoom().getId(), photoUrl, (int) time/1000);
    }
}
