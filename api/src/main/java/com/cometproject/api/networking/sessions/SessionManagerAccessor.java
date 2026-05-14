package com.cometproject.api.networking.sessions;

/**
 * Describes session manager accessor behavior for the networking subsystem.
 */
public class SessionManagerAccessor {
    private static SessionManagerAccessor instance;

    private ISessionManager sessionManager;

    /**
     * Returns the session manager for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public ISessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * Updates the session manager for this networking contract.
     *
     * @param sessionManager Session manager supplied by the caller.
     */
    public void setSessionManager(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Returns the instance associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public static SessionManagerAccessor getInstance() {
        if (instance == null) {
            instance = new SessionManagerAccessor();
        }

        return instance;
    }
}
