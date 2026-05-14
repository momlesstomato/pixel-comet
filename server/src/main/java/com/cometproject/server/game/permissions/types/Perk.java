package com.cometproject.server.game.permissions.types;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes perk behavior for the permission subsystem.
 */
public class Perk {
    private int id;
    private String title;
    private String data;
    private int rank;
    private boolean overrideRank;
    private boolean overrideDefault;

    /**
     * Creates a perk instance for the permission subsystem.
     *
     * @param result Result supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public Perk(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.title = result.getString("title");
        this.data = result.getString("data");
        this.rank = result.getInt("min_rank");
        this.overrideRank = result.getString("override_rank").equals("1");
        this.overrideDefault = result.getString("override_default").equals("1");
    }

    /**
     * Returns the id for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the title for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the data for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public String getData() {
        return this.data;
    }

    /**
     * Returns the rank for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Executes does override for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean doesOverride() {
        return this.overrideRank;
    }

    /**
     * Returns the default for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getDefault() {
        return this.overrideDefault;
    }
}

