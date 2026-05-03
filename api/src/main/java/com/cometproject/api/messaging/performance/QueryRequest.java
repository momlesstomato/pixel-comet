package com.cometproject.api.messaging.performance;

public class QueryRequest {
    private final long timeTakenMs;
    private final String query;

    public QueryRequest(String query, long timeTakenMs) {
        this.query = query;
        this.timeTakenMs = timeTakenMs;
    }

    public long getTimeTakenMs() {
        return timeTakenMs;
    }

    public String getQuery() {
        return query;
    }
}
