package com.cometproject.server.game.players.components;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.achievements.types.IAchievement;
import com.cometproject.api.game.achievements.types.IAchievementGroup;
import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.components.PlayerAchievements;
import com.cometproject.api.game.players.data.components.achievements.IAchievementProgress;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.achievements.AchievementManager;
import com.cometproject.server.game.players.components.types.achievements.AchievementProgress;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.network.messages.outgoing.messenger.FriendNotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.achievements.AchievementPointsMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.achievements.AchievementProgressMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.achievements.AchievementUnlockedMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.purse.UpdateActivityPointsMessageComposer;
import com.cometproject.server.storage.queries.achievements.PlayerAchievementDao;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Owns achievement behavior inside the player subsystem.
 */
public class AchievementComponent extends PlayerComponent implements PlayerAchievements {
    private Map<AchievementType, IAchievementProgress> progression;

    /**
     * Creates a achievement component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public AchievementComponent(IPlayer player) {
        super(player);

        this.loadAchievements();
    }

    /**
     * Loads achievements for this player contract.
     */
    @Override
    public void loadAchievements() {
        if (this.progression != null) {
            this.progression.clear();
        }

        this.progression = PlayerAchievementDao.getAchievementProgress(this.getPlayer().getId());
    }

    /**
     * Executes progress achievement for this player contract.
     *
     * @param type Type supplied by the caller.
     * @param data Data supplied by the caller.
     */
    @Override
    public void progressAchievement(AchievementType type, int data) {
        IAchievementGroup achievementGroup = AchievementManager.getInstance().getAchievementGroup(type);

        if (achievementGroup == null) {
            return;
        }

        IAchievementProgress progress;

        if (this.progression.containsKey(type)) {
            progress = this.progression.get(type);
        } else {
            progress = new AchievementProgress(1, 0);
            this.progression.put(type, progress);
        }

        int totalLevels = achievementGroup.getAchievements().size();
        int currentLevel = progress.getLevel();
        int currentProgress = progress.getProgress();
        int totalProgressRequired = achievementGroup.getAchievement(totalLevels).getProgressNeeded();

        if (achievementGroup.getAchievements() == null)
            return;

        if (totalLevels == currentLevel && currentProgress == totalProgressRequired) {
            return;
        }

        int targetLevel = currentLevel + 1;

        if(targetLevel >= totalLevels) {
            targetLevel = totalLevels;
        }

        final IAchievement currentAchievement = achievementGroup.getAchievement(currentLevel);
        final IAchievement targetAchievement = achievementGroup.getAchievement(targetLevel);

        int progressToGive = Math.min(currentAchievement.getProgressNeeded(), data);
        int remainingProgress = progressToGive >= data ? 0 : data - progressToGive;

        progress.increaseProgress(progressToGive);


        // Current progress is greater or equal than current progression needed.
        if (progress.getProgress() >= currentAchievement.getProgressNeeded()) {
            // subtract the difference and add it onto remainingProgress.
            int difference = progress.getProgress() - currentAchievement.getProgressNeeded();

            progress.decreaseProgress(difference);
            remainingProgress += difference;

            this.processUnlock(currentAchievement, targetAchievement, achievementGroup, progress, targetLevel, type);
            this.getPlayer().getMessenger().broadcast(new FriendNotificationMessageComposer(this.getPlayer().getId(), 1, achievementGroup.getGroupName() + currentAchievement.getLevel()));
            this.getPlayer().getMessenger().sendStatus(true, this.getPlayer().getEntity() != null);

        } else {
            this.getPlayer().getSession().send(new AchievementProgressMessageComposer(progress, achievementGroup));
        }

        boolean hasFinishedGroup = false;

        if (progress.getLevel() >= achievementGroup.getLevelCount() && progress.getProgress() >= achievementGroup.getAchievement(currentLevel).getProgressNeeded()) {
            hasFinishedGroup = true;
        }

        this.getPlayer().getData().save();
        PlayerAchievementDao.saveProgress(this.getPlayer().getId(), type, progress);

        this.getPlayer().flush();
    }

    private void processUnlock(IAchievement currentAchievement, IAchievement targetAchievement, IAchievementGroup achievementGroup, IAchievementProgress progress, int targetLevel, AchievementType type) {
        this.getPlayer().getData().increaseAchievementPoints(currentAchievement.getRewardAchievement());

        boolean sendsProtocolUpdate = false;
        int protocolCurrencyId = currentAchievement.getRewardType();

        switch (currentAchievement.getRewardType()){
            case 0:
            case 5:
            case 103:
                this.getPlayer().getData().increaseCurrency(
                        CometBootstrap.resolve(ICurrencyService.class).currencyCodeForProtocolId(protocolCurrencyId),
                        currentAchievement.getRewardActivityPoints());
                sendsProtocolUpdate = protocolCurrencyId == 0;
                break;
        }

        this.getPlayer().poof();
        this.getPlayer().sendBalance();

        if(sendsProtocolUpdate) {
            this.getPlayer().getSession().send(new UpdateActivityPointsMessageComposer(
                    this.getPlayer().getData().getCurrencyBalance(CometBootstrap.resolve(ICurrencyService.class).currencyCodeForProtocolId(protocolCurrencyId)),
                    currentAchievement.getRewardAchievement(),
                    protocolCurrencyId));
        }

        boolean hasFinished = false;

        if (achievementGroup.getAchievement(targetLevel) != null) {
            hasFinished = progress.increaseLevel(achievementGroup.getLevelCount());
        }

        // Achievement unlocked!
        this.getPlayer().getSession().send(new AchievementPointsMessageComposer(this.getPlayer().getData().getAchievementPoints(), this.getPlayer().getStats().getLevel()));
        this.getPlayer().getSession().send(new AchievementProgressMessageComposer(progress, achievementGroup));
        this.getPlayer().getSession().send(new AchievementUnlockedMessageComposer(achievementGroup.getCategory().toString(), achievementGroup.getGroupName(), achievementGroup.getId(), targetAchievement, hasFinished));

        this.getPlayer().getInventory().achievementBadge(type.getGroupName(), currentAchievement.getLevel());

        this.getPlayer().flush();
    }

    /**
     * Indicates whether this player contract has started achievement.
     *
     * @param achievementType Achievement type supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasStartedAchievement(AchievementType achievementType) {
        return this.progression.containsKey(achievementType);
    }

    /**
     * Returns the progress for this player contract.
     *
     * @param achievementType Achievement type supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IAchievementProgress getProgress(AchievementType achievementType) {
        return this.progression.get(achievementType);
    }

    /**
     * Releases resources owned by this player component.
     */
    @Override
    public void dispose() {
        this.progression.clear();
    }

    /**
     * Executes to JSON for this player contract.
     *
     * @return Result produced by the operation.
     */
    public JsonArray toJson() {
        final JsonArray coreArray = new JsonArray();

        for(Map.Entry<AchievementType, IAchievementProgress> achievementEntry : progression.entrySet()) {
            final JsonObject achievementObject = new JsonObject();

            achievementObject.addProperty("type", achievementEntry.getKey().getGroupName());
            achievementObject.addProperty("level", achievementEntry.getValue().getLevel());
            achievementObject.addProperty("progress", achievementEntry.getValue().getProgress());

            coreArray.add(achievementObject);
        }

        return coreArray;
    }
}
