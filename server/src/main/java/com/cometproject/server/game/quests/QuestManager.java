package com.cometproject.server.game.quests;

import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.game.quests.IQuestService;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.storage.queries.quests.QuestsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Manages quest runtime state for the quest subsystem.
 */
public class QuestManager implements Startable, IQuestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestManager.class.getName());

    private Map<String, IQuest> quests;

    /**
     * Creates a quest manager instance for the quest subsystem.
     */
    public QuestManager() {

    }

    /**
     * Returns the instance for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    public static QuestManager getInstance() {
        return CometBootstrap.resolve(QuestManager.class);
    }

    /**
     * Starts this quest component.
     */
    @Override
    public void start() {
        this.loadQuests();
    }

    /**
     * Loads quests for this quest contract.
     */
    @Override
    public void loadQuests() {
        if (this.quests != null) {
            this.quests.clear();
        }

        this.quests = QuestsDao.getAllQuests();
        LOGGER.info("Loaded " + this.quests.size() + " quests");
        LOGGER.info("QuestManager initialized");
    }

    /**
     * Returns the by id for this quest contract.
     *
     * @param questId Quest id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IQuest getById(int questId) {
        for (IQuest quest : this.quests.values()) {
            if (quest.getId() == questId)
                return quest;
        }

        return null;
    }

    /**
     * Returns the amount of quests in category for this quest contract.
     *
     * @param category Category supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public int getAmountOfQuestsInCategory(String category) {
        int count = 0;

        for (IQuest quest : this.quests.values()) {
            if (quest.getCategory().equals(category)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns the next quest in series for this quest contract.
     *
     * @param lastQuest Last quest supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IQuest getNextQuestInSeries(IQuest lastQuest) {
        for (IQuest quest : this.quests.values()) {
            if (quest.getCategory().equals(lastQuest.getCategory()) &&
                    quest.getSeriesNumber() == (lastQuest.getSeriesNumber() + 1)) {
                return quest;
            }
        }

        return null;
    }

    /**
     * Returns the quests for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<String, IQuest> getQuests() {
        return quests;
    }
}
