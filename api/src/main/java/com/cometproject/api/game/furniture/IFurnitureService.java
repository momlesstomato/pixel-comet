package com.cometproject.api.game.furniture;

import java.util.Map;

import org.slf4j.Logger;

import com.cometproject.api.game.furniture.types.CrackableReward;
import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.furniture.types.IMusicData;
import com.cometproject.api.utilities.Startable;

/**
 * Defines the i furniture service contract for the furniture subsystem.
 */
public interface IFurnitureService extends Startable {
    /**
     * Loads item definitions data for this furniture contract.
     */
    void loadItemDefinitions();

    /**
     * Loads music data data for this furniture contract.
     */
    void loadMusicData();

    /**
     * Returns the item virtual id associated with this furniture contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getItemVirtualId(long itemId);

    /**
     * Executes the dispose item virtual id operation for this furniture contract.
     *
     * @param itemId Item id value supplied by the caller.
     */
    void disposeItemVirtualId(long itemId);

    /**
     * Returns the item id by virtual id associated with this furniture contract.
     *
     * @param virtualId Virtual id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Long getItemIdByVirtualId(int virtualId);

    /**
     * Returns the teleport partner associated with this furniture contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getTeleportPartner(long itemId);

    /**
     * Executes the room id by item id operation for this furniture contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @return Result produced by the operation.
     */
    int roomIdByItemId(long itemId);

    /**
     * Returns the definition associated with this furniture contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    FurnitureDefinition getDefinition(int itemId);

    /**
     * Returns the music data associated with this furniture contract.
     *
     * @param songId Song id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IMusicData getMusicData(int songId);

    /**
     * Returns the music data by name associated with this furniture contract.
     *
     * @param name Name value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IMusicData getMusicDataByName(String name);

    /**
     * Returns the item id to virtual ids associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Long, Integer> getItemIdToVirtualIds();

    /**
     * Returns the by sprite id associated with this furniture contract.
     *
     * @param spriteId Sprite id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    FurnitureDefinition getBySpriteId(int spriteId);

    /**
     * Returns the logger associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Logger getLogger();

    /**
     * Returns the item definitions associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, FurnitureDefinition> getItemDefinitions();

    /**
     * Returns the crackable rewards associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, CrackableReward> getCrackableRewards();

    /**
     * Returns the saddle id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Integer getSaddleId();
}
