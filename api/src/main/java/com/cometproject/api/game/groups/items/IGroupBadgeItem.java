package com.cometproject.api.game.groups.items;

/**
 * Defines the i group badge item contract for the group subsystem.
 */
public interface IGroupBadgeItem {
    /**
     * Returns the id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the first value associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getFirstValue();

    /**
     * Returns the second value associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getSecondValue();
}
