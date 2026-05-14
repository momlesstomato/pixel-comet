package com.cometproject.server.game.navigator.types;

import com.cometproject.api.game.rooms.RoomCategory;
import com.cometproject.server.game.navigator.types.categories.NavigatorCategoryType;
import com.cometproject.server.game.navigator.types.categories.NavigatorSearchAllowance;
import com.cometproject.server.game.navigator.types.categories.NavigatorViewMode;

/**
 * Describes category behavior for the navigator subsystem.
 */
public class Category implements RoomCategory {
    private final int id;
    private final String category;
    private final String categoryId;
    private final String publicName;
    private final boolean canDoActions;
    private final int colour;
    private final int requiredRank;
    private final NavigatorViewMode viewMode;
    private final NavigatorCategoryType categoryType;
    private final NavigatorSearchAllowance searchAllowance;
    private final int orderId;
    private final boolean visible;
    private final int roomCount;
    private final int roomCountExpanded;

    /**
     * Creates a category instance for the navigator subsystem.
     *
     * @param id Id supplied by the caller.
     * @param category Category supplied by the caller.
     * @param categoryId Category id supplied by the caller.
     * @param publicName Public name supplied by the caller.
     * @param canDoActions Can do actions supplied by the caller.
     * @param colour Colour supplied by the caller.
     * @param requiredRank Required rank supplied by the caller.
     * @param viewMode View mode supplied by the caller.
     * @param categoryType Category type supplied by the caller.
     * @param searchAllowance Search allowance supplied by the caller.
     * @param orderId Order id supplied by the caller.
     * @param visible Visible supplied by the caller.
     * @param roomCount Room count supplied by the caller.
     * @param roomCountExpanded Room count expanded supplied by the caller.
     */
    public Category(int id, String category, String categoryId, String publicName, boolean canDoActions, int colour, int requiredRank, NavigatorViewMode viewMode, String categoryType, String searchAllowance, int orderId, boolean visible, int roomCount, int roomCountExpanded) {
        this.id = id;
        this.category = category;
        this.categoryId = categoryId;
        this.publicName = publicName;
        this.canDoActions = canDoActions;
        this.colour = colour;
        this.requiredRank = requiredRank;
        this.viewMode = viewMode;
        this.categoryType = NavigatorCategoryType.valueOf(categoryType.toUpperCase());
        this.searchAllowance = NavigatorSearchAllowance.valueOf(searchAllowance.toUpperCase());
        this.orderId = orderId;
        this.visible = visible;
        this.roomCount = roomCount;
        this.roomCountExpanded = roomCountExpanded;
    }

    /**
     * Returns the id for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the category for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the category id for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Returns the public name for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPublicName() {
        return publicName;
    }

    /**
     * Indicates whether this navigator contract can do actions.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canDoActions() {
        return canDoActions;
    }

    /**
     * Returns the colour for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getColour() {
        return colour;
    }

    /**
     * Returns the required rank for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRequiredRank() {
        return requiredRank;
    }

    /**
     * Returns the view mode for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public NavigatorViewMode getViewMode() {
        return viewMode;
    }

    /**
     * Returns the category type for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public NavigatorCategoryType getCategoryType() {
        return categoryType;
    }

    /**
     * Returns the search allowance for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public NavigatorSearchAllowance getSearchAllowance() {
        return searchAllowance;
    }

    /**
     * Returns the order id for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Indicates whether visible applies to this navigator contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Returns the room count for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomCount() {
        return roomCount;
    }

    /**
     * Returns the room count expanded for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomCountExpanded() {
        return roomCountExpanded;
    }
}
