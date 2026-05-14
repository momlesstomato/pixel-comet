package com.cometproject.server.game.quests.types;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.game.quests.QuestReward;
import com.cometproject.api.game.quests.QuestType;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.quests.QuestManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Describes quest behavior for the quest subsystem.
 */
public class Quest implements IQuest {
    private final int id;
    private final String name;
    private final String category;
    private final int seriesNumber;

    private final int goalType;
    private final int goalData;

    private final int reward;
    private final int timestamp;
    private final QuestReward rewardType;

    private final String dataBit;

    private final QuestType questType;
    private final String badgeId;

    /**
     * Creates a quest instance for the quest subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public Quest(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.name = data.getString("name");
        this.category = data.getString("category");
        this.seriesNumber = data.getInt("series_number");
        this.goalType = data.getInt("goal_type");
        this.goalData = data.getInt("goal_data");

        this.reward = data.getInt("reward");
        this.rewardType = QuestReward.valueOf(data.getString("reward_type"));
        this.dataBit = data.getString("data_bit");

        this.questType = QuestType.getById(this.goalType);
        this.badgeId = data.getString("badge_id");
        this.timestamp = data.getInt("timestamp");
    }

    /**
     * Creates a quest instance for the quest subsystem.
     *
     * @param id Id supplied by the caller.
     * @param name Name supplied by the caller.
     * @param category Category supplied by the caller.
     * @param seriesNumber Series number supplied by the caller.
     * @param goalType Goal type supplied by the caller.
     * @param goalData Goal data supplied by the caller.
     * @param reward Reward supplied by the caller.
     * @param rewardType Reward type supplied by the caller.
     * @param dataBit Data bit supplied by the caller.
     * @param questType Quest type supplied by the caller.
     * @param badgeId Badge id supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    public Quest(int id, String name, String category, int seriesNumber, int goalType, int goalData, int reward, QuestReward rewardType, String dataBit, QuestType questType, String badgeId, int timestamp) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.seriesNumber = seriesNumber;
        this.goalType = goalType;
        this.goalData = goalData;
        this.reward = reward;
        this.rewardType = rewardType;
        this.dataBit = dataBit;
        this.questType = questType;
        this.badgeId = badgeId;
        this.timestamp = timestamp;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param player Player participating in the operation.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IPlayer player, IComposer msg) {
        boolean startedQuest = player.getData().getQuestId() == this.getId();
        int progress = player.getQuests().getProgress(this.getId());

        msg.writeString(this.getCategory());
        msg.writeInt(player.getQuests().hasCompletedQuest(this.getId()) ? this.getSeriesNumber() : (this.getSeriesNumber() - 1));
        msg.writeInt(QuestManager.getInstance().getAmountOfQuestsInCategory(this.getCategory()));
        msg.writeInt(this.getRewardType().equals(QuestReward.SEASONAL_POINTS) ? 103 : this.getRewardType().equals(QuestReward.CREDITS) ? 3 : this.getRewardType().equals(QuestReward.VIP_POINTS) ? 5 : -1); // reward type (pixels)
        msg.writeInt(this.getId());
        msg.writeBoolean(startedQuest); // started
        msg.writeString(this.getType().getAction());
        msg.writeString(this.getDataBit());
        msg.writeInt(this.getReward());//reward
        msg.writeString(this.getName());
        msg.writeInt(progress); // progress
        msg.writeInt(this.getGoalData()); // total steps to get goal
        msg.writeInt(0); // sort order
        msg.writeString("");
        msg.writeString("");
        msg.writeBoolean(true);// has hint.
        msg.writeInt(this.getTimestamp() != 0 ? (int)(this.getTimestamp() - Comet.getTime()) : 0);
    }

    /**
     * Returns the type for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public QuestType getType() {
        return this.questType;
    }

    /**
     * Returns the id for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns the name for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the category for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Returns the series number for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getSeriesNumber() {
        return seriesNumber;
    }

    /**
     * Returns the goal type for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getGoalType() {
        return goalType;
    }

    /**
     * Returns the goal data for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getGoalData() {
        return goalData;
    }

    /**
     * Returns the reward for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getReward() {
        return reward;
    }

    /**
     * Returns the timestamp for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the reward type for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public QuestReward getRewardType() {
        return rewardType;
    }

    /**
     * Returns the data bit for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDataBit() {
        return dataBit;
    }

    /**
     * Returns the badge id for this quest contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getBadgeId() {
        return badgeId;
    }
}
