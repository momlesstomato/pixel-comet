package com.cometproject.api.game.players.data;

public interface IPlayerData extends PlayerAvatar {

    void save();

    void decreaseCredits(int amount);

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

    int getId();

    int getRank();

    String getUsername();

    void setUsername(String username);

    int getAchievementPoints();

    void increaseAchievementPoints(int amount);

    String getMotto();

    void setMotto(String motto);

    String getFigure();

    String getGender();

    int getCredits();

    void setCredits(int credits);

    int getLastVisit();

    String getRegDate();

    boolean isVip();

    void setVip(boolean vip);

    void setLastVisit(long time);

    void setFigure(String figure);

    void setGender(String gender);

    int getRegTimestamp();

    void setRegTimestamp(int regTimestamp);

    String getEmail();

    void setEmail(String email);

    int getFavouriteGroup();

    void setFavouriteGroup(int favouriteGroup);

    String getIpAddress();

    void setIpAddress(String ipAddress);

    void setRank(int rank);

    void setJob(String job);

    String getJob();

    String getTemporaryFigure();

    void setTemporaryFigure(String temporaryFigure);

    int getQuestId();

    void setQuestId(int questId);

    String getNameColour();

    void setNameColour(String nameColour);
    void setTag(String tag);

    /**
     * Returns the profile tag shown alongside the player account.
     *
     * @return the persisted profile tag.
     */
    String getTag();
}
