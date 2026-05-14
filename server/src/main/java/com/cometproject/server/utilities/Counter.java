package com.cometproject.server.utilities;

/**
 * Describes counter behavior for the Comet subsystem.
 */
public class Counter {
    private int currentCount;

    /**
     * Creates a counter instance for the Comet subsystem.
     *
     * @param start Start supplied by the caller.
     */
    public Counter(int start) {
        this.currentCount = start;
    }

    /**
     * Creates a counter instance for the Comet subsystem.
     */
    public Counter() {
        this.currentCount = 0;
    }

    /**
     * Executes increase for this Comet contract.
     */
    public void increase() {
        this.increase(1);
    }

    /**
     * Executes increase for this Comet contract.
     *
     * @param delta Delta supplied by the caller.
     */
    public void increase(int delta) {
        this.currentCount += delta;
    }

    /**
     * Executes increase and get for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public int increaseAndGet() {
        this.increase(1);

        return this.currentCount;
    }

    /**
     * Executes increase and get for this Comet contract.
     *
     * @param delta Delta supplied by the caller.
     * @return Result produced by the operation.
     */
    public int increaseAndGet(int delta) {
        this.increase(delta);

        return this.currentCount;
    }

    /**
     * Executes decrease for this Comet contract.
     */
    public void decrease() {
        this.decrease(1);
    }

    /**
     * Executes decrease for this Comet contract.
     *
     * @param delta Delta supplied by the caller.
     */
    public void decrease(int delta) {
        this.currentCount -= delta;
    }

    /**
     * Executes set for this Comet contract.
     *
     * @param newCounter New counter supplied by the caller.
     */
    public void set(int newCounter) {
        this.currentCount = newCounter;
    }

    /**
     * Executes get for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int get() {
        return this.currentCount;
    }
}
