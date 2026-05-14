package com.cometproject.api.game.groups;

import com.cometproject.api.game.groups.items.IGroupBadgeItem;

import java.util.List;
import java.util.Map;

/**
 * Defines the i group item service contract for the group subsystem.
 */
public interface IGroupItemService {

    /**
     * Returns the bases associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IGroupBadgeItem> getBases();

    /**
     * Returns the symbols associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IGroupBadgeItem> getSymbols();

    /**
     * Returns the base colours associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IGroupBadgeItem> getBaseColours();

    /**
     * Returns the symbol colours associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IGroupBadgeItem> getSymbolColours();

    /**
     * Returns the background colours associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IGroupBadgeItem> getBackgroundColours();

    /**
     * Executes the load operation for this group contract.
     */
    void load();
}
