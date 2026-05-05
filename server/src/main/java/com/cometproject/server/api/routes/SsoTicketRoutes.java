package com.cometproject.server.api.routes;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.sso.SsoConfiguration;
import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.api.game.sso.SsoTicket;
import com.cometproject.server.api.ApiRequestUtils;
import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.boot.CometBootstrap;

import io.javalin.http.Context;

/**
 * Exposes authenticated management routes for issuing SSO tickets.
 */
public final class SsoTicketRoutes {
    private SsoTicketRoutes() {
    }

    /**
     * Issues a new one-shot SSO ticket for the supplied player id.
     *
     * @param context The active Javalin request context.
     */
    public static void issueTicket(final Context context) {
        final String rawPlayerId = ApiRequestUtils.bodyField(context, "player_id");

        if (!StringUtils.isNumeric(rawPlayerId)) {
            ApiResponseUtils.error(context, 400, "missing_player_id", "player_id is required.");
            return;
        }

        final int playerId = Integer.parseInt(rawPlayerId);

        if (playerId <= 0) {
            ApiResponseUtils.error(context, 400, "missing_player_id", "player_id must be greater than zero.");
            return;
        }

        final ISsoTicketService ssoTicketService = CometBootstrap.getCurrentInjector().getInstance(ISsoTicketService.class);
        final int ttlSeconds = Integer.parseInt(Configuration.currentConfig().getOrDefault(
                SsoConfiguration.TICKET_TTL_SECONDS,
                SsoConfiguration.defaults().get(SsoConfiguration.TICKET_TTL_SECONDS)));
        final SsoTicket ticket = ssoTicketService.issue(playerId, ttlSeconds);

        ApiResponseUtils.success(context, 201, Map.of(
                "ticket", ticket.ticket(),
                "player_id", ticket.playerId(),
                "expires_at", ticket.expiresAt().toString()
        ));
    }
}