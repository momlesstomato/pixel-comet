package com.cometproject.api.game.quests;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.networking.messages.IComposer;

/**
 * Defines the i quest contract for the quest subsystem.
 */
public interface IQuest {

    /**
     * Executes the compose operation for this quest contract.
     *
     * @param player Player value supplied by the caller.
     * @param msg Msg value supplied by the caller.
     */
    void compose(IPlayer player, IComposer msg);

    /**
     * Returns the type associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    QuestType getType();

    /**
     * Returns the id associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the name associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getName();

    /**
     * Returns the category associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getCategory();

    /**
     * Returns the series number associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSeriesNumber();

    /**
     * Returns the goal type associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getGoalType();

    /**
     * Returns the goal data associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getGoalData();

    /**
     * Returns the reward associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getReward();

    /**
     * Returns the timestamp associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getTimestamp();

    /**
     * Returns the reward type associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    QuestReward getRewardType();

    /**
     * Returns the data bit associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDataBit();

    /**
     * Returns the badge id associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getBadgeId();
}
