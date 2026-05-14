package com.cometproject.server.game.navigator.types.categories;

/**
 * Enumerates navigator search allowance values used by the navigator subsystem.
 */
public enum NavigatorSearchAllowance {
    NOTHING,
    SHOW_MORE,
    GO_BACK;

    /**
     * Returns the int value for this navigator contract.
     *
     * @param allowance Allowance supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getIntValue(NavigatorSearchAllowance allowance) {
        switch (allowance) {
            default:
            case NOTHING:
                return 0;
            case SHOW_MORE:
                return 1;
            case GO_BACK:
                return 2;
        }
    }
}
