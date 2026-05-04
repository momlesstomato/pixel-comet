package com.cometproject.server.game.catalog.purchase.handlers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.cometproject.api.game.catalog.types.ICatalogItem;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.catalog.purchase.PurchaseHandler;
import com.cometproject.server.game.catalog.purchase.PurchaseResult;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.filter.FilterResult;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;

public class TrophyPurchaseHandler implements PurchaseHandler {
    private static final DateTimeFormatter TROPHY_DATE_FORMAT = DateTimeFormatter.ofPattern("d-M-yyyy");

    @Override
    public PurchaseResult handlePurchaseData(Session session, String purchaseData, ICatalogItem catalogItem, int amount) {

        if (!session.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(purchaseData);

            if (filterResult.isBlocked()) {
                filterResult.sendLogToStaffs(session, "PurchaseTrophy");
                session.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                return null;
            } else if (filterResult.wasModified()) {
                purchaseData = filterResult.getMessage();
            }
        }

        return new PurchaseResult(1, session.getPlayer().getData().getUsername()
                + Character.toChars(9)[0]
            + LocalDate.now().format(TROPHY_DATE_FORMAT) +
                Character.toChars(9)[0] + purchaseData);
    }
}
