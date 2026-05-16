package com.cometproject.storage.api.services;

import com.cometproject.storage.api.data.permissions.PermissionCheckResult;
import com.cometproject.storage.api.data.permissions.PermissionContext;
import com.cometproject.storage.api.data.permissions.PermissionGrantRequest;
import com.cometproject.storage.api.data.permissions.PermissionGroup;
import com.cometproject.storage.api.data.permissions.PermissionGroupMutation;
import com.cometproject.storage.api.data.permissions.PermissionNode;
import com.cometproject.storage.api.data.permissions.PermissionNodeMutation;
import com.cometproject.storage.api.data.permissions.PermissionSnapshot;
import com.cometproject.storage.api.data.permissions.PlayerPermissionGroup;

import java.util.Collection;
import java.util.List;

/**
 * Resolves effective player permissions and coordinates permission mutations.
 */
public interface IPermissionService {
    /**
     * Checks whether a player has one permission node.
     *
     * @param playerId the player id.
     * @param node the requested node.
     * @return true when the permission is granted.
     */
    boolean hasPermission(int playerId, String node);

    /**
     * Checks whether a player has one permission node in a context.
     *
     * @param playerId the player id.
     * @param node the requested node.
     * @param context the runtime permission context.
     * @return true when the permission is granted.
     */
    boolean hasPermission(int playerId, String node, PermissionContext context);

    /**
     * Checks whether a player has any of the supplied nodes.
     *
     * @param playerId the player id.
     * @param nodes the requested nodes.
     * @return true when any permission is granted.
     */
    boolean hasAnyPermission(int playerId, Collection<String> nodes);

    /**
     * Checks whether an exact permission node is configured by any active group.
     *
     * @param node the requested node.
     * @return true when the node exists in the permission cache.
     */
    boolean isPermissionNodeDefined(String node);

    /**
     * Resolves one permission check with diagnostic metadata.
     *
     * @param playerId the player id.
     * @param node the requested node.
     * @param context the runtime permission context.
     * @return the permission check result.
     */
    PermissionCheckResult check(int playerId, String node, PermissionContext context);

    /**
     * Returns the cached effective snapshot for a player.
     *
     * @param playerId the player id.
     * @return the effective permission snapshot.
     */
    PermissionSnapshot snapshot(int playerId);

    /**
     * Returns the legacy rank id exposed to old packets and checks.
     *
     * @param playerId the player id.
     * @param fallbackRankId the fallback rank id.
     * @return the effective legacy rank id.
     */
    int legacyRankId(int playerId, int fallbackRankId);

    /**
     * Returns the highest effective group priority for a player.
     *
     * @param playerId the player id.
     * @param fallbackPriority the fallback priority.
     * @return the highest priority.
     */
    int highestPriority(int playerId, int fallbackPriority);

    /**
     * Lists all permission groups.
     *
     * @param includeDisabled whether disabled groups should be included.
     * @return permission groups.
     */
    List<PermissionGroup> groups(boolean includeDisabled);

    /**
     * Creates or updates a permission group.
     *
     * @param mutation the group mutation.
     * @return the persisted group.
     */
    PermissionGroup upsertGroup(PermissionGroupMutation mutation);

    /**
     * Disables one permission group.
     *
     * @param groupCode the stable group code.
     */
    void disableGroup(String groupCode);

    /**
     * Lists permission nodes for a group.
     *
     * @param groupCode the stable group code.
     * @return permission nodes.
     */
    List<PermissionNode> groupNodes(String groupCode);

    /**
     * Adds or updates one permission node on a group.
     *
     * @param groupCode the stable group code.
     * @param mutation the node mutation.
     * @return the persisted node.
     */
    PermissionNode upsertGroupNode(String groupCode, PermissionNodeMutation mutation);

    /**
     * Removes one permission node from a group.
     *
     * @param groupCode the stable group code.
     * @param node the permission node.
     * @param context the node context.
     */
    void removeGroupNode(String groupCode, String node, String context);

    /**
     * Lists groups assigned to a player.
     *
     * @param playerId the player id.
     * @param includeExpired whether expired memberships should be included.
     * @return player group memberships.
     */
    List<PlayerPermissionGroup> playerGroups(int playerId, boolean includeExpired);

    /**
     * Grants a group to a player.
     *
     * @param playerId the player id.
     * @param groupCode the stable group code.
     * @param request the grant request.
     */
    void grantGroupToPlayer(int playerId, String groupCode, PermissionGrantRequest request);

    /**
     * Revokes a group from a player.
     *
     * @param playerId the player id.
     * @param groupCode the stable group code.
     */
    void revokeGroupFromPlayer(int playerId, String groupCode);

    /**
     * Reloads cached permission data.
     */
    void reload();
}
