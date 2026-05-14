package com.cometproject.api.caching;

import java.util.function.BiConsumer;

/**
 * Defines the cache contract for the Comet subsystem.
 */
public interface Cache<TKey, TObj> {

    /**
     * Executes the get operation for this Comet contract.
     *
     * @param key Key value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    TObj get(TKey key);

    /**
     * Executes the remove operation for this Comet contract.
     *
     * @param key Key value supplied by the caller.
     */
    void remove(TKey key);

    /**
     * Executes the add operation for this Comet contract.
     *
     * @param key Key value supplied by the caller.
     * @param obj Obj value supplied by the caller.
     */
    void add(TKey key, TObj obj);

    /**
     * Executes the contains operation for this Comet contract.
     *
     * @param key Key value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean contains(TKey key);

    /**
     * Executes the for each operation for this Comet contract.
     *
     * @param consumer Consumer value supplied by the caller.
     */
    void forEach(BiConsumer<TKey, TObj> consumer);
}
