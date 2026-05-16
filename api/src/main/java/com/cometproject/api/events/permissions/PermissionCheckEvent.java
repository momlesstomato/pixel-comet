package com.cometproject.api.events.permissions;

import com.cometproject.api.events.Event;
import com.cometproject.storage.api.data.permissions.PermissionCheckResult;
import com.cometproject.storage.api.data.permissions.PermissionContext;

/**
 * Event fired when a permission node is checked.
 */
public final class PermissionCheckEvent extends Event {
    private final int playerId;
    private final String permissionNode;
    private final PermissionContext context;
    private final boolean mutable;
    private PermissionCheckResult result;

    /**
     * Creates a permission check event.
     *
     * @param playerId the player id.
     * @param permissionNode the requested permission node.
     * @param context the runtime permission context.
     * @param result the resolved check result.
     * @param mutable whether listeners may replace the result.
     */
    public PermissionCheckEvent(
            final int playerId,
            final String permissionNode,
            final PermissionContext context,
            final PermissionCheckResult result,
            final boolean mutable) {
        this.playerId = playerId;
        this.permissionNode = permissionNode;
        this.context = context;
        this.result = result;
        this.mutable = mutable;
    }

    /**
     * Returns the player id.
     *
     * @return the player id.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Returns the requested permission node.
     *
     * @return the permission node.
     */
    public String getPermissionNode() {
        return this.permissionNode;
    }

    /**
     * Returns the runtime permission context.
     *
     * @return the permission context.
     */
    public PermissionContext getContext() {
        return this.context;
    }

    /**
     * Returns the resolved check result.
     *
     * @return the check result.
     */
    public PermissionCheckResult getResult() {
        return this.result;
    }

    /**
     * Replaces the check result when the event is mutable.
     *
     * @param result the replacement result.
     */
    public void setResult(final PermissionCheckResult result) {
        if (this.mutable) {
            this.result = result;
        }
    }

    /**
     * Returns whether listeners may replace the result.
     *
     * @return true when mutable.
     */
    public boolean isMutable() {
        return this.mutable;
    }
}
