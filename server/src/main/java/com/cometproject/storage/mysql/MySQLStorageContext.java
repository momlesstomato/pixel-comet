package com.cometproject.storage.mysql;

/**
 * Describes my SQL storage context behavior for the MySQL storage subsystem.
 */
public class MySQLStorageContext {
    private static MySQLStorageContext currentContext;

    private final MySQLConnectionProvider connectionProvider;

    /**
     * Creates a my SQL storage context instance for the MySQL storage subsystem.
     *
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public MySQLStorageContext(MySQLConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    /**
     * Returns the connection provider for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    public MySQLConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    /**
     * Returns the current context for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static MySQLStorageContext getCurrentContext() {
        return currentContext;
    }

    /**
     * Updates the current context for this MySQL storage contract.
     *
     * @param storageContext Storage context supplied by the caller.
     */
    public static void setCurrentContext(MySQLStorageContext storageContext) {
        currentContext = storageContext;
    }

}
