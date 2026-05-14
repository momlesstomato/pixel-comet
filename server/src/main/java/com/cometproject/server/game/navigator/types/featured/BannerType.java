package com.cometproject.server.game.navigator.types.featured;

/**
 * Enumerates banner type values used by the navigator subsystem.
 */
public enum BannerType {
    BIG,
    SMALL;

    /**
     * Executes get for this navigator contract.
     *
     * @param t T supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static BannerType get(String t) {
        if (t.equals("big")) {
            return BIG;
        }
        return SMALL;
    }
}
