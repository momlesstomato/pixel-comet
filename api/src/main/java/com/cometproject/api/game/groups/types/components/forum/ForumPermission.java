package com.cometproject.api.game.groups.types.components.forum;

/**
 * Enumerates forum permission values used by the group subsystem.
 */
public enum ForumPermission {
    EVERYBODY(0),
    MEMBERS(1),
    ADMINISTRATORS(2),
    OWNER(3);

    private int permissionId;

    ForumPermission(int permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * Returns the permission id for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPermissionId() {
        return permissionId;
    }

    /**
     * Returns the by id associated with this group contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public static ForumPermission getById(int id) {
        switch(id) {
            case 0: return EVERYBODY;
            case 1: return MEMBERS;
            case 2: return ADMINISTRATORS;
            case 3: return OWNER;
        }

        return MEMBERS;
    }
}

