package com.cometproject.server.game.polls.types;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Describes poll behavior for the Comet subsystem.
 */
public class Poll {
    private final int pollId;
    private final int roomId;

    private final String pollTitle;
    private final String thanksMessage;

    private final String rewardBadge;
    private final int rewardCredits;
    private final int rewardActivityPoints;
    private final int rewardVipPoints;
    private final int rewardAchievementPoints;

    private final Map<Integer, PollQuestion> pollQuestions;

    private final Set<Integer> playersAnswered;

    /**
     * Creates a poll instance for the Comet subsystem.
     *
     * @param pollId Poll id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param pollTitle Poll title supplied by the caller.
     * @param thanksMessage Thanks message supplied by the caller.
     * @param rewardBadge Reward badge supplied by the caller.
     * @param rewardCredits Reward credits supplied by the caller.
     * @param rewardVipPoints Reward vip points supplied by the caller.
     * @param rewardActivityPoints Reward activity points supplied by the caller.
     * @param rewardAchievementPoints Reward achievement points supplied by the caller.
     */
    public Poll(int pollId, int roomId, String pollTitle, String thanksMessage, String rewardBadge, int rewardCredits, int rewardVipPoints, int rewardActivityPoints, int rewardAchievementPoints) {
        this.pollId = pollId;
        this.roomId = roomId;
        this.pollTitle = pollTitle;
        this.thanksMessage = thanksMessage;

        this.rewardBadge = rewardBadge;
        this.rewardCredits = rewardCredits;
        this.rewardVipPoints = rewardVipPoints;
        this.rewardAchievementPoints = rewardAchievementPoints;
        this.rewardActivityPoints = rewardActivityPoints;

        this.pollQuestions = new ConcurrentHashMap<>();
        this.playersAnswered = new HashSet<>();
    }

    /**
     * Handles the player finished poll callback for this Comet contract.
     *
     * @param player Player participating in the operation.
     */
    public void onPlayerFinishedPoll(Player player) {
        this.getPlayersAnswered().add(player.getId());

        if (this.getRewardBadge() != null && !this.getRewardBadge().isEmpty()) {
            player.getInventory().addBadge(this.getRewardBadge(), true, true);
        }

        boolean save = false;

        if (this.rewardCredits != 0) {
            player.getData().increaseCredits(this.rewardCredits);
            player.getSession().send(new AlertMessageComposer(
                    Locale.getOrDefault("wired.reward.coins", "You received %s coin(s)!").replace("%s", this.rewardCredits + "")));

            player.getSession().send(player.composeCurrenciesBalance());

            save = true;
        }

        if (this.rewardActivityPoints != 0) {
            player.getData().increaseCurrency(currencyCodeForUseCase(CurrencyUseCases.POLL_REWARD_PRIMARY), this.rewardActivityPoints);
            player.getSession().send(new AlertMessageComposer(
                    Locale.getOrDefault("wired.reward.duckets", "You received %s ducket(s)!").replace("%s", this.rewardActivityPoints + "")));

            player.getSession().send(player.composeCurrenciesBalance());

            save = true;
        }

        if (this.rewardAchievementPoints != 0) {
            // reward achievements points
            player.getData().increaseAchievementPoints(this.rewardAchievementPoints);

            save = true;
        }

        if (this.rewardVipPoints != 0) {
            player.getData().increaseCurrency(currencyCodeForUseCase(CurrencyUseCases.POLL_REWARD_SECONDARY), this.rewardVipPoints);
            player.getSession().send(player.composeCurrenciesBalance());

            player.getSession().send(new AlertMessageComposer(
                    Locale.getOrDefault("wired.reward.diamonds", "You received %s diamonds(s)!").replace("%s", this.rewardVipPoints + "")));
            save = true;
        }

        if (save) {
            player.getData().save();
        }
    }

    private static String currencyCodeForUseCase(final String useCase) {
        return CometBootstrap.resolve(ICurrencyService.class).currencyCodeForUseCase(useCase);
    }

    /**
     * Adds question to this Comet contract.
     *
     * @param questionId Question id supplied by the caller.
     * @param pollQuestion Poll question supplied by the caller.
     */
    public void addQuestion(int questionId, PollQuestion pollQuestion) {
        this.pollQuestions.put(questionId, pollQuestion);
    }

    /**
     * Indicates whether final question applies to this Comet contract.
     *
     * @param questionId Question id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isFinalQuestion(final int questionId) {
        int index = 0;
        int count = this.pollQuestions.size();

        for (Map.Entry<Integer, PollQuestion> pollQuestion : this.pollQuestions.entrySet()) {
            index++;

            if (pollQuestion.getKey() == questionId && index >= count) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the poll id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPollId() {
        return pollId;
    }

    /**
     * Returns the room id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Returns the poll title for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPollTitle() {
        return pollTitle;
    }

    /**
     * Returns the poll questions for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, PollQuestion> getPollQuestions() {
        return pollQuestions;
    }

    /**
     * Returns the thanks message for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getThanksMessage() {
        return thanksMessage;
    }

    /**
     * Returns the players answered for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getPlayersAnswered() {
        return playersAnswered;
    }

    /**
     * Returns the reward badge for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRewardBadge() {
        return rewardBadge;
    }
}
