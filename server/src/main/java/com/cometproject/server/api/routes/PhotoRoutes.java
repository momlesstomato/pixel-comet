package com.cometproject.server.api.routes;

import java.util.Map;

import com.cometproject.server.api.ApiRequestUtils;
import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;

import io.javalin.http.Context;

public class PhotoRoutes {
    public static void purchase(final Context context) {
        final String ssoTicket = ApiRequestUtils.firstNonBlank(
                context.header("sso_ticket"),
                context.header("ssoTicket")
        );
        final String photoId = ApiRequestUtils.firstNonBlank(
                context.header("photo_id"),
                context.header("photoId")
        );
        final Integer playerId = PlayerManager.getInstance().getPlayerIdByAuthToken(ssoTicket);

        if (playerId == null) {
            ApiResponseUtils.error(context, 401, "invalid_session_token", "Invalid session token.");
            return;
        }

        Session client = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (client == null) {
            ApiResponseUtils.error(context, 404, "session_not_found", "Cannot find session for the player.");
            return;
        }

        final long currentTime = System.currentTimeMillis();

        if (currentTime < (client.getPlayer().getLastPhotoTaken() + 10000)) {
            ApiResponseUtils.error(context, 429, "photo_rate_limited", "Taking photos too fast.");
            return;
        }

//        final String itemExtraData = "{\"t\":" + System.currentTimeMillis() + ",\"u\":\"" + photoId + "\",\"n\":\"" + client.getPlayer().getData().getUsername() + "\",\"m\":\"\",\"s\":" + client.getPlayer().getId() + ",\"w\":\"" + CometSettings.cameraPhotoUrl.replace("%photoId%", photoId) + "\"}";
//
//        long itemId = ItemDao.createItem(client.getPlayer().getId(), CometSettings.cameraPhotoItemId, itemExtraData);
//        final PlayerItem playerItem = new InventoryItem(itemId, CometSettings.cameraPhotoItemId, itemExtraData);
//
//        client.getPlayer().getInventory().addItem(playerItem);
//
//        client.send(new NotificationMessageComposer("generic", Locale.getOrDefault("camera.photoTaken", "You successfully took a photo!")));
//        client.send(new UpdateInventoryMessageComposer());
//
//        client.send(new UnseenItemsMessageComposer(Sets.newHashSet(playerItem)));
//
//        client.getPlayer().getAchievements().progressAchievement(AchievementType.CAMERA_PHOTO, 1);
    ApiResponseUtils.success(context, Map.of(
        "player_id", playerId,
        "photo_id", photoId,
        "accepted", true
    ));
    }
}
