package com.cometproject.api.events.permissions;

import com.cometproject.api.events.Cancellable;

import java.util.Map;

/**
 * Cancellable permission configuration event fired before persistence.
 */
public class CancellablePermissionConfigurationEvent extends PermissionConfigurationEvent implements Cancellable {
    private boolean cancelled;
    private String cancellationCode = "permission_configuration_cancelled";
    private String cancellationMessage = "Permission configuration change was cancelled.";

    /**
     * Creates a cancellable permission configuration event.
     *
     * @param action the action name.
     * @param targetType the target resource type.
     * @param targetId the target resource id.
     * @param metadata metadata describing the change.
     */
    public CancellablePermissionConfigurationEvent(
            final String action,
            final String targetType,
            final String targetId,
            final Map<String, String> metadata) {
        super(action, targetType, targetId, metadata);
    }

    /**
     * Cancels the event with a public code and message.
     *
     * @param code the machine-readable cancellation code.
     * @param message the public cancellation message.
     */
    public void cancel(final String code, final String message) {
        this.cancellationCode = code;
        this.cancellationMessage = message;
        this.cancelled = true;
    }

    /**
     * Returns the cancellation code.
     *
     * @return the cancellation code.
     */
    public String getCancellationCode() {
        return this.cancellationCode;
    }

    /**
     * Returns the cancellation message.
     *
     * @return the cancellation message.
     */
    public String getCancellationMessage() {
        return this.cancellationMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
