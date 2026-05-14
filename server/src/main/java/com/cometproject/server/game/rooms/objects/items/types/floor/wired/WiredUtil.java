package com.cometproject.server.game.rooms.objects.items.types.floor.wired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Describes wired util behavior for the room subsystem.
 */
public class WiredUtil {
    public static final int MAX_FURNI_SELECTION = 30;

    public static final int PARAM_STATE = 0;
    public static final int PARAM_ROTATION = 1;
    public static final int PARAM_POSITION = 2;

    /**
     * Returns the random element for this room contract.
     *
     * @param elements Elements supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static <T> T getRandomElement(List<T> elements) {
        int size = elements.size();
        if (size > 0) {
            return elements.get(ThreadLocalRandom.current().nextInt(size));
        } else {
            return null;
        }
    }

    /**
     * Returns the random element for this room contract.
     *
     * @param elements Elements supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static <T> T getRandomElement(Collection<T> elements) {
        List<T> list = new ArrayList<T>(elements);

        return getRandomElement(list);
    }
}
