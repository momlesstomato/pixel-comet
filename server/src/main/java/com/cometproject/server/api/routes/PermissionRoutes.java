package com.cometproject.server.api.routes;

import com.cometproject.server.api.ApiRequestUtils;
import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.storage.api.data.permissions.PermissionContext;
import com.cometproject.storage.api.data.permissions.PermissionEffect;
import com.cometproject.storage.api.data.permissions.PermissionGrantRequest;
import com.cometproject.storage.api.data.permissions.PermissionGroup;
import com.cometproject.storage.api.data.permissions.PermissionGroupMutation;
import com.cometproject.storage.api.data.permissions.PermissionNode;
import com.cometproject.storage.api.data.permissions.PermissionNodeMutation;
import com.cometproject.storage.api.data.permissions.PermissionSnapshot;
import com.cometproject.storage.api.data.permissions.PlayerPermissionGroup;
import com.cometproject.storage.api.services.IPermissionService;
import io.javalin.http.Context;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Javalin handlers for permission group and membership management.
 */
public final class PermissionRoutes {
    private PermissionRoutes() {
    }

    /**
     * Lists permission groups.
     *
     * @param context the active Javalin request context.
     */
    public static void listGroups(final Context context) {
        final boolean includeDisabled = Boolean.parseBoolean(context.queryParam("include_disabled"));
        final List<Map<String, Object>> groups = permissionService().groups(includeDisabled)
                .stream()
                .map(PermissionRoutes::groupResponse)
                .toList();

        ApiResponseUtils.success(context, Map.of("groups", groups));
    }

    /**
     * Creates or updates a permission group.
     *
     * @param context the active Javalin request context.
     */
    public static void upsertGroup(final Context context) {
        final String pathCode = context.pathParamMap().get("group_code");
        final String code = ApiRequestUtils.firstNonBlank(pathCode, ApiRequestUtils.bodyField(context, "code"));

        if (StringUtils.isBlank(code)) {
            ApiResponseUtils.error(context, 400, "invalid_group_code", "Permission group code is required.");
            return;
        }

        final PermissionGroup existingGroup = existingGroup(code);
        final String displayName = ApiRequestUtils.firstNonBlank(
                ApiRequestUtils.bodyField(context, "display_name"),
                existingGroup == null ? null : existingGroup.displayName(),
                code);

        try {
            final PermissionGroup group = permissionService().upsertGroup(new PermissionGroupMutation(
                    code,
                    displayName,
                    bodyInt(context, "priority", existingGroup == null ? 0 : existingGroup.priority()),
                    bodyInt(context, "legacy_rank_id", existingGroup == null ? 1 : existingGroup.legacyRankId()),
                    ApiRequestUtils.bodyBoolean(context, "staff", existingGroup != null && existingGroup.staff()),
                    ApiRequestUtils.bodyBoolean(
                            context,
                            "default_group",
                            existingGroup != null && existingGroup.defaultGroup()),
                    ApiRequestUtils.bodyBoolean(context, "enabled", existingGroup == null || existingGroup.enabled())));

            ApiResponseUtils.success(context, Map.of("group", groupResponse(group)));
        } catch (IllegalStateException exception) {
            ApiResponseUtils.error(context, 409, "permission_group_cancelled", exception.getMessage());
        }
    }

    /**
     * Loads one permission group.
     *
     * @param context the active Javalin request context.
     */
    public static void getGroup(final Context context) {
        final String groupCode = context.pathParam("group_code");
        final List<PermissionGroup> groups = permissionService().groups(true);

        groups.stream()
                .filter(group -> group.code().equals(groupCode.toLowerCase(Locale.ROOT)))
                .findFirst()
                .ifPresentOrElse(
                        group -> ApiResponseUtils.success(context, Map.of("group", groupResponse(group))),
                        () -> ApiResponseUtils.error(
                                context,
                                404,
                                "permission_group_not_found",
                                "Permission group was not found."));
    }

