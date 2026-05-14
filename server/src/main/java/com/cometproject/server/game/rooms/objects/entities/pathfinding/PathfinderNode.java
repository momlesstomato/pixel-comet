package com.cometproject.server.game.rooms.objects.entities.pathfinding;

import com.cometproject.api.game.utilities.Position;


/**
 * Describes pathfinder node behavior for the room pathfinding subsystem.
 */
public class PathfinderNode implements Comparable<PathfinderNode> {
    private Position position;
    private PathfinderNode nextNode;

    private Integer cost = Integer.MAX_VALUE;
    private boolean inOpen = false;
    private boolean inClosed = false;

    /**
     * Creates a pathfinder node instance for the room pathfinding subsystem.
     *
     * @param current Current supplied by the caller.
     */
    public PathfinderNode(Position current) {
        this.position = current;
    }

    /**
     * Returns the position for this room pathfinding contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the position for this room pathfinding contract.
     *
     * @param position Position supplied by the caller.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns the next node for this room pathfinding contract.
     *
     * @return Value exposed by the contract.
     */
    public PathfinderNode getNextNode() {
        return nextNode;
    }

    /**
     * Updates the next node for this room pathfinding contract.
     *
     * @param nextNode Next node supplied by the caller.
     */
    public void setNextNode(PathfinderNode nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * Returns the cost for this room pathfinding contract.
     *
     * @return Value exposed by the contract.
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * Updates the cost for this room pathfinding contract.
     *
     * @param cost Cost supplied by the caller.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Indicates whether in open applies to this room pathfinding contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isInOpen() {
        return inOpen;
    }

    /**
     * Updates the in open for this room pathfinding contract.
     *
     * @param inOpen In open supplied by the caller.
     */
    public void setInOpen(boolean inOpen) {
        this.inOpen = inOpen;
    }

    /**
     * Indicates whether in closed applies to this room pathfinding contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isInClosed() {
        return inClosed;
    }

    /**
     * Updates the in closed for this room pathfinding contract.
     *
     * @param inClosed In closed supplied by the caller.
     */
    public void setInClosed(boolean inClosed) {
        this.inClosed = inClosed;
    }

    /**
     * Executes equals for this room pathfinding contract.
     *
     * @param obj Obj supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PathfinderNode) && ((PathfinderNode) obj).getPosition().equals(this.position);
    }

    /**
     * Executes equals for this room pathfinding contract.
     *
     * @param node Node supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean equals(PathfinderNode node) {
        return node.getPosition().equals(this.position);
    }

    /**
     * Indicates whether this room pathfinding contract has h code.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int hashCode() {
        return this.position.hashCode();
    }

    /**
     * Executes compare to for this room pathfinding contract.
     *
     * @param o O supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public int compareTo(PathfinderNode o) {
        return this.getCost().compareTo(o.getCost());
    }
}
