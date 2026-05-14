package com.cometproject.server.utilities;

import java.util.concurrent.ThreadLocalRandom;


/**
 * Describes random util behavior for the Comet subsystem.
 */
public class RandomUtil {
    /**
     * Returns the random int for this Comet contract.
     *
     * @param min Min supplied by the caller.
     * @param max Max supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt((max - min) + 1) + min;
    }

    /**
     * Returns the random bool for this Comet contract.
     *
     * @param p P supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean getRandomBool(double p) {
        return (ThreadLocalRandom.current().nextDouble() < p);
    }
}