    /**
     * Disables one permission group.
     *
     * @param context the active Javalin request context.
     */
    public static void disableGroup(final Context context) {
        try {
            permissionService().disableGroup(context.pathParam("group_code"));
            ApiResponseUtils.success(context, Map.of("disabled", true));
        } catch (IllegalStateException exception) {
            ApiResponseUtils.error(context, 409, "permission_group_cancelled", exception.getMessage());
        }
    }

    /**
     * Lists permission nodes assigned to a group.
     *
     * @param context the active Javalin request context.
     */
    public static void listGroupNodes(final Context context) {
        final List<Map<String, Object>> nodes = permissionService().groupNodes(context.pathParam("group_code"))
                .stream()
                .map(PermissionRoutes::nodeResponse)
                .toList();

        ApiResponseUtils.success(context, Map.of("permissions", nodes));
    }

    /**
     * Creates or updates one permission node.
     *
     * @param context the active Javalin request context.
     */
    public static void upsertGroupNode(final Context context) {
        final String permissionNode = context.pathParam("permission_node");
        final PermissionEffect effect = effect(ApiRequestUtils.bodyField(context, "effect"));
        final String nodeContext = ApiRequestUtils.firstNonBlank(ApiRequestUtils.bodyField(context, "context"), "global");

        try {
            final PermissionNode node = permissionService().upsertGroupNode(
                    context.pathParam("group_code"),
                    new PermissionNodeMutation(
                            permissionNode,
                            effect,
                            nodeContext,
                            instant(ApiRequestUtils.bodyField(context, "expires_at"))));

            ApiResponseUtils.success(context, Map.of("permission", nodeResponse(node)));
        } catch (IllegalStateException exception) {
            ApiResponseUtils.error(context, 409, "permission_node_cancelled", exception.getMessage());
        }
    }

    /**
     * Removes one permission node from a group.
     *
     * @param context the active Javalin request context.
     */
    public static void removeGroupNode(final Context context) {
        try {
            permissionService().removeGroupNode(
                    context.pathParam("group_code"),
                    context.pathParam("permission_node"),
                    ApiRequestUtils.firstNonBlank(ApiRequestUtils.bodyField(context, "context"), "global"));
            ApiResponseUtils.success(context, Map.of("removed", true));
        } catch (IllegalStateException exception) {
            ApiResponseUtils.error(context, 409, "permission_node_cancelled", exception.getMessage());
        }
    }

