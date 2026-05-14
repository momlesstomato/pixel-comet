package com.cometproject.server.game.rooms.types.components.games.casino.types;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;

/**
 * Describes casino player behavior for the room processing subsystem.
 */
public class CasinoPlayer {
    private final PlayerEntity entity;

    private int bet = 0;
    private int multiplyer = 1;

    /**
     * Creates a casino player instance for the room processing subsystem.
     *
     * @param playerEntity Player entity supplied by the caller.
     */
    public CasinoPlayer(PlayerEntity playerEntity) {
        this.entity = playerEntity;
    }

    /**
     * Returns the entity for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerEntity getEntity() {
        return entity;
    }
}
