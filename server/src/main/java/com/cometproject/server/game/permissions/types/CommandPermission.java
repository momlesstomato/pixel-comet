package com.cometproject.server.game.permissions.types;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes command permission behavior for the permission subsystem.
 */
public class CommandPermission {
    private String commandId;
    private int minimumRank;
    private boolean vipOnly;

    /**
     * Creates a command permission instance for the permission subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public CommandPermission(ResultSet data) throws SQLException {
        this.commandId = data.getString("command_id");
        this.minimumRank = data.getInt("minimum_rank");
        this.vipOnly = data.getString("vip_only").equals("1");
    }

    /**
     * Returns the command id for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCommandId() {
        return commandId;
    }

    /**
     * Returns the minimum rank for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMinimumRank() {
        return minimumRank;
    }

    /**
     * Indicates whether VIP only applies to this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isVipOnly() {
        return vipOnly;
    }
}
