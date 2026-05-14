package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.game.quests.QuestType;

/**
 * Defines the player quests contract for the player subsystem.
 */
public interface PlayerQuests {

    /**
     * Loads quest progression data for this player contract.
     */
    void loadQuestProgression();

    /**
     * Indicates whether this player contract has started quest.
     *
     * @param questId Quest id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasStartedQuest(int questId);

    /**
     * Indicates whether this player contract has completed quest.
     *
     * @param questId Quest id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasCompletedQuest(int questId);

    /**
     * Executes the start quest operation for this player contract.
     *
     * @param quest Quest value supplied by the caller.
     */
    void startQuest(IQuest quest);

    /**
     * Indicates whether this player contract can cel quest.
     *
     * @param questId Quest id value supplied by the caller.
     */
    void cancelQuest(int questId);

    /**
     * Executes the progress quest operation for this player contract.
     *
     * @param type Type value supplied by the caller.
     */
    void progressQuest(QuestType type);

    /**
     * Executes the progress quest operation for this player contract.
     *
     * @param type Type value supplied by the caller.
     * @param data Data value supplied by the caller.
     */
    void progressQuest(QuestType type, int data);

    /**
     * Executes the progress quest by id operation for this player contract.
     *
     * @param id Id value supplied by the caller.
     * @param data Data value supplied by the caller.
     */
    void progressQuestById(int id, int data);

    /**
     * Returns the progress associated with this player contract.
     *
     * @param quest Quest value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getProgress(int quest);
}
