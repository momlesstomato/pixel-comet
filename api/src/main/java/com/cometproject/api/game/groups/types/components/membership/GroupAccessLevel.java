package com.cometproject.api.game.groups.types.components.membership;

/**
 * Enumerates group access level values used by the group subsystem.
 */
public enum GroupAccessLevel {
    MEMBER, ADMIN, OWNER;

    /**
     * Indicates whether admin applies to this group contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isAdmin() {
        return this.equals(ADMIN) || this.equals(OWNER);
    }
}
