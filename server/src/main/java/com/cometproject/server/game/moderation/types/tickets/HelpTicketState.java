package com.cometproject.server.game.moderation.types.tickets;

/**
 * Enumerates help ticket state values used by the moderation subsystem.
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
     * Returns the tab id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTabId() {
        return this.tabId;
    }
}
