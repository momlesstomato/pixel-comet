package com.cometproject.storage.mysql.data.results;

/**
 * Defines the result reader consumer contract for the MySQL storage subsystem.
 */
public interface ResultReaderConsumer {
    /**
     * Executes accept for this Comet contract.
     *
     * @param data Data supplied by the caller.
     */
    void accept(IResultReader data) throws Exception;
}
