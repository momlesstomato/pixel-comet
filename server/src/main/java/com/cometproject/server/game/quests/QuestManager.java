package com.cometproject.server.game.quests;

import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.game.quests.IQuestService;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.storage.queries.quests.QuestsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class QuestManager implements Startable, IQuestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestManager.class.getName());

    private Map<String, IQuest> quests;

    public QuestManager() {

    }

    public static QuestManager getInstance() {
        return CometBootstrap.resolve(QuestManager.class);
    }

    @Override
    public void start() {
        this.loadQuests();
    }

    @Override
    public void loadQuests() {
        if (this.quests != null) {
            this.quests.clear();
        }

        this.quests = QuestsDao.getAllQuests();
        LOGGER.info("Loaded " + this.quests.size() + " quests");
        LOGGER.info("QuestManager initialized");
    }

    @Override
    public IQuest getById(int questId) {
        for (IQuest quest : this.quests.values()) {
            if (quest.getId() == questId)
                return quest;
        }

        return null;
    }

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

    @Override
    public Map<String, IQuest> getQuests() {
        return quests;
    }
}
