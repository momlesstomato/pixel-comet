package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates reward type values used by the room subsystem.
 */
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

    /**
     * Returns the currency type by key for this room contract.
     *
     * @param str Str supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static RewardType getCurrencyTypeByKey(String str) {
        return map.getOrDefault(str, CURRENCY);
    }

    /**
     * Returns the currency for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCurrency() {
        return this.currency;
    }
}
