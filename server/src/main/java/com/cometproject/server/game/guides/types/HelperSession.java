package com.cometproject.server.game.guides.types;

/**
 * Describes helper session behavior for the Comet subsystem.
 */
public class HelperSession {
    public HelpRequest handlingRequest;
    private int playerId;
    private boolean tourRequests;
    private boolean helpRequests;
    private boolean bullyReports;

    /**
     * Creates a helper session instance for the Comet subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param tourRequests Tour requests supplied by the caller.
     * @param helpRequests Help requests supplied by the caller.
     * @param bullyReports Bully reports supplied by the caller.
     */
    public HelperSession(int playerId, boolean tourRequests, boolean helpRequests, boolean bullyReports) {
        this.playerId = playerId;

        this.tourRequests = tourRequests;
        this.helpRequests = helpRequests;
        this.bullyReports = bullyReports;
    }

    /**
     * Returns the player id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Handles s tour requests for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean handlesTourRequests() {
        return tourRequests;
    }

    /**
     * Handles s help requests for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean handlesHelpRequests() {
        return helpRequests;
    }

    /**
     * Handles s bully reports for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean handlesBullyReports() {
        return bullyReports;
    }
}
