package com.cometproject.server.game.permissions.types;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes effect permission behavior for the permission subsystem.
 */
public class EffectPermission {
    private int effectId;
    private int playerId;
    private boolean enabled;

    /**
     * Creates a effect permission instance for the permission subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public EffectPermission(ResultSet data) throws SQLException {
        this.effectId = data.getInt("effect_id");
        this.playerId = data.getInt("player_id");
        this.enabled = data.getString("enabled").equals("1");
    }

    /**
     * Returns the effect id for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEffectId() {
        return effectId;
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
