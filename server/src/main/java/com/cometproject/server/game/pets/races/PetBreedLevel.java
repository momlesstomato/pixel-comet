package com.cometproject.server.game.pets.races;

/**
 * Enumerates pet breed level values used by the pet subsystem.
 */
public enum PetBreedLevel {
    COMMON(3),
    UNCOMMON(2),
    RARE(1),
    EPIC(0);

    private int levelId;

    PetBreedLevel(int levelId) {
        this.levelId = levelId;
    }

    /**
     * Returns the level id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLevelId() {
        return this.levelId;
    }
}
