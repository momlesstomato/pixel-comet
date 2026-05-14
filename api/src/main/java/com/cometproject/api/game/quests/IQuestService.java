package com.cometproject.api.game.quests;

import java.util.Map;

/**
 * Defines the i quest service contract for the quest subsystem.
 */
public interface IQuestService {

    /**
     * Loads quests data for this quest contract.
     */
    void loadQuests();

    /**
     * Returns the by id associated with this quest contract.
     *
     * @param questId Quest id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IQuest getById(int questId);

    /**
     * Returns the amount of quests in category associated with this quest contract.
     *
     * @param category Category value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAmountOfQuestsInCategory(String category);

    /**
     * Returns the next quest in series associated with this quest contract.
     *
     * @param lastQuest Last quest value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IQuest getNextQuestInSeries(IQuest lastQuest);

    /**
     * Returns the quests associated with this quest contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<String, IQuest> getQuests();
}
