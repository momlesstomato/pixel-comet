package com.cometproject.server.game.groups.items.types;

import com.cometproject.api.game.groups.items.IGroupBadgeItem;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes group base behavior for the group subsystem.
 */
public class GroupBase implements IGroupBadgeItem {
    private int id;
    private String valueA;
    private String valueB;

    /**
     * Creates a group base instance for the group subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public GroupBase(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.valueA = data.getString("firstvalue");
        this.valueB = data.getString("secondvalue");
    }

    /**
     * Returns the id for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the first value for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getFirstValue() {
        return valueA;
    }

    /**
     * Returns the second value for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getSecondValue() {
        return valueB;
    }
}
