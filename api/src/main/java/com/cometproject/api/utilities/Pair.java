package com.cometproject.api.utilities;

/**
 * Describes pair behavior for the Comet subsystem.
 */
public class Pair<L, R> {

    private final L left;
    private final R right;

    /**
     * Creates a pair instance for the utility subsystem.
     *
     * @param left Left value supplied by the caller.
     * @param right Right value supplied by the caller.
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public L getLeft() {
        return left;
    }

    /**
     * Returns the right for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public R getRight() {
        return right;
    }

    /**
     * Executes hash code for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    /**
     * Executes equals for this Comet contract.
     *
     * @param o O supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

}
