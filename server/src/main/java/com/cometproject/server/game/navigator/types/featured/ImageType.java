package com.cometproject.server.game.navigator.types.featured;

/**
 * Enumerates image type values used by the navigator subsystem.
 */
public enum ImageType {
    INTERNAL,
    EXTERNAL;

    /**
     * Executes get for this navigator contract.
     *
     * @param t T supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static ImageType get(String t) {
        if (t.equals("internal")) {
            return INTERNAL;
        }
        return EXTERNAL;
    }
}
