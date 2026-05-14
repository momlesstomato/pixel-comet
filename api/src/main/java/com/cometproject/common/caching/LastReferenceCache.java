package com.cometproject.common.caching;

import com.cometproject.api.caching.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Describes last reference cache behavior for the Comet subsystem.
 */
public class LastReferenceCache<TKey, TObj> implements Cache<TKey, TObj> {

    private final Map<TKey, CacheEntry<TObj>> cache;

    private final long objectLifetimeMillis;
    private final BiConsumer<TKey, TObj> expireConsumer;

    private final Future processFuture;

    /**
     * Creates a last reference cache instance for the Comet subsystem.
     *
     * @param objectLifetimeMillis Object lifetime millis value supplied by the caller.
     * @param lifetimeCheckDelayMillis Lifetime check delay millis value supplied by the caller.
     * @param expireConsumer Expire consumer value supplied by the caller.
     * @param executorService Executor service value supplied by the caller.
     */
    public LastReferenceCache(long objectLifetimeMillis, long lifetimeCheckDelayMillis,
                              BiConsumer<TKey, TObj> expireConsumer, ScheduledExecutorService executorService) {
        this.cache = new ConcurrentHashMap<>();
        this.expireConsumer = expireConsumer;
        this.objectLifetimeMillis = objectLifetimeMillis;

        this.processFuture = executorService.schedule(this::processExpiredObjects, lifetimeCheckDelayMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Executes for each for this Comet contract.
     *
     * @param consumer Consumer supplied by the caller.
     */
    @Override
    public void forEach(BiConsumer<TKey, TObj> consumer) {
        for(Map.Entry<TKey, CacheEntry<TObj>> entry : cache.entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue().getObject());
        }
    }

    private void processExpiredObjects() {
        for(Map.Entry<TKey, CacheEntry<TObj>> cacheEntry : cache.entrySet()) {
            if(cacheEntry.getValue().hasExpired(this.objectLifetimeMillis)) {
                if(this.expireConsumer != null) {
                    this.expireConsumer.accept(cacheEntry.getKey(), cacheEntry.getValue().getObject());
                }

                this.cache.remove(cacheEntry.getKey());
            }
        }
    }

    /**
     * Executes get for this Comet contract.
     *
     * @param tKey T key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public TObj get(TKey tKey) {
        return this.cache.get(tKey).getObject();
    }

    /**
     * Executes remove for this Comet contract.
     *
     * @param key Key supplied by the caller.
     */
    @Override
    public void remove(TKey key) {
        this.cache.remove(key);
    }

    /**
     * Executes add for this Comet contract.
     *
     * @param key Key supplied by the caller.
     * @param obj Obj supplied by the caller.
     */
    @Override
    public void add(TKey key, TObj obj) {
        this.cache.put(key, new CacheEntry<>(obj));
    }

    /**
     * Executes contains for this Comet contract.
     *
     * @param tKey T key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean contains(TKey tKey) {
        return this.cache.containsKey(tKey);
    }

    private class CacheEntry<T> {
        private T obj;
        private long lastAccessed = System.currentTimeMillis();

        /**
         * Executes cache entry for this Comet contract.
         *
         * @param obj Obj supplied by the caller.
         */
        public CacheEntry(T obj) {
            this.obj = obj;
        }

        /**
         * Returns the object for this Comet contract.
         *
         * @return Value exposed by the contract.
         */
        public T getObject() {
            this.lastAccessed = System.currentTimeMillis();

            return obj;
        }

        /**
         * Executes has expired for this Comet contract.
         *
         * @param objectLifetime Object lifetime supplied by the caller.
         * @return True when the condition is satisfied; otherwise false.
         */
        public boolean hasExpired(long objectLifetime) {
            return (System.currentTimeMillis() - objectLifetime) < lastAccessed;
        }
    }
}
