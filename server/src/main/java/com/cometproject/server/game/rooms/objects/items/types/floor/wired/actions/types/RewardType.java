package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.types;

import java.util.HashMap;
import java.util.Map;

public enum RewardType {
    CREDITS("credits"),
    CURRENCY("currency"),
    GO_TO_ROOM("goto"),
    ALERT("alert");


    private static Map<String, RewardType> map = new HashMap<String, RewardType>();

    private String currency;

    RewardType(String currency) {
        this.currency = currency;
    }

    static {
        for (RewardType rewardType : RewardType.values()) {
            map.put(rewardType.currency, rewardType);
        }
    }

    public static RewardType getCurrencyTypeByKey(String str) {
        return map.getOrDefault(str, CURRENCY);
    }

    public String getCurrency() {
        return this.currency;
    }
}
