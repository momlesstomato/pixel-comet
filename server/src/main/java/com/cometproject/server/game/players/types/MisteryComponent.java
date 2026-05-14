package com.cometproject.server.game.players.types;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Owns mistery behavior inside the player subsystem.
 */
public class MisteryComponent {
    int playerId;
    String mistery_key;
    String mistery_box;


    /**
     * Creates a mistery component instance for the player subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public MisteryComponent(ResultSet data) throws SQLException {
        this.playerId = data.getInt("player_id");
        this.mistery_key = data.getString("mistery_key");
        this.mistery_box = data.getString("mistery_box");
    }

    /**
     * Creates a mistery component instance for the player subsystem.
     *
     * @param playerId Player identifier used by the operation.
     */
    public MisteryComponent(int playerId){
        this.playerId = playerId;
        this.mistery_key = "";
        this.mistery_box = "";
    }

    /**
     * Returns the mistery box for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMisteryBox() { return mistery_box; }
    /**
     * Returns the mistery key for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMisteryKey() { return mistery_key; }

    /**
     * Updates the mistery box for this player contract.
     *
     * @param box Box supplied by the caller.
     */
    public void setMisteryBox(String box) { this.mistery_box = box; }
    /**
     * Updates the mistery key for this player contract.
     *
     * @param key Key supplied by the caller.
     */
    public void setMisteryKey(String key) { this.mistery_key = key; }
}
