package com.cometproject.server.game.groups.items.types;

import com.cometproject.api.game.groups.items.IGroupBadgeItem;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes group background colour behavior for the group subsystem.
 */
public class GroupBackgroundColour implements IGroupBadgeItem {
    private int id;
    private String colour;

    /**
     * Creates a group background colour instance for the group subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public GroupBackgroundColour(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.colour = data.getString("firstvalue");
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
        return colour;
    }

    /**
     * Returns the second value for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getSecondValue() {
        return null;
    }
}
