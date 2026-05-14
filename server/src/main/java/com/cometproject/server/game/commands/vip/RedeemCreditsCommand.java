package com.cometproject.server.game.commands.vip;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * Describes redeem credits command behavior for the Comet subsystem.
 */
public class RedeemCreditsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        int coinsToGive = 0;
        int diamondsToGive = 0;

        List<Long> itemsToRemove = Lists.newArrayList();

        if (!client.getPlayer().getInventory().itemsLoaded()) {
            sendNotif(Locale.getOrDefault("command.redeemcredits.inventory", "Please open your inventory before executing this command!"), client);
            return;
        }

        for (PlayerItem playerItem : client.getPlayer().getInventory().getInventoryItems().values()) {
            if (playerItem == null || playerItem.getDefinition() == null) continue;

            String itemName = playerItem.getDefinition().getItemName();

            if (itemName.startsWith("CF_") || itemName.startsWith("CFC_") || itemName.startsWith("DIA_")) {
                try {
                    if (itemName.contains("DIA_")) {
                        diamondsToGive += Integer.parseInt(itemName.split("_")[1]);
                    } else {
                        coinsToGive += Integer.parseInt(itemName.split("_")[1]);
                    }

                    itemsToRemove.add(playerItem.getId());

                    StorageContext.getCurrentContext().getRoomItemRepository().deleteItem(playerItem.getId());
                } catch (Exception ignored) {

                }
            }
        }

        if (itemsToRemove.size() == 0) {
            return;
        }

        for (long itemId : itemsToRemove) {
            client.getPlayer().getInventory().removeItem(itemId);
        }

        itemsToRemove.clear();

        client.send(new UpdateInventoryMessageComposer());

        if (diamondsToGive > 0) {
            client.getPlayer().getData().increaseCurrency(
                    CometBootstrap.resolve(ICurrencyService.class).currencyCodeForUseCase(CurrencyUseCases.EXCHANGE_SECONDARY),
                    diamondsToGive);
        }

        if (coinsToGive > 0) {
            client.getPlayer().getData().increaseCredits(coinsToGive);
        }

        if (diamondsToGive > 0 || coinsToGive > 0) {
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
        return "redeemcredits_command";
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
        return Locale.get("command.redeemcredits.description");
    }
}
