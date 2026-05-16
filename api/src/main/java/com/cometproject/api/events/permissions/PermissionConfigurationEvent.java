package com.cometproject.api.events.permissions;

import com.cometproject.api.events.Event;

import java.util.Map;

/**
 * Base permission configuration event with action and metadata.
 */
public class PermissionConfigurationEvent extends Event {
    private final String action;
    private final String targetType;
    private final String targetId;
    private final Map<String, String> metadata;

    /**
     * Creates a permission configuration event.
     *
     * @param action the action name.
     * @param targetType the target resource type.
     * @param targetId the target resource id.
     * @param metadata metadata describing the change.
     */
    public PermissionConfigurationEvent(
            final String action,
            final String targetType,
            final String targetId,
            final Map<String, String> metadata) {
        this.action = action;
        this.targetType = targetType;
        this.targetId = targetId;
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    /**
     * Returns the action name.
     *
     * @return the action name.
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Returns the target resource type.
     *
     * @return the target resource type.
     */
    public String getTargetType() {
        return this.targetType;
    }

    /**
     * Returns the target resource id.
     *
     * @return the target resource id.
     */
    public String getTargetId() {
        return this.targetId;
    }

    /**
     * Returns metadata describing the change.
     *
     * @return immutable metadata.
     */
    public Map<String, String> getMetadata() {
        return this.metadata;
    }
}
