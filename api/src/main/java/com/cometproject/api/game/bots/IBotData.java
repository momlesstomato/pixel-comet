package com.cometproject.api.game.bots;

import com.google.gson.JsonObject;

/**
 * Defines the i bot data contract for the Comet subsystem.
 */
public interface IBotData {
    /**
     * Executes the to JSON object operation for this game domain contract.
     *
     * @return Result produced by the operation.
     */
    JsonObject toJsonObject();

    /**
     * Returns the random message associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getRandomMessage();

    /**
     * Executes the save operation for this game domain contract.
     */
    void save();

    /**
     * Returns the id associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the username associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getUsername();

    /**
     * Updates the username value for this game domain contract.
     *
     * @param username Username value supplied by the caller.
     */
    void setUsername(String username);

    /**
     * Returns the motto associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMotto();

    /**
     * Updates the motto value for this game domain contract.
     *
     * @param motto Motto value supplied by the caller.
     */
    void setMotto(String motto);

    /**
     * Returns the figure associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getFigure();

    /**
     * Updates the figure value for this game domain contract.
     *
     * @param figure Figure value supplied by the caller.
     */
    void setFigure(String figure);

    /**
     * Returns the gender associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getGender();

    /**
     * Updates the gender value for this game domain contract.
     *
     * @param gender Gender value supplied by the caller.
     */
    void setGender(String gender);

    /**
     * Returns the chat delay associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getChatDelay();

    /**
     * Updates the chat delay value for this game domain contract.
     *
     * @param delay Delay value supplied by the caller.
     */
    void setChatDelay(int delay);

    /**
     * Returns the messages associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String[] getMessages();

    /**
     * Updates the messages value for this game domain contract.
     *
     * @param messages Messages value supplied by the caller.
     */
    void setMessages(String[] messages);

    /**
     * Indicates whether automatic chat is enabled for this game domain contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isAutomaticChat();

    /**
     * Updates the automatic chat value for this game domain contract.
     *
     * @param isAutomaticChat Is automatic chat value supplied by the caller.
     */
    void setAutomaticChat(boolean isAutomaticChat);

    /**
     * Returns the owner name associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getOwnerName();

    /**
     * Returns the owner id associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOwnerId();

    /**
     * Executes the dispose operation for this game domain contract.
     */
    void dispose();

    /**
     * Returns the bot type associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    BotType getBotType();

    /**
     * Returns the mode associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    BotMode getMode();

    /**
     * Updates the mode value for this game domain contract.
     *
     * @param mode Mode value supplied by the caller.
     */
    void setMode(BotMode mode);

    /**
     * Returns the data associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getData();

    /**
     * Updates the data value for this game domain contract.
     *
     * @param data Data value supplied by the caller.
     */
    void setData(String data);
}
