package com.cometproject.server.api.routes;

import java.util.Map;

import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.utilities.TimeSpan;

import io.javalin.http.Context;

/**
 * Handles the public server health status endpoint.
 *
 * <p>No authentication is required for this route. It is intended for health
 * monitoring, load-balancer probes, and client-facing availability checks.
 * Only non-sensitive runtime metrics are exposed.
 */
public final class StatusRoutes {
    private StatusRoutes() {
    }

    /**
     * Returns a lightweight server health snapshot.
     *
     * <p>Exposes uptime, online player count, and active room count.
     * Process details, memory, and OS information are intentionally omitted.
     *
     * @param context The Javalin request context.
     */
    public static void status(final Context context) {
        ApiResponseUtils.success(context, Map.of(
                "status", "up",
                "players_online", NetworkManager.getInstance().getSessions().getUsersOnlineCount(),
                "rooms_active", RoomManager.getInstance().getRoomInstances().size(),
                "uptime", TimeSpan.millisecondsToDate(System.currentTimeMillis() - Comet.start)
        ));
    }
}
