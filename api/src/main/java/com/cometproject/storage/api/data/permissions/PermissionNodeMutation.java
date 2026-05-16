package com.cometproject.storage.api.data.permissions;

import java.time.Instant;

/**
 * Request object for creating or updating a permission node.
 *
 * @param node the normalized permission node.
 * @param effect whether the node allows or denies access.
 * @param context the context scope for the node.
 * @param expiresAt the optional expiration timestamp.
 */
public record PermissionNodeMutation(
        String node,
        PermissionEffect effect,
        String context,
        Instant expiresAt) {
}
