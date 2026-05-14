package com.cometproject.api.messaging.performance;

/**
 * Describes query request behavior for the Comet subsystem.
 */
public class QueryRequest {
    private final long timeTakenMs;
    private final String query;

    /**
     * Creates a query request instance for the messaging subsystem.
     *
     * @param query Query value supplied by the caller.
     * @param timeTakenMs Time taken ms value supplied by the caller.
     */
    public QueryRequest(String query, long timeTakenMs) {
        this.query = query;
        this.timeTakenMs = timeTakenMs;
    }

    /**
     * Returns the time taken ms for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public long getTimeTakenMs() {
        return timeTakenMs;
    }

    /**
     * Returns the query for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getQuery() {
        return query;
    }
}
