package com.cometproject.api.events.permissions;

import com.cometproject.api.events.Event;

/**
 * Event fired after permission caches are reloaded.
 */
public final class PermissionsReloadedEvent extends Event {
    private final int groupCount;
    private final int nodeCount;

    /**
     * Creates a permissions reloaded event.
     *
     * @param groupCount the loaded group count.
     * @param nodeCount the loaded node count.
     */
    public PermissionsReloadedEvent(final int groupCount, final int nodeCount) {
        this.groupCount = groupCount;
        this.nodeCount = nodeCount;
    }

    /**
     * Returns the loaded group count.
     *
     * @return the group count.
     */
    public int getGroupCount() {
        return this.groupCount;
    }

    /**
     * Returns the loaded node count.
     *
     * @return the node count.
     */
    public int getNodeCount() {
        return this.nodeCount;
    }
}
