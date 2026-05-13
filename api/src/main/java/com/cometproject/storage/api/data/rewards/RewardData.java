package com.cometproject.storage.api.data.rewards;

public class RewardData {
    private String code;
    private String badge;
    private int primaryCurrencyAmount;
    private int secondaryCurrencyAmount;

    public RewardData(String code, String badge, int primaryCurrencyAmount, int secondaryCurrencyAmount) {
        this.code = code;
        this.badge = badge;
        this.primaryCurrencyAmount = primaryCurrencyAmount;
        this.secondaryCurrencyAmount = secondaryCurrencyAmount;
    }

    public String getCode() {
        return code;
    }

    public String getBadge() {
        return badge;
    }

    public int getPrimaryCurrencyAmount() {
        return primaryCurrencyAmount;
    }

    public int getSecondaryCurrencyAmount() {
        return secondaryCurrencyAmount;
    }
}
