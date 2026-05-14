package com.cometproject.api.game.groups.types;

/**
 * Enumerates group type values used by the group subsystem.
 */
public enum GroupType {
    REGULAR(0), EXCLUSIVE(1), PRIVATE(2);

    private int typeId;

    GroupType(int type) {
        this.typeId = type;
    }

    /**
     * Returns the type id for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTypeId() {
        return this.typeId;
    }

    /**
     * Executes the value of operation for this group contract.
     *
     * @param typeId Type id value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static GroupType valueOf(int typeId) {
        if (typeId == 0)
            return REGULAR;
        else if (typeId == 1)
            return EXCLUSIVE;
        else
            return PRIVATE;
    }
}
