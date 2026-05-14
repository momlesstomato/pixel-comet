package com.cometproject.api.game.furniture.types;

/**
 * Enumerates item type values used by the furniture subsystem.
 */
public enum ItemType {
    WALL("i"),
    FLOOR("s"),
    EFFECT("e");

    private String type;

    ItemType(String type) {
        this.type = type;
    }

    /**
     * Returns the type for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public String getType() {
        return type;
    }

    /**
     * Executes the for string operation for this furniture contract.
     *
     * @param str Str value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static ItemType forString(final String str) {
        if(str.equals("i")) {
            return WALL;
        } else if(str.equals("e")) {
            return EFFECT;
        }

        return FLOOR;
    }
}