    /**
     * Lists groups assigned to one player.
     *
     * @param context the active Javalin request context.
     */
    public static void listPlayerGroups(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "player_id");
        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_player_id", "Player id must be numeric.");
            return;
        }

        final boolean includeExpired = Boolean.parseBoolean(context.queryParam("include_expired"));
        final List<Map<String, Object>> groups = permissionService().playerGroups(playerId, includeExpired)
                .stream()
                .map(PermissionRoutes::membershipResponse)
                .toList();

        ApiResponseUtils.success(context, Map.of("groups", groups));
    }

    /**
     * Grants one group to a player.
     *
     * @param context the active Javalin request context.
     */
    public static void grantPlayerGroup(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "player_id");
        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_player_id", "Player id must be numeric.");
            return;
        }

        try {
            permissionService().grantGroupToPlayer(
                    playerId,
                    context.pathParam("group_code"),
                    new PermissionGrantRequest(
                            ApiRequestUtils.firstNonBlank(ApiRequestUtils.bodyField(context, "granted_by"), "api"),
                            ApiRequestUtils.firstNonBlank(ApiRequestUtils.bodyField(context, "reason"), ""),
                            instant(ApiRequestUtils.bodyField(context, "expires_at"))));
            ApiResponseUtils.success(context, Map.of("granted", true));
        } catch (IllegalStateException exception) {
            ApiResponseUtils.error(context, 409, "permission_grant_cancelled", exception.getMessage());
        }
    }

    /**
     * Revokes one group from a player.
     *
     * @param context the active Javalin request context.
     */
    public static void revokePlayerGroup(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "player_id");
        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_player_id", "Player id must be numeric.");
            return;
        }

        try {
            permissionService().revokeGroupFromPlayer(playerId, context.pathParam("group_code"));
            ApiResponseUtils.success(context, Map.of("revoked", true));
        } catch (IllegalStateException exception) {
            ApiResponseUtils.error(context, 409, "permission_revoke_cancelled", exception.getMessage());
        }
    }

    /**
     * Checks a player permission node.
     *
     * @param context the active Javalin request context.
     */
    public static void check(final Context context) {
        final Integer playerId = ApiRequestUtils.bodyInt(context, "player_id");
        final String permissionNode = ApiRequestUtils.bodyField(context, "permission_node");

        if (playerId == null || StringUtils.isBlank(permissionNode)) {
            ApiResponseUtils.error(context, 400, "invalid_permission_check", "Player id and permission node are required.");
            return;
        }

        ApiResponseUtils.success(context, Map.of(
                "check",
                permissionService().check(playerId, permissionNode, PermissionContext.global())));
    }

    /**
     * Returns the cached permission snapshot for a player.
     *
     * @param context the active Javalin request context.
     */
    public static void snapshot(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "player_id");
        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_player_id", "Player id must be numeric.");
            return;
        }

        ApiResponseUtils.success(context, Map.of("snapshot", snapshotResponse(permissionService().snapshot(playerId))));
    }

    /**
     * Reloads permission caches.
     *
     * @param context the active Javalin request context.
     */
    public static void reload(final Context context) {
        permissionService().reload();
        ApiResponseUtils.success(context, Map.of("reloaded", true));
    }

    private static IPermissionService permissionService() {
        return CometBootstrap.resolve(IPermissionService.class);
    }

    private static PermissionGroup existingGroup(final String groupCode) {
        return permissionService().groups(true).stream()
                .filter(group -> group.code().equals(groupCode.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElse(null);
    }

    private static Map<String, Object> groupResponse(final PermissionGroup group) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("code", group.code());
        response.put("display_name", group.displayName());
        response.put("priority", group.priority());
        response.put("legacy_rank_id", group.legacyRankId());
        response.put("staff", group.staff());
        response.put("default_group", group.defaultGroup());
        response.put("enabled", group.enabled());
        return response;
    }

    private static Map<String, Object> nodeResponse(final PermissionNode node) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("group_code", node.groupCode());
        response.put("permission_node", node.node());
        response.put("effect", node.effect().name());
        response.put("context", node.context());
        response.put("expires_at", node.expiresAt() == null ? null : node.expiresAt().toString());
        return response;
    }

    private static Map<String, Object> membershipResponse(final PlayerPermissionGroup membership) {
        final Map<String, Object> response = groupResponse(membership.group());
        response.put("player_id", membership.playerId());
        response.put("granted_by", membership.grantedBy());
        response.put("reason", membership.reason());
        response.put("expires_at", membership.expiresAt() == null ? null : membership.expiresAt().toString());
        return response;
    }

    private static Map<String, Object> snapshotResponse(final PermissionSnapshot snapshot) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("player_id", snapshot.playerId());
        response.put("highest_priority", snapshot.highestPriority());
        response.put("legacy_rank_id", snapshot.legacyRankId());
        response.put("legacy_rank_name", snapshot.legacyRankName());
        response.put("built_at", snapshot.builtAt().toString());
        response.put("groups", snapshot.groups().stream().map(PermissionRoutes::groupResponse).toList());
        response.put("permissions", snapshot.nodes().stream().map(PermissionRoutes::nodeResponse).toList());
        return response;
    }

    private static PermissionEffect effect(final String value) {
        if ("DENY".equalsIgnoreCase(value)) {
            return PermissionEffect.DENY;
        }

        return PermissionEffect.ALLOW;
    }

    private static Instant instant(final String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return Instant.parse(value);
    }

    private static int bodyInt(final Context context, final String key, final int fallback) {
        final Integer value = ApiRequestUtils.bodyInt(context, key);
        return value == null ? fallback : value;
    }
}
