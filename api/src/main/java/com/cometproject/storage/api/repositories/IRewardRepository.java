package com.cometproject.storage.api.repositories;

import com.cometproject.storage.api.data.rewards.RewardData;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Defines the i reward repository contract for the storage subsystem.
 */
public interface IRewardRepository {
    /**
     * Executes the player received reward operation for this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param badgeCode Badge code value supplied by the caller.
     * @param consumer Consumer value supplied by the caller.
     */
    void playerReceivedReward(int playerId, String badgeCode, Consumer<Boolean> consumer);

    /**
     * Executes the give reward operation for this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param badgeCode Badge code value supplied by the caller.
     * @param primaryCurrencyAmount Primary currency amount value supplied by the caller.
     * @param secondaryCurrencyAmount Secondary currency amount value supplied by the caller.
     */
    void giveReward(int playerId, String badgeCode, int primaryCurrencyAmount, int secondaryCurrencyAmount);

    /**
     * Returns the active rewards associated with this storage contract.
     *
     * @param consumer Consumer value supplied by the caller.
     */
    void getActiveRewards(Consumer<Map<String, RewardData>> consumer);

    /**
     * Executes the player redeemed reward operation for this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param code Code value supplied by the caller.
     * @param consumer Consumer value supplied by the caller.
     */
    void playerRedeemedReward(int playerId, String code, Consumer<Boolean> consumer);

    /**
     * Executes the redeem reward operation for this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param rewardData Reward data value supplied by the caller.
     */
    void redeemReward(int playerId, RewardData rewardData);
}
