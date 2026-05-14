package com.cometproject.storage.api.data;

/**
 * Describes data behavior for the storage subsystem.
 */
public class Data<T> {

    private T value;

    /**
     * Creates a data instance for the storage subsystem.
     */
    public Data() {

    }

    /**
     * Executes the set operation for this storage contract.
     *
     * @param value Value value supplied by the caller.
     */
    public void set(T value) {
        this.value = value;
    }

    /**
     * Executes the get operation for this storage contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public T get() {
        return value;
    }

    /**
     * Executes the has operation for this storage contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    public boolean has() {
        return this.value != null;
    }

    /**
     * Creates empty data for this storage contract.
     *
     * @return Result produced by the mutation.
     */
    public static <T> Data<T> createEmpty() {
        return new Data<T>();
    }
}
