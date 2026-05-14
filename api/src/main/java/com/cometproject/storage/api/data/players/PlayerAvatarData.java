package com.cometproject.storage.api.data.players;

import com.cometproject.api.game.players.data.PlayerAvatar;

/**
 * Describes player avatar data behavior for the storage subsystem.
 */
public class PlayerAvatarData implements PlayerAvatar {
    private final int id;
    private String username;
    private String figure;
    private String motto;
    private String gender;

    /**
     * Creates a player avatar data instance for the player subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param username Username value supplied by the caller.
     * @param figure Figure value supplied by the caller.
     * @param motto Motto value supplied by the caller.
     * @param gender Gender value supplied by the caller.
     */
    public PlayerAvatarData(int id, String username, String figure, String motto, String gender) {
        this.id = id;
        this.username = username;
        this.figure = figure;
        this.motto = motto;
        this.gender = gender;
    }

    /**
     * Returns the id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns the username for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username for this storage contract.
     *
     * @param username Username supplied by the caller.
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the figure for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getFigure() {
        return figure;
    }

    /**
     * Updates the figure for this storage contract.
     *
     * @param figure Figure supplied by the caller.
     */
    @Override
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Returns the motto for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getMotto() {
        return motto;
    }

    /**
     * Updates the motto for this storage contract.
     *
     * @param motto Motto supplied by the caller.
     */
    @Override
    public void setMotto(String motto) {
        this.motto = motto;
    }

    /**
     * Returns the gender for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getGender() {
        return gender;
    }

    /**
     * Updates the gender for this storage contract.
     *
     * @param gender Gender supplied by the caller.
     */
    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }
}
