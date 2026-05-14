package com.cometproject.server.game.permissions.types;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes override command permission behavior for the permission subsystem.
 */
public class OverrideCommandPermission {
    private String commandId;
    private int playerId;
    private boolean enabled;

    /**
     * Creates a override command permission instance for the permission subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public OverrideCommandPermission(ResultSet data) throws SQLException {
        this.commandId = data.getString("command_id");
        this.playerId = data.getInt("player_id");
        this.enabled = data.getString("enabled").equals("1");
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
     * Returns the player id for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Indicates whether enabled applies to this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isEnabled() {
        return enabled;
    }
}
