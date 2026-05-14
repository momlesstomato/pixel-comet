package com.cometproject.server.game.landing.calendar;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Describes calendar day behavior for the Comet subsystem.
 */
public class CalendarDay {
    private int day;
    private String gift;
    private String product;
    private String image;
    private String item;

    /**
     * Creates a calendar day instance for the Comet subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public CalendarDay(ResultSet data) throws SQLException {
        this.day = data.getInt("day");
        this.gift = data.getString("gift");
        this.product = data.getString("product");
        this.image = data.getString("image");
        this.item = data.getString("item");
    }

    /**
     * Returns the day for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the gift for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGift() { return gift; }

    /**
     * Returns the product for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getProduct() {
        return product;
    }

    /**
     * Returns the image for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getImage() {
        return image;
    }

    /**
     * Returns the item for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getItem() {
        return item;
    }
}
