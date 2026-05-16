package com.cometproject.storage.api.data.permissions;

import java.time.Instant;

/**
 * Immutable permission node assigned to a permission group.
 *
 * @param id the internal storage id.
 * @param groupId the owning group id.
 * @param groupCode the owning group code.
 * @param node the normalized permission node.
 * @param effect whether the node allows or denies access.
 * @param context the context scope for the node.
 * @param expiresAt the optional expiration timestamp.
 */
public record PermissionNode(
        long id,
        long groupId,
        String groupCode,
        String node,
        PermissionEffect effect,
        String context,
        Instant expiresAt) {
}
