package com.cometproject.api.game.catalog.types;

import com.cometproject.api.networking.messages.IComposer;

import java.util.List;

/**
 * Defines the i catalog item contract for the catalog subsystem.
 */
public interface ICatalogItem {
    /**
     * Executes the compose operation for this catalog contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void compose(IComposer msg);

    /**
     * Executes the compose club presents operation for this catalog contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void composeClubPresents(IComposer msg);

    /**
     * Executes the serialize availability operation for this catalog contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void serializeAvailability(IComposer msg);

    /**
     * Returns the id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the item id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getItemId();

    /**
     * Returns the items associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<ICatalogBundledItem> getItems();

    /**
     * Returns the display name associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDisplayName();

    /**
     * Returns the cost credits associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostCredits();

    /**
     * Returns the cost activity points associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostActivityPoints();

    /**
     * Returns the cost diamonds associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostDiamonds();

    /**
     * Returns the cost seasonal associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostSeasonal();

    /**
     * Returns the cost tokens associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostTokens();

    /**
     * Returns the amount associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAmount();

    /**
     * Indicates whether VIP is enabled for this catalog contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isVip();

    /**
     * Returns the limited total associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLimitedTotal();

    /**
     * Returns the limited sells associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLimitedSells();

    /**
     * Executes the allow offer operation for this catalog contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean allowOffer();

    /**
     * Executes the increase limited sells operation for this catalog contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseLimitedSells(int amount);

    /**
     * Indicates whether this catalog contract has badge.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasBadge();

    /**
     * Indicates whether badge only is enabled for this catalog contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isBadgeOnly();

    /**
     * Returns the badge id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getBadgeId();

    /**
     * Returns the preset data associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getPresetData();

    /**
     * Returns the page id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPageId();
}
