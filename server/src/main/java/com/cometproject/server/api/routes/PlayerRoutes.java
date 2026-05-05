package com.cometproject.server.api.routes;

import com.cometproject.server.api.ApiRequestUtils;
import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.server.storage.queries.player.inventory.InventoryDao;
import io.javalin.http.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class PlayerRoutes {
    public static void reloadPlayerData(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        if (!PlayerManager.getInstance().isOnline(playerId)) {
            ApiResponseUtils.error(context, 404, "player_not_online", "Player is not online.");
            return;
        }

        Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (session == null) {
            ApiResponseUtils.error(context, 404, "session_not_found", "Unable to find the player's session.");
            return;
        }

        PlayerData newPlayerData = PlayerDao.getDataById(playerId);
        PlayerData currentPlayerData = session.getPlayer().getData();

        if (newPlayerData == null) {
            ApiResponseUtils.error(context, 404, "player_data_not_found", "Unable to find the player's new data.");
            return;
        }

        final boolean sendCurrencies = (newPlayerData.getCredits() != currentPlayerData.getCredits()) ||
                (newPlayerData.getActivityPoints() != currentPlayerData.getActivityPoints()) ||
                (newPlayerData.getVipPoints() != currentPlayerData.getVipPoints() || (newPlayerData.getSeasonalPoints() != currentPlayerData.getSeasonalPoints()));

        currentPlayerData.setRank(newPlayerData.getRank());
        currentPlayerData.setMotto(newPlayerData.getMotto());
        currentPlayerData.setFigure(newPlayerData.getFigure());
        currentPlayerData.setGender(newPlayerData.getGender());
        currentPlayerData.setEmail(newPlayerData.getEmail());

        currentPlayerData.setCredits(newPlayerData.getCredits());
        currentPlayerData.setVipPoints(newPlayerData.getVipPoints());
        currentPlayerData.setActivityPoints(newPlayerData.getActivityPoints());
        currentPlayerData.setSeasonalPoints(newPlayerData.getSeasonalPoints());

        currentPlayerData.setAchievementPoints(newPlayerData.getAchievementPoints());
        currentPlayerData.setFavouriteGroup(newPlayerData.getFavouriteGroup());
        currentPlayerData.setVip(newPlayerData.isVip());

        if (sendCurrencies) {
            session.getPlayer().sendBalance();
        }

        session.getPlayer().poof();

        ApiResponseUtils.success(context, Map.of(
                "player_id", playerId,
                "reloaded", true
        ));
    }

    public static void disconnect(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        if (!PlayerManager.getInstance().isOnline(playerId)) {
            ApiResponseUtils.error(context, 404, "player_not_online", "Player is not online.");
            return;
        }

        Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (session == null) {
            ApiResponseUtils.error(context, 404, "session_not_found", "Unable to find the player's session.");
            return;
        }

        session.disconnect();

        ApiResponseUtils.success(context, Map.of(
                "player_id", playerId,
                "disconnected", true
        ));
    }

    public static void alert(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        if (!PlayerManager.getInstance().isOnline(playerId)) {
            ApiResponseUtils.error(context, 404, "player_not_online", "Player is not online.");
            return;
        }

        Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (session == null) {
            ApiResponseUtils.error(context, 404, "session_not_found", "Unable to find the player's session.");
            return;
        }

        String title = ApiRequestUtils.firstNonBlank(
                ApiRequestUtils.bodyField(context, "title"),
                context.queryParam("title")
        );

        if (title == null)
            title = "Notification";

        String alert = ApiRequestUtils.firstNonBlank(
                ApiRequestUtils.bodyField(context, "message"),
                context.queryParam("message")
        );

        if (StringUtils.isBlank(alert)) {
            ApiResponseUtils.error(context, 400, "missing_message", "Message is required.");
            return;
        }

        session.send(new AdvancedAlertMessageComposer(title, alert));
        ApiResponseUtils.success(context, Map.of(
                "player_id", playerId,
                "alert_sent", true
        ));
    }

    public static void giveBadge(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        String badgeId = context.pathParam("badge");

        if (StringUtils.isBlank(badgeId)) {
            ApiResponseUtils.error(context, 400, "invalid_badge", "Badge code is required.");
            return;
        }

        if (!PlayerManager.getInstance().isOnline(playerId)) {
            InventoryDao.addBadge(badgeId, playerId);
        } else {
            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

            if (badgeId != null && session != null) {
                if (!session.getPlayer().getInventory().hasBadge(badgeId))
                    session.getPlayer().getInventory().addBadge(badgeId, true);
            }
        }

        ApiResponseUtils.success(context, Map.of(
                "player_id", playerId,
                "badge", badgeId,
                "awarded", true
        ));
    }
}
