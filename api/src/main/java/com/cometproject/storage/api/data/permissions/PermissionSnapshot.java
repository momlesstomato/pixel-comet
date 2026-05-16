package com.cometproject.storage.api.data.permissions;

import java.time.Instant;
import java.util.List;

/**
 * Cached effective permission state for one player.
 *
 * @param playerId the player id.
 * @param groups the active groups assigned to the player.
 * @param nodes the active nodes contributed by those groups.
 * @param highestPriority the highest priority among active groups.
 * @param legacyRankId the rank exposed to legacy code and packets.
 * @param legacyRankName the legacy rank display name.
 * @param builtAt the timestamp when the snapshot was created.
 */
public record PermissionSnapshot(
        int playerId,
        List<PermissionGroup> groups,
        List<PermissionNode> nodes,
        int highestPriority,
        int legacyRankId,
        String legacyRankName,
        Instant builtAt) {
}
