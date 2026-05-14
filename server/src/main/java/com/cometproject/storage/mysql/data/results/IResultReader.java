package com.cometproject.storage.mysql.data.results;

/**
 * Defines the i result reader contract for the MySQL storage subsystem.
 */
public interface IResultReader {
    /**
     * Executes read string for this Comet contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Value exposed by the contract.
     */
    String readString(String columnName) throws Exception;

    /**
     * Executes read string for this Comet contract.
     *
     * @param index Index supplied by the caller.
     * @return Value exposed by the contract.
     */
    String readString(int index) throws Exception;

    /**
     * Executes read integer for this Comet contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Value exposed by the contract.
     */
    int readInteger(String columnName) throws Exception;

    /**
     * Executes read integer for this Comet contract.
     *
     * @param index Index supplied by the caller.
     * @return Value exposed by the contract.
     */
    int readInteger(int index) throws Exception;

    /**
     * Executes read long for this Comet contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Value exposed by the contract.
     */
    long readLong(String columnName) throws Exception;

    /**
     * Executes read long for this Comet contract.
     *
     * @param index Index supplied by the caller.
     * @return Value exposed by the contract.
     */
    long readLong(int index) throws Exception;

    /**
     * Executes read boolean for this Comet contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean readBoolean(String columnName) throws Exception;

    /**
     * Executes read boolean for this Comet contract.
     *
     * @param index Index supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean readBoolean(int index) throws Exception;

    /**
     * Executes read double for this Comet contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Value exposed by the contract.
     */
    double readDouble(String columnName) throws Exception;

    /**
     * Executes read double for this Comet contract.
     *
     * @param index Index supplied by the caller.
     * @return Value exposed by the contract.
     */
    double readDouble(int index) throws Exception;

}
