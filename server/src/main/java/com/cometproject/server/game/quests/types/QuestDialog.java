package com.cometproject.server.game.quests.types;

/**
 * Describes quest dialog behavior for the quest subsystem.
 */
public class QuestDialog {
    private final int id;
    private final int botId;
    private final int questId;
    private final int step;
    private final String keyword;
    private final String answer;

    /**
     * Creates a quest dialog instance for the quest subsystem.
     *
     * @param id Id supplied by the caller.
     * @param botId Bot id supplied by the caller.
     * @param questId Quest id supplied by the caller.
     * @param step Step supplied by the caller.
     * @param keyword Keyword supplied by the caller.
     * @param answer Answer supplied by the caller.
     */
    public QuestDialog(int id, int botId, int questId, int step, String keyword, String answer) {
        this.id = id;
        this.botId = botId;
        this.questId = questId;
        this.step = step;
        this.keyword = keyword;
        this.answer = answer;
    }

    /**
     * Returns the id for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the bot id for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBotId() {
        return botId;
    }

    /**
     * Returns the quest id for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public int getQuestId() {
        return questId;
    }

    /**
     * Returns the step for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public int getStep() {
        return step;
    }

    /**
     * Returns the keyword for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Returns the answer for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public String getAnswer() {
        return answer;
    }
}
