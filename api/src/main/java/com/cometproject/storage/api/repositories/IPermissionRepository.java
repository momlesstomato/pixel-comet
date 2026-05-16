package com.cometproject.storage.api.repositories;

import com.cometproject.storage.api.data.permissions.PermissionAuditEntry;
import com.cometproject.storage.api.data.permissions.PermissionGrantRequest;
import com.cometproject.storage.api.data.permissions.PermissionGroup;
import com.cometproject.storage.api.data.permissions.PermissionGroupMutation;
import com.cometproject.storage.api.data.permissions.PermissionNode;
import com.cometproject.storage.api.data.permissions.PermissionNodeMutation;
import com.cometproject.storage.api.data.permissions.PlayerPermissionGroup;

import java.util.List;
import java.util.function.Consumer;

/**
 * Provides persistence operations for permission groups, nodes, memberships, and audit records.
 */
public interface IPermissionRepository {
    /**
     * Loads all permission groups.
     *
     * @param includeDisabled whether disabled groups should be included.
     * @param consumer the callback receiving the groups.
     */
    void getGroups(boolean includeDisabled, Consumer<List<PermissionGroup>> consumer);

    /**
     * Loads one permission group.
     *
     * @param groupCode the stable group code.
     * @param consumer the callback receiving the group, or null when absent.
     */
    void getGroup(String groupCode, Consumer<PermissionGroup> consumer);

    /**
     * Creates or updates a permission group.
     *
     * @param mutation the requested group mutation.
     * @param consumer the callback receiving the persisted group.
     */
    void upsertGroup(PermissionGroupMutation mutation, Consumer<PermissionGroup> consumer);

    /**
     * Soft-disables a permission group.
     *
     * @param groupCode the stable group code.
     */
    void disableGroup(String groupCode);

    /**
     * Loads nodes for one group.
     *
     * @param groupCode the stable group code.
     * @param consumer the callback receiving the group nodes.
     */
    void getGroupNodes(String groupCode, Consumer<List<PermissionNode>> consumer);

    /**
     * Loads all active permission nodes.
     *
     * @param consumer the callback receiving active group nodes.
     */
    void getAllNodes(Consumer<List<PermissionNode>> consumer);

    /**
     * Creates or updates one group permission node.
     *
     * @param groupCode the stable group code.
     * @param mutation the requested node mutation.
     * @param consumer the callback receiving the persisted node.
     */
    void upsertGroupNode(String groupCode, PermissionNodeMutation mutation, Consumer<PermissionNode> consumer);

    /**
     * Deletes one group permission node.
     *
     * @param groupCode the stable group code.
     * @param node the permission node.
     * @param context the node context.
     */
    void deleteGroupNode(String groupCode, String node, String context);

    /**
     * Loads active groups assigned to a player.
     *
     * @param playerId the player id.
     * @param includeExpired whether expired memberships should be included.
     * @param consumer the callback receiving player memberships.
     */
    void getPlayerGroups(int playerId, boolean includeExpired, Consumer<List<PlayerPermissionGroup>> consumer);

    /**
     * Grants one group to a player.
     *
     * @param playerId the player id.
     * @param groupCode the stable group code.
     * @param request the grant request.
     */
    void grantGroup(int playerId, String groupCode, PermissionGrantRequest request);

    /**
     * Revokes one group from a player.
     *
     * @param playerId the player id.
     * @param groupCode the stable group code.
     */
    void revokeGroup(int playerId, String groupCode);

    /**
     * Loads player ids assigned to a group.
     *
     * @param groupCode the stable group code.
     * @param consumer the callback receiving player ids.
     */
    void getPlayersByGroup(String groupCode, Consumer<List<Integer>> consumer);

    /**
     * Writes an audit entry.
     *
     * @param entry the entry to persist.
     */
    void writeAudit(PermissionAuditEntry entry);
}
