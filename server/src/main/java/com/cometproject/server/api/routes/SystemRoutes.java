package com.cometproject.server.api.routes;

import java.util.Map;

import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.boot.utils.ShutdownProcess;
import com.cometproject.server.composers.catalog.CatalogPublishMessageComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.commands.CommandManager;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.landing.LandingManager;
import com.cometproject.server.game.moderation.BanManager;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.navigator.NavigatorManager;
import com.cometproject.server.game.permissions.PermissionsManager;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.moderation.ModToolMessageComposer;
import com.cometproject.server.storage.queries.config.ConfigDao;

import io.javalin.http.Context;

/**
 * Registers system routes endpoints for the HTTP route subsystem.
 */
public class SystemRoutes {
    /**
     * Executes status for this HTTP route contract.
     *
     * @param context Context supplied by the caller.
     */
    public static void status(final Context context) {
        ApiResponseUtils.success(context, Map.of("status", Comet.getStats()));
    }

    /**
     * Executes shutdown for this HTTP route contract.
     *
     * @param context Context supplied by the caller.
     */
    public static void shutdown(final Context context) {
        try {
            ApiResponseUtils.success(context, Map.of("shutdown", true));
        } finally {
            ShutdownProcess.shutdown(true);
        }
    }

    /**
     * Executes reload for this HTTP route contract.
     *
     * @param context Context supplied by the caller.
     */
    public static void reload(final Context context) {
        String type = context.pathParam("type");

        if (type == null) {
            ApiResponseUtils.error(context, 400, "invalid_reload_type", "Invalid reload type.");
            return;
        }

        switch (type) {
            case "bans":
                BanManager.getInstance().loadBans();
                break;

            case "catalog":
                CatalogManager.getInstance().loadItemsAndPages();
                CatalogManager.getInstance().loadGiftBoxes();

                NetworkManager.getInstance().getSessions().broadcast(new CatalogPublishMessageComposer(true));
                break;

            case "navigator":
                NavigatorManager.getInstance().loadPublicRooms();
                break;

            case "permissions":
                PermissionsManager.getInstance().loadPermissions();
                PermissionsManager.getInstance().loadPerks();
                break;

            case "config":
                ConfigDao.getAll();
                break;

            case "news":
                LandingManager.getInstance().loadArticles();
                break;

            case "items":
                ItemManager.getInstance().loadItemDefinitions();
                break;

            case "filter":
                RoomManager.getInstance().getFilter().loadFilter();
                break;

            case "locale":
                Locale.reload();
                CommandManager.getInstance().reloadAllCommands();
                break;

            case "modpresets":
                ModerationManager.getInstance().loadPresets();

                ModerationManager.getInstance().getModerators().forEach((session -> {
                    session.send(new ModToolMessageComposer());
                }));
                break;

            default:
                ApiResponseUtils.error(context, 400, "invalid_reload_type", "Unsupported reload type.");
                return;
        }

        ApiResponseUtils.success(context, Map.of(
                "reload_type", type,
                "reloaded", true
        ));
    }

}
