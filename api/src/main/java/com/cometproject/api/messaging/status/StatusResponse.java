package com.cometproject.api.messaging.status;

import com.cometproject.api.stats.CometStats;

/**
 * Describes status response behavior for the Comet subsystem.
 */
public class StatusResponse {
    private final CometStats status;
    private final String version;

    /**
     * Creates a status response instance for the messaging subsystem.
     *
     * @param status Status value supplied by the caller.
     * @param version Version value supplied by the caller.
     */
    public StatusResponse(final CometStats status, final String version) {
        this.status = status;
        this.version = version;
    }

    /**
     * Returns the status for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public CometStats getStatus() {
        return status;
    }

    /**
     * Returns the version for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getVersion() {
        return version;
    }
}
