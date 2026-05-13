package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.furniture.types.CrackableReward;
import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.api.game.quests.QuestType;
import com.cometproject.api.game.rooms.objects.data.LimitedEditionItemData;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.composers.catalog.UnseenItemsMessageComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.storage.queries.catalog.CraftingDao;
import com.cometproject.server.storage.queries.items.LimitedEditionDao;
import com.cometproject.server.utilities.RandomUtil;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.data.Data;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.math.NumberUtils;

public class CrackableFloorItem extends RoomItemFloor {

    public CrackableFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        if (!NumberUtils.isNumber(this.getItemData().getData()))
            this.getItemData().setData("0");
    }

    @Override
    public boolean onInteract(RoomEntity entity, int state, boolean isWiredTrigger) {
        final CrackableReward crackableReward = ItemManager.getInstance().getCrackableRewards().get(this.getItemData().getItemId());

        if (crackableReward == null) {
            return false;
        }

        if (isWiredTrigger || !(entity instanceof PlayerEntity)) {
            return false;
        }

        final Player player = ((PlayerEntity) entity).getPlayer();

        int hits = Integer.parseInt(this.getItemData().getData());
        int maxHits = crackableReward.getHitRequirement();

        if (hits >= maxHits - 1) {
            // we're open!
            this.getItemData().setData(hits);
            this.sendUpdate();

            switch (crackableReward.getRewardType()) {
                case ITEM:
                    // we need to turn into this item!
                    final FurnitureDefinition itemDefinition = ItemManager.getInstance().getDefinition(crackableReward.getRewardDataInt());

                    if (itemDefinition != null) {
                        this.getRoom().getItems().removeItem(this, player.getSession(), false);

                        this.getRoom().getItems().placeFloorItem(new InventoryItem(this.getId(), itemDefinition.getId(), crackableReward.getRewardData()), this.getPosition().getX(), this.getPosition().getY(), this.getRotation(), player, false);
                    }
                    break;

                case COINS:
                    player.getData().increaseCredits(crackableReward.getRewardDataInt());
                    player.sendBalance();
                    player.getData().save();
                    break;

                case VIP_POINTS:
                    player.getData().increaseCurrency(
                            currencyCodeForUseCase(CurrencyUseCases.CRACKABLE_REWARD_SECONDARY),
                            crackableReward.getRewardDataInt());
                    player.sendBalance();
                    player.getData().save();
                    break;

                case ACTIVITY_POINTS:
                    player.getData().increaseCurrency(
                            currencyCodeForUseCase(CurrencyUseCases.CRACKABLE_REWARD_PRIMARY),
                            crackableReward.getRewardDataInt());
                    player.sendBalance();
                    player.getData().save();
                    break;

                case EASTER:
                    Position eggPosition = this.getPosition();

                    if (eggPosition.distanceTo(entity.getPosition()) > 1) {
                        entity.moveTo(eggPosition.getX(), eggPosition.getY());
                        return false;
                    }

                    int result = RandomUtil.getRandomInt(1, 20);
                    int seasonalPrize = RandomUtil.getRandomInt(3, 5);
                    int diamondPrize = RandomUtil.getRandomInt(1, 2);

                    if (result == 1) {
                        if (CometSettings.globalEggsCrafted <= 10) {
                            LimitedEditionItemData ltdItem;

                            int newItemPrize = Integer.parseInt(Locale.getOrDefault("crackable.itemID", "228"));

                            long t;
                            FurnitureDefinition prizeDefinition = ItemManager.getInstance().getDefinition(newItemPrize);

                            if (prizeDefinition != null) {
                                final Data<Long> newItem = Data.createEmpty();
                                StorageContext.getCurrentContext().getRoomItemRepository().createItem(player.getData().getId(), newItemPrize, "", newItem::set);

                                t = newItem.get();
                                ltdItem = new LimitedEditionItemData(t, CometSettings.globalEggsCrafted, 10);

                                PlayerItem playerItem = new InventoryItem(newItem.get(), newItemPrize, "", null, ltdItem);

                                player.getInventory().addItem(playerItem);
                                player.getSession().send(new UpdateInventoryMessageComposer());
                                player.getSession().send(new UnseenItemsMessageComposer(Sets.newHashSet(playerItem), ItemManager.getInstance()));

                                LimitedEditionDao.save(ltdItem);
                                CraftingDao.updateLimitedEgg();
                            }
                        } else {
                            player.getData().increaseCurrency(
                                    currencyCodeForUseCase(CurrencyUseCases.CRACKABLE_REWARD_PRIMARY),
                                    seasonalPrize);
                            player.sendBalance();
                            player.getData().save();
                            player.sendBubble("eggSeasonal","¡Has recibido " + seasonalPrize + " Píxeles tras explotar el huevo!");
                        }
                    }

                    if(result >= 2 && result < 17) {
                        player.getData().increaseCurrency(
                                currencyCodeForUseCase(CurrencyUseCases.CRACKABLE_REWARD_PRIMARY),
                                seasonalPrize);
                        player.sendBalance();
                        player.getData().save();
                        player.sendBubble("eggSeasonal","¡Has recibido " + seasonalPrize + " Píxeles tras explotar el huevo!");
                    }

                    if(result > 17){
                        player.getData().increaseCurrency(
                                currencyCodeForUseCase(CurrencyUseCases.CRACKABLE_REWARD_SECONDARY),
                                diamondPrize);
                        player.sendBalance();
                        player.getData().save();
                        player.sendBubble("eggDiamond","¡Has recibido " + diamondPrize + " Amatistas tras explotar el huevo!");
                    }
                    break;

                case RANDOM:
                    final FurnitureDefinition randomPrize = ItemManager.getInstance().getDefinition(crackableReward.getRandomReward());

                    if (randomPrize != null) {
                        this.getRoom().getItems().removeItem(this, player.getSession(), false);
                        this.getRoom().getItems().placeFloorItem(new InventoryItem(this.getId(), randomPrize.getId(), ""), this.getPosition().getX(), this.getPosition().getY(), this.getRotation(), player, false);
                    }

                    break;

                case BADGE:
                    player.getInventory().addBadge(crackableReward.getRewardData(), true, true);
            }
                entity.getRoom().getItems().removeItem(this, player.getSession(), false, true);
        } else{
            hits++;
            player.getQuests().progressQuest(QuestType.EAS20_2, 1);
        }

        this.getItemData().setData(hits);
        this.sendUpdate();

        return true;
    }

    @Override
    public void composeItemData(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(7);

        int state = Integer.parseInt(this.getItemData().getData());
        final CrackableReward crackableReward = ItemManager.getInstance().getCrackableRewards().get(this.getItemData().getItemId());

        if (crackableReward != null) {
            msg.writeString(this.calculateState(crackableReward.getHitRequirement(), state));
            msg.writeInt(state);//state
            msg.writeInt(crackableReward.getHitRequirement());//max
        } else {
            msg.writeString(this.calculateState(20, state));
            msg.writeInt(state);//state
            msg.writeInt(20);//max
        }
    }

    private static String currencyCodeForUseCase(final String useCase) {
        return CometBootstrap.resolve(ICurrencyService.class).currencyCodeForUseCase(useCase);
    }

    private int calculateState(int maxHits, int currentHits) {
        return (int) Math.floor((1.0D / ((double) maxHits / (double) currentHits) * currentHits));
    }
}
