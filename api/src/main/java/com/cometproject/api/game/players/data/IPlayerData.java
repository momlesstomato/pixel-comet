package com.cometproject.api.game.players.data;

/**
 * Defines the i player data contract for the player subsystem.
 */
public interface IPlayerData extends PlayerAvatar {

    /**
     * Executes the save operation for this player contract.
     */
    void save();

    /**
     * Executes the decrease credits operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void decreaseCredits(int amount);

    /**
     * Executes the increase credits operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseCredits(int amount);

    /**
     * Adds a configured inventory currency.
     *
     * @param currencyCode the currency code or alias.
     * @param amount       the positive amount.
     */
    void increaseCurrency(String currencyCode, int amount);

    /**
     * Removes a configured inventory currency.
     *
     * @param currencyCode the currency code or alias.
     * @param amount       the positive amount.
     */
    void decreaseCurrency(String currencyCode, int amount);

    /**
     * Returns a configured inventory balance snapshot.
     *
     * @param currencyCode the currency code or alias.
     * @return the balance, or zero when absent.
     */
    int getCurrencyBalance(String currencyCode);

    /**
     * Applies a inventory balance snapshot without recording a new movement.
     *
     * @param currencyCode the currency code or alias.
     * @param balance      the exact balance.
     */
    void setCurrencyBalance(String currencyCode, int balance);

    /**
     * Returns the id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the rank associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRank();

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
     * Returns the achievement points associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAchievementPoints();

    /**
     * Executes the increase achievement points operation for this player contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseAchievementPoints(int amount);

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
     * Returns the figure associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getFigure();

    /**
     * Returns the gender associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getGender();

    /**
     * Returns the credits associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCredits();

    /**
     * Updates the credits value for this player contract.
     *
     * @param credits Credits value supplied by the caller.
     */
    void setCredits(int credits);

    /**
     * Returns the last visit associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLastVisit();

    /**
     * Returns the reg date associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getRegDate();

    /**
     * Indicates whether VIP is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isVip();

    /**
     * Updates the VIP value for this player contract.
     *
     * @param vip Vip value supplied by the caller.
     */
    void setVip(boolean vip);

    /**
     * Updates the last visit value for this player contract.
     *
     * @param time Time value supplied by the caller.
     */
    void setLastVisit(long time);

    /**
     * Updates the figure value for this player contract.
     *
     * @param figure Figure value supplied by the caller.
     */
    void setFigure(String figure);

    /**
     * Updates the gender value for this player contract.
     *
     * @param gender Gender value supplied by the caller.
     */
    void setGender(String gender);

    /**
     * Returns the reg timestamp associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRegTimestamp();

    /**
     * Updates the reg timestamp value for this player contract.
     *
     * @param regTimestamp Reg timestamp value supplied by the caller.
     */
    void setRegTimestamp(int regTimestamp);

    /**
     * Returns the email associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getEmail();

    /**
     * Updates the email value for this player contract.
     *
     * @param email Email value supplied by the caller.
     */
    void setEmail(String email);

    /**
     * Returns the favourite group associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getFavouriteGroup();

    /**
     * Updates the favourite group value for this player contract.
     *
     * @param favouriteGroup Favourite group value supplied by the caller.
     */
    void setFavouriteGroup(int favouriteGroup);

    /**
     * Returns the IP address associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getIpAddress();

    /**
     * Updates the IP address value for this player contract.
     *
     * @param ipAddress Ip address value supplied by the caller.
     */
    void setIpAddress(String ipAddress);

    /**
     * Updates the rank value for this player contract.
     *
     * @param rank Rank value supplied by the caller.
     */
    void setRank(int rank);

    /**
     * Updates the job value for this player contract.
     *
     * @param job Job value supplied by the caller.
     */
    void setJob(String job);

    /**
     * Returns the job associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getJob();

    /**
     * Returns the temporary figure associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getTemporaryFigure();

    /**
     * Updates the temporary figure value for this player contract.
     *
     * @param temporaryFigure Temporary figure value supplied by the caller.
     */
    void setTemporaryFigure(String temporaryFigure);

    /**
     * Returns the quest id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getQuestId();

    /**
     * Updates the quest id value for this player contract.
     *
     * @param questId Quest id value supplied by the caller.
     */
    void setQuestId(int questId);

    /**
     * Returns the name colour associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getNameColour();

    /**
     * Updates the name colour value for this player contract.
     *
     * @param nameColour Name colour value supplied by the caller.
     */
    void setNameColour(String nameColour);
    /**
     * Updates the tag value for this player contract.
     *
     * @param tag Tag value supplied by the caller.
     */
    void setTag(String tag);

    /**
     * Returns the profile tag shown alongside the player account.
     *
     * @return the persisted profile tag.
     */
    String getTag();
}
