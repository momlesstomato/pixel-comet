package com.cometproject.api.game.players.data.components.inventory;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.furniture.types.LimitedEditionItem;
import com.cometproject.api.networking.messages.IComposer;

/**
 * Defines the player item contract for the player subsystem.
 */
public interface PlayerItem {
    /**
     * Returns the id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getId();

    /**
     * Returns the definition associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    FurnitureDefinition getDefinition();

    /**
     * Returns the base id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBaseId();

    /**
     * Returns the extra data associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getExtraData();

    /**
     * Returns the limited edition item associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    LimitedEditionItem getLimitedEditionItem();

    /**
     * Returns the virtual id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getVirtualId();

    /**
     * Executes the compose operation for this player contract.
     *
     * @param message Message value supplied by the caller.
     */
    void compose(IComposer message);

    /**
     * Creates snapshot data for this player contract.
     *
     * @return Result produced by the mutation.
     */
    PlayerItemSnapshot createSnapshot();
}

