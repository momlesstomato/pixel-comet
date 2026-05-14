package com.cometproject.server.game.rooms.types.components.games.freeze.types;

import com.cometproject.server.utilities.RandomUtil;

/**
 * Enumerates freeze power up values used by the room processing subsystem.
 */
public enum FreezePowerUp {
    None,
    ExtraRange,
    ExtraBall,
    DiagonalExplosion,
    MegaExplosion,
    Life,
    Shield;

    /**
     * Returns the random for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public static FreezePowerUp getRandom() {
        FreezePowerUp[] powerUps = FreezePowerUp.values();

        return powerUps[RandomUtil.getRandomInt(0, powerUps.length - 1)];
    }
}
