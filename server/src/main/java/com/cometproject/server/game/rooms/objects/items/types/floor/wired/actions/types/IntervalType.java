package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates interval type values used by the room subsystem.
 */
public enum IntervalType {
    ONCE(0),
    DAYS(1),
    HOURS(2),
    MINUTES(3);


    private static Map<Integer, IntervalType> map = new HashMap<Integer, IntervalType>();

    private int type;

    IntervalType(int type) {
        this.type = type;
    }

    static {
        for (IntervalType intervalType : IntervalType.values()) {
            map.put(intervalType.type, intervalType);
        }
    }

    /**
     * Returns the interval by int for this room contract.
     *
     * @param integer Integer supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static IntervalType getIntervalByInt(Integer integer) {
        return map.get(integer);
    }

    /**
     * Returns the integer for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getInteger() {
        return this.type;
    }
}
