package com.cometproject.api.game.players.data;

/**
 * Defines the player avatar contract for the player subsystem.
 */
public interface PlayerAvatar {
    byte USERNAME_FIGURE = 0;
    byte USERNAME_FIGURE_MOTTO = 1;

    /**
     * Returns the id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the username associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getUsername();

    /**
     * Updates the username value for this player contract.
     *
     * @param username Username value supplied by the caller.
     */
    void setUsername(String username);

    /**
     * Returns the figure associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getFigure();

    /**
     * Updates the figure value for this player contract.
     *
     * @param figure Figure value supplied by the caller.
     */
    void setFigure(String figure);

    /**
     * Returns the motto associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMotto();

    /**
     * Updates the motto value for this player contract.
     *
     * @param motto Motto value supplied by the caller.
     */
    void setMotto(String motto);

    /**
     * Returns the gender associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getGender();

    /**
     * Updates the gender value for this player contract.
     *
     * @param gender Gender value supplied by the caller.
     */
    void setGender(String gender);

    /**
     * Executes the temp data operation for this player contract.
     *
     * @param tempData Temp data value supplied by the caller.
     */
    default void tempData(final Object tempData) {
    }

    /**
     * Executes the temp data operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    default Object tempData() {
        return null;
    }

}