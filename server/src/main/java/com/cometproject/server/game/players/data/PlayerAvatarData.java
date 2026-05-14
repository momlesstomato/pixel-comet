package com.cometproject.server.game.players.data;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.server.game.utilities.validator.PlayerFigureValidator;

/**
 * Carries player avatar data data for the player subsystem.
 */
public class PlayerAvatarData implements PlayerAvatar {

    private int id;
    private String username;
    private String figure;
    private String gender;
    private String motto;

    private Object tempData = null;

    /**
     * Creates a player avatar data instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param motto Motto supplied by the caller.
     */
    public PlayerAvatarData(int id, String username, String figure, String gender, String motto) {
        this.id = id;
        this.username = username;
        this.figure = figure;
        this.gender = gender;
        this.motto = motto;

        if (figure == null) {
            return;
        }

        if (!PlayerFigureValidator.isValidFigureCode(this.figure, gender)) {
            this.figure = PlayerData.DEFAULT_FIGURE;
        }
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the id for this player contract.
     *
     * @param id Id supplied by the caller.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the username for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username for this player contract.
     *
     * @param username Username supplied by the caller.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the figure for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getFigure() {
        return figure;
    }

    /**
     * Updates the figure for this player contract.
     *
     * @param figure Figure supplied by the caller.
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Returns the motto for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMotto() {
        return motto;
    }

    /**
     * Updates the motto for this player contract.
     *
     * @param motto Motto supplied by the caller.
     */
    public void setMotto(String motto) {
        this.motto = motto;
    }

    /**
     * Returns the gender for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getGender() {
        return this.gender;
    }

    /**
     * Updates the gender for this player contract.
     *
     * @param gender Gender supplied by the caller.
     */
    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Executes temp data for this player contract.
     *
     * @param data Data supplied by the caller.
     */
    public void tempData(final Object data) {
        this.tempData = data;
    }

    /**
     * Executes temp data for this player contract.
     *
     * @return Result produced by the operation.
     */
    public Object tempData() {
        return this.tempData;
    }

}
