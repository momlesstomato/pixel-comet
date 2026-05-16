package com.cometproject.storage.api.data.permissions;

/**
 * Result of resolving a permission node for a player or group.
 *
 * @param allowed whether access is granted.
 * @param permissionNode the requested permission node.
 * @param matchedNode the node that decided the result.
 * @param effect the matched effect.
 * @param groupCode the group that provided the match.
 * @param priority the matched group priority.
 * @param reason the machine-readable resolution reason.
 */
public record PermissionCheckResult(
        boolean allowed,
        String permissionNode,
        String matchedNode,
        PermissionEffect effect,
        String groupCode,
        int priority,
        String reason) {
    /**
     * Creates a denied result for an unresolved permission node.
     *
     * @param permissionNode the requested permission node.
     * @return a denied check result.
     */
    public static PermissionCheckResult denied(final String permissionNode) {
        return new PermissionCheckResult(
                false,
                permissionNode,
                "",
                PermissionEffect.DENY,
                "",
                0,
                "no_matching_permission");
    }
}
