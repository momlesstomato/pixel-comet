package com.cometproject.networking.api;

/**
 * Describes networking context behavior for the networking subsystem.
 */
public class NetworkingContext {
    private static NetworkingContext currentContext;

    private final INetworkingServerFactory serverFactory;

    /**
     * Creates a networking context instance for the networking subsystem.
     *
     * @param serverFactory Server factory value supplied by the caller.
     */
    public NetworkingContext(INetworkingServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    /**
     * Returns the server factory for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public INetworkingServerFactory getServerFactory() {
        return serverFactory;
    }

    /**
     * Returns the current context for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public static NetworkingContext getCurrentContext() {
        return currentContext;
    }

    /**
     * Updates the current context for this networking contract.
     *
     * @param networkingContext Networking context supplied by the caller.
     */
    public static void setCurrentContext(NetworkingContext networkingContext) {
        currentContext = networkingContext;
    }
}
