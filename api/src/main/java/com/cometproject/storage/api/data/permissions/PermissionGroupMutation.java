package com.cometproject.storage.api.data.permissions;

/**
 * Request object for creating or updating a permission group.
 *
 * @param code the stable group code.
 * @param displayName the human-readable group name.
 * @param priority the hierarchy priority.
 * @param legacyRankId the rank id exposed to legacy packets and checks.
 * @param staff whether this group represents staff membership.
 * @param defaultGroup whether new players should receive this group.
 * @param enabled whether the group participates in permission resolution.
 */
public record PermissionGroupMutation(
        String code,
        String displayName,
        int priority,
        int legacyRankId,
        boolean staff,
        boolean defaultGroup,
        boolean enabled) {
}
