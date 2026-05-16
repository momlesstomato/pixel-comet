package com.cometproject.storage.api.data.permissions;

/**
 * Immutable permission group loaded from storage.
 *
 * @param id the internal storage id.
 * @param code the stable group code.
 * @param displayName the human-readable group name.
 * @param priority the hierarchy priority.
 * @param legacyRankId the rank id exposed to legacy packets and checks.
 * @param staff whether this group represents staff membership.
 * @param defaultGroup whether new players should receive this group.
 * @param enabled whether the group participates in permission resolution.
 */
public record PermissionGroup(
        long id,
        String code,
        String displayName,
        int priority,
        int legacyRankId,
        boolean staff,
        boolean defaultGroup,
        boolean enabled) {
}
