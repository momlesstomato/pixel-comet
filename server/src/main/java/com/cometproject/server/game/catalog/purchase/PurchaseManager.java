package com.cometproject.server.game.catalog.purchase;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.furniture.types.GiftData;
import com.cometproject.server.composers.catalog.GiftUserNotFoundMessageComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.catalog.purchase.handlers.BotPurchaseHandler;
import com.cometproject.server.game.catalog.purchase.handlers.PetPurchaseHandler;
import com.cometproject.server.game.catalog.purchase.handlers.StickiesPurchaseHandler;
import com.cometproject.server.game.catalog.purchase.handlers.TrophyPurchaseHandler;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages purchase runtime state for the catalog subsystem.
 */
public class PurchaseManager {
    private static PurchaseManager purchaseManager;

    private final Map<String, PurchaseHandler> handlers;

    /**
     * Creates a purchase manager instance for the catalog subsystem.
     */
    public PurchaseManager() {
        this.handlers = new HashMap<>();

        this.handlers.put("trophy", new TrophyPurchaseHandler());
        this.handlers.put("postit", new StickiesPurchaseHandler());
        this.handlers.put("pet", new PetPurchaseHandler());
        this.handlers.put("bot", new BotPurchaseHandler());
    }

    /**
     * Handles purchase for this catalog contract.
     *
     * @param session Session participating in the operation.
     * @param pageId Page id supplied by the caller.
     * @param itemId Item id supplied by the caller.
     * @param data Data supplied by the caller.
     * @param amount Amount supplied by the caller.
     * @param giftData Gift data supplied by the caller.
     */
    public void handlePurchase(Session session, int pageId, int itemId, String data, int amount, GiftData giftData) {
        if (amount > 100) {
            session.send(new AlertMessageComposer(Locale.get("catalog.error.toomany")));
            return;
        }

        final int playerIdToDeliver = giftData == null ? -1 : PlayerDao.getIdByUsername(giftData.getReceiver());

        if (giftData != null) {
            if (playerIdToDeliver == 0) {
                session.send(new GiftUserNotFoundMessageComposer());
                return;
            } else {
                session.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_25, 1);
            }
        }

    }

    /**
     * Returns the instance for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public PurchaseManager getInstance() {
        return purchaseManager;
    }
}
