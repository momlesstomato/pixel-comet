package com.cometproject.storage.api.data.rewards;

/**
 * Describes reward data behavior for the storage subsystem.
 */
public class RewardData {
    private String code;
    private String badge;
    private int primaryCurrencyAmount;
    private int secondaryCurrencyAmount;

    /**
     * Creates a reward data instance for the storage subsystem.
     *
     * @param code Code value supplied by the caller.
     * @param badge Badge value supplied by the caller.
     * @param primaryCurrencyAmount Primary currency amount value supplied by the caller.
     * @param secondaryCurrencyAmount Secondary currency amount value supplied by the caller.
     */
    public RewardData(String code, String badge, int primaryCurrencyAmount, int secondaryCurrencyAmount) {
        this.code = code;
        this.badge = badge;
        this.primaryCurrencyAmount = primaryCurrencyAmount;
        this.secondaryCurrencyAmount = secondaryCurrencyAmount;
    }

    /**
     * Returns the code for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the badge for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public String getBadge() {
        return badge;
    }

    /**
     * Returns the primary currency amount for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPrimaryCurrencyAmount() {
        return primaryCurrencyAmount;
    }

    /**
     * Returns the secondary currency amount for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSecondaryCurrencyAmount() {
        return secondaryCurrencyAmount;
    }
}
