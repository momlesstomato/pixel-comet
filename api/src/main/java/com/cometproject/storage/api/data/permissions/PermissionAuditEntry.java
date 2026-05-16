package com.cometproject.storage.api.data.permissions;

import java.util.Map;

/**
 * Audit entry describing one permission mutation.
 *
 * @param action the action name.
 * @param actorType the actor type.
 * @param actorId the actor id.
 * @param targetType the target type.
 * @param targetId the target id.
 * @param metadata additional mutation metadata.
 */
public record PermissionAuditEntry(
        String action,
        String actorType,
        String actorId,
        String targetType,
        String targetId,
        Map<String, String> metadata) {
}
