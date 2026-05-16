package com.cometproject.storage.api.data.permissions;

import java.util.Map;

/**
 * Runtime context for permission checks that depend on room or actor state.
 *
 * @param roomId the optional room id.
 * @param roomOwner whether the actor owns the current room.
 * @param roomRights whether the actor has rights in the current room.
 * @param source the subsystem requesting the check.
 * @param metadata additional contextual metadata.
 */
public record PermissionContext(
        Integer roomId,
        boolean roomOwner,
        boolean roomRights,
        String source,
        Map<String, String> metadata) {
    /**
     * Returns an empty global permission context.
     *
     * @return a context without room-specific state.
     */
    public static PermissionContext global() {
        return new PermissionContext(null, false, false, "global", Map.of());
    }
}
