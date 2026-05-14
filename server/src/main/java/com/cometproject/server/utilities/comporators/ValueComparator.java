package com.cometproject.server.utilities.comporators;

import java.util.Comparator;
import java.util.Map;


/**
 * Describes value comparator behavior for the Comet subsystem.
 */
public class ValueComparator implements Comparator<String> {

    private Map<String, Integer> base;

    /**
     * Creates a value comparator instance for the Comet subsystem.
     *
     * @param base Base supplied by the caller.
     */
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    /**
     * Executes compare for this Comet contract.
     *
     * @param a A supplied by the caller.
     * @param b B supplied by the caller.
     * @return Result produced by the operation.
     */
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return 1;
        } else {
            return -1;
        }
    }
}
