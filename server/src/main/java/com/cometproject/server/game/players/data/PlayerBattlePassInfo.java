package com.cometproject.server.game.players.data;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.composers.catalog.UnseenItemsMessageComposer;
import com.cometproject.server.game.achievements.BattlePassGlobals;
import com.cometproject.server.game.achievements.types.BattlePassMission;
import com.cometproject.server.game.achievements.types.BattlePassRewardEnum;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.data.Data;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Describes player battle pass info behavior for the player subsystem.
 */
public class PlayerBattlePassInfo {
    public int playerId;
    public int level;
    public int exp;
    public ArrayList<Integer> completedMissions = new ArrayList<Integer>();

    /**
     * Creates a player battle pass info instance for the player subsystem.
     *
     * @param level Level supplied by the caller.
     * @param exp Exp supplied by the caller.
     */
    public PlayerBattlePassInfo(int level, int exp){
        this.level = level;
        this.exp = exp;
    }

    /**
     * Adds experience point to this player contract.
     *
     * @param mission Mission supplied by the caller.
     */
    public void addExperiencePoint(int mission){
        if(this.level != mission) return;

        BattlePassMission ms = BattlePassGlobals.battlePassMissions.stream().filter(x -> x.id == mission).findAny().orElse(null);
        if(ms == null) return;

        int matches = 0;
        for(int completed : completedMissions){
            if(mission == completed) matches++;
        }

        if(matches >= ms.maxExp) return;

        Session player = NetworkManager.getInstance().getSessions().getByPlayerId(this.playerId);
        if(player == null) return;

        this.exp++;
        matches++;

        completedMissions.add(mission);
        PlayerDao.InsertBattlePassCompleted(mission, this.playerId);


        if(matches == ms.maxExp){
            player.send(new NotificationMessageComposer("generic", "¡Felicidades! Has completado el nivel " + ms.id + "."));
            this.level++;
            this.giveReward(ms, player);
        }
        else player.send(new NotificationMessageComposer("generic", "Has conseguido 1 exp en el nivel " + ms.id + " del GalaxyPass."));

        PlayerDao.UpdateBattlePass(this);
        player.getPlayer().getData().updateBattlePass();
    }

    /**
     * Executes give reward for this player contract.
     *
     * @param ms Ms supplied by the caller.
     * @param player Player participating in the operation.
     */
    public void giveReward(BattlePassMission ms, Session player){
        if(ms.rewardType == BattlePassRewardEnum.RewardType.BADGE){
            player.getPlayer().getInventory().addBadge(ms.rewardReference, true);
            player.send(new NotificationMessageComposer("generic", "Has recibido una placa como recompensa por subir al nivel " + this.level));
        }

        if(ms.rewardType == BattlePassRewardEnum.RewardType.CURRENCY){
            try{
                int amount = Integer.parseInt(ms.rewardReference);
                final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
                final ICurrencyDefinition definition = currencyService.definition(currencyService.firstNonCreditCurrencyCode());

                player.getPlayer().getData().increaseCurrency(definition.getCode(), amount);
                player.getPlayer().sendBalance();
                player.send(new NotificationMessageComposer("generic", "Has recibido " + amount + " "
                        + StringUtils.defaultIfBlank(definition.getNounPlural(), definition.getDisplayName())
                        + " como recompensa por subir al nivel " + this.level));
            }
            catch (Exception ex) { }
        }

        if(ms.rewardType == BattlePassRewardEnum.RewardType.RARE){
            try{
                int itemId = Integer.parseInt(ms.rewardReference);

                final String itemExtraData = "0";

                final Data<Long> itemIdData = Data.createEmpty();

                StorageContext.getCurrentContext().getRoomItemRepository().createItem(player.getPlayer().getId(), itemId, itemExtraData, itemIdData::set);

                final PlayerItem playerItem = new InventoryItem(itemIdData.get(), itemId, itemExtraData);

                player.getPlayer().getInventory().addItem(playerItem);

                player.send(new UpdateInventoryMessageComposer());

                player.send(new UnseenItemsMessageComposer(Sets.newHashSet(playerItem), ItemManager.getInstance()));
                player.send(new NotificationMessageComposer("generic", "Has recibido un rare como recompensa por subir al nivel " + this.level));
            }
            catch (Exception ex) { }
        }

        if(ms.id == 10){
            final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
            player.getPlayer().getInventory().addBadge("GLXF5", true);
            player.getPlayer().getData().increaseCurrency(
                    currencyService.currencyCodeForUseCase(CurrencyUseCases.BATTLE_PASS_REWARD),
                    5);
            player.getPlayer().getData().flush();
        }
    }

    /**
     * Executes count exp mission for this player contract.
     *
     * @param mission Mission supplied by the caller.
     * @return Result produced by the operation.
     */
    public int countExpMission(int mission){
        int matches = 0;
        for(int completed : completedMissions){
            if(mission == completed) matches++;
        }

        return matches;
    }
}
