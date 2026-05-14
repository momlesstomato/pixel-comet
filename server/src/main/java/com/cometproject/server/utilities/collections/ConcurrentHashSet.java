package com.cometproject.server.utilities.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Describes concurrent hash set behavior for the Comet subsystem.
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E> {
    private final Map<E, Boolean> backingMap;
    private final Set<E> backingMapSet;

    /**
     * Creates a concurrent hash set instance for the Comet subsystem.
     */
    public ConcurrentHashSet() {
        backingMap = new ConcurrentHashMap<E, Boolean>();
        backingMapSet = backingMap.keySet();
    }

    /**
     * Executes size for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public int size() {
        return backingMap.size();
    }

    /**
     * Indicates whether empty applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    /**
     * Executes contains for this Comet contract.
     *
     * @param o O supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean contains(Object o) {
        return backingMap.containsKey(o);
    }

    /**
     * Executes iterator for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public Iterator<E> iterator() {
        return backingMapSet.iterator();
    }

    /**
     * Executes clear for this Comet contract.
     */
    public void clear() {
        backingMap.clear();
    }

    /**
     * Executes to array for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public Object[] toArray() {
        return backingMapSet.toArray();
    }

    /**
     * Executes to array for this Comet contract.
     *
     * @param a A supplied by the caller.
     * @return Result produced by the operation.
     */
    public <T> T[] toArray(T[] a) {
        return backingMapSet.toArray(a);
    }

    /**
     * Executes add for this Comet contract.
     *
     * @param e E supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean add(E e) {
        return backingMap.put(e, Boolean.TRUE) == null;
    }

    /**
     * Executes remove for this Comet contract.
     *
     * @param o O supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean remove(Object o) {
        return backingMap.remove(o) != null;
    }

    /**
     * Executes contains all for this Comet contract.
     *
     * @param c C supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean containsAll(Collection<?> c) {
        return backingMapSet.containsAll(c);
    }

    /**
     * Removes all from this Comet contract.
     *
     * @param c C supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean removeAll(Collection<?> c) {
        return backingMapSet.removeAll(c);
    }

    /**
     * Executes retain all for this Comet contract.
     *
     * @param c C supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean retainAll(Collection<?> c) {
        return backingMapSet.retainAll(c);
    }

    /**
     * Executes to string for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public String toString() {
        return backingMapSet.toString();
    }

    /**
     * Indicates whether this Comet contract has h code.
     *
     * @return Result produced by the operation.
     */
    public int hashCode() {
        return backingMapSet.hashCode();
    }

    /**
     * Executes equals for this Comet contract.
     *
     * @param o O supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean equals(Object o) {
        return o == this || backingMapSet.equals(o);
    }

}