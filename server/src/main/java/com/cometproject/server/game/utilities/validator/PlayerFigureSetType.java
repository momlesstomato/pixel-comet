package com.cometproject.server.game.utilities.validator;

import java.util.Map;

/**
 * Describes player figure set type behavior for the Comet subsystem.
 */
public class PlayerFigureSetType {

    private String typeName;
    private int paletteId;
    private Map<Integer, PlayerFigureSet> sets;

    /**
     * Creates a player figure set type instance for the Comet subsystem.
     *
     * @param typeName Type name supplied by the caller.
     * @param paletteId Palette id supplied by the caller.
     * @param sets Sets supplied by the caller.
     */
    public PlayerFigureSetType(final String typeName, final int paletteId, final Map<Integer, PlayerFigureSet> sets) {
        this.typeName = typeName;
        this.paletteId = paletteId;
        this.sets = sets;
    }

    /**
     * Returns the sets for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, PlayerFigureSet> getSets() {
        return this.sets;
    }

    /**
     * Returns the palette id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPaletteId() {
        return this.paletteId;
    }

    /**
     * Returns the type name for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTypeName() {
        return this.typeName;
    }
}
