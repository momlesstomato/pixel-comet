package com.cometproject.storage.api.data.permissions;

import java.time.Instant;

/**
 * Immutable membership assigning a permission group to a player.
 *
 * @param id the internal storage id.
 * @param playerId the assigned player id.
 * @param group the assigned group.
 * @param grantedBy the actor that created the membership.
 * @param reason the audit reason.
 * @param expiresAt the optional membership expiration timestamp.
 */
public record PlayerPermissionGroup(
        long id,
        int playerId,
        PermissionGroup group,
        String grantedBy,
        String reason,
        Instant expiresAt) {
}
