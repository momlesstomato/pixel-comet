package com.cometproject.storage.api.data.permissions;

import java.time.Instant;

/**
 * Request object for granting a group to a player.
 *
 * @param grantedBy the actor creating the membership.
 * @param reason the audit reason.
 * @param expiresAt the optional membership expiration timestamp.
 */
public record PermissionGrantRequest(String grantedBy, String reason, Instant expiresAt) {
}
