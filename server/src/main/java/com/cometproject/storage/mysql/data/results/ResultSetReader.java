package com.cometproject.storage.mysql.data.results;

import java.sql.ResultSet;

/**
 * Describes result set reader behavior for the MySQL storage subsystem.
 */
public class ResultSetReader implements IResultReader {

    private final ResultSet resultSet;

    /**
     * Creates a result set reader instance for the MySQL storage subsystem.
     *
     * @param resultSet Result set supplied by the caller.
     */
    public ResultSetReader(final ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Executes read string for this MySQL storage contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public String readString(String columnName) throws Exception {
        return this.resultSet.getString(columnName);
    }

    /**
     * Executes read string for this MySQL storage contract.
     *
     * @param index Index supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public String readString(int index) throws Exception {
        return this.resultSet.getString(index);
    }

    /**
     * Executes read integer for this MySQL storage contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public int readInteger(String columnName) throws Exception {
        return this.resultSet.getInt(columnName);
    }

    /**
     * Executes read integer for this MySQL storage contract.
     *
     * @param index Index supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public int readInteger(int index) throws Exception {
        return this.resultSet.getInt(index);
    }

    /**
     * Executes read long for this MySQL storage contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public long readLong(String columnName) throws Exception {
        return this.resultSet.getLong(columnName);
    }

    /**
     * Executes read long for this MySQL storage contract.
     *
     * @param index Index supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public long readLong(int index) throws Exception {
        return this.resultSet.getLong(index);
    }

    /**
     * Executes read boolean for this MySQL storage contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public boolean readBoolean(String columnName) throws Exception {
        return this.resultSet.getBoolean(columnName);
    }

    /**
     * Executes read boolean for this MySQL storage contract.
     *
     * @param index Index supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public boolean readBoolean(int index) throws Exception {
        return this.resultSet.getBoolean(index);
    }

    /**
     * Executes read double for this MySQL storage contract.
     *
     * @param columnName Column name supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public double readDouble(String columnName) throws Exception {
        return this.resultSet.getDouble(columnName);
    }

    /**
     * Executes read double for this MySQL storage contract.
     *
     * @param index Index supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public double readDouble(int index) throws Exception {
        return this.resultSet.getDouble(index);
    }
}
