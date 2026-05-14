package com.cometproject.api.game.moderation;

/**
 * Enumerates help ticket state values used by the Comet subsystem.
 */
public enum HelpTicketState {
    OPEN(1),
    IN_PROGRESS(2),
    CLOSED(0),
    INVALID(0),
    RESOLVED(0),
    ABUSIVE(0);

    private int tabId;

    HelpTicketState(int tabId) {
        this.tabId = tabId;
    }

    /**
     * Returns the tab id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTabId() {
        return this.tabId;
    }
}
