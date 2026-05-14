package com.cometproject.api.game.players.data;

/**
 * Defines the i player statistics contract for the player subsystem.
 */
public interface IPlayerStatistics {
    /**
     * Executes the save operation for this player contract.
     */
    void save();
    /**
     * Persists fireworks data for this player contract.
     */
    void saveFireworks();
    /**
     * Executes the increment experience points operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void incrementExperiencePoints(int amount);
    /**
     * Executes the increment level operation for this player contract.
     */
    void incrementLevel();
    /**
     * Executes the increment cautions operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void incrementCautions(int amount);
    /**
     * Executes the increment fireworks operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void incrementFireworks(int amount);
    /**
     * Executes the decrement fireworks operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void decrementFireworks(int amount);
    /**
     * Returns the fireworks associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getFireworks();
    /**
     * Executes the increment respect points operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void incrementRespectPoints(int amount);
    /**
     * Executes the decrement daily respects operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void decrementDailyRespects(int amount);
    /**
     * Executes the increment bans operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void incrementBans(int amount);
    /**
     * Executes the increment abusive help tickets operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void incrementAbusiveHelpTickets(int amount);
    /**
     * Returns the player id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPlayerId();
    /**
     * Returns the daily respects associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDailyRespects();
    /**
     * Returns the respect points associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRespectPoints();
    /**
     * Returns the experience points associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getExperiencePoints();
    /**
     * Returns the level associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevel();
    /**
     * Executes the level pass operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean levelPass();
    /**
     * Returns the friend count associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getFriendCount();
    /**
     * Returns the help tickets associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHelpTickets();
    /**
     * Updates the help tickets value for this player contract.
     *
     * @param helpTickets Help tickets value supplied by the caller.
     */
    void setHelpTickets(int helpTickets);
    /**
     * Returns the abusive help tickets associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAbusiveHelpTickets();
    /**
     * Updates the abusive help tickets value for this player contract.
     *
     * @param abusiveHelpTickets Abusive help tickets value supplied by the caller.
     */
    void setAbusiveHelpTickets(int abusiveHelpTickets);
    /**
     * Returns the cautions associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCautions();
    /**
     * Updates the cautions value for this player contract.
     *
     * @param cautions Cautions value supplied by the caller.
     */
    void setCautions(int cautions);
    /**
     * Returns the bans associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBans();
    /**
     * Updates the bans value for this player contract.
     *
     * @param bans Bans value supplied by the caller.
     */
    void setBans(int bans);
    /**
     * Adds ban data to this player contract.
     */
    void addBan();
    /**
     * Updates the daily respects value for this player contract.
     *
     * @param points Points value supplied by the caller.
     */
    void setDailyRespects(int points);
    /**
     * Updates the scratches value for this player contract.
     *
     * @param scratches Scratches value supplied by the caller.
     */
    void setScratches(int scratches);
    /**
     * Returns the scratches associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getScratches();
    /**
     * Updates the daily rolls value for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void setDailyRolls(int amount);
    /**
     * Returns the daily rolls associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDailyRolls();
}
