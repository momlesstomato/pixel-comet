package com.cometproject.server.game.players.components.types.wardrobe;

/**
 * Describes wardrobe clothing behavior for the player subsystem.
 */
public class WardrobeClothing {
    private final int id;
    private final int partId;
    private final int part;

    /**
     * Creates a wardrobe clothing instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param partId Part id supplied by the caller.
     * @param part Part supplied by the caller.
     */
    public WardrobeClothing(int id, int partId, int part) {
        this.id = id;
        this.partId = partId;
        this.part = part;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the part id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPartId() {
        return partId;
    }

    /**
     * Returns the part for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPart() {
        return part;
    }
}
