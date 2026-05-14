package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.furniture.types.IGiftData;
import com.cometproject.api.game.furniture.types.LimitedEditionItem;
import com.cometproject.api.game.furniture.types.SongItem;
import com.cometproject.api.game.players.data.IPlayerComponent;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines the player inventory contract for the player subsystem.
 */
public interface PlayerInventory extends IPlayerComponent {
    /**
     * Loads items data for this player contract.
     *
     * @param id Id value supplied by the caller.
     */
    void loadItems(int id);

    /**
     * Loads badges data for this player contract.
     */
    void loadBadges();

    /**
     * Loads effects data for this player contract.
     */
    void loadEffects();

    /**
     * Executes the send operation for this player contract.
     */
    void send();

    /**
     * Adds badge data to this player contract.
     *
     * @param code Code value supplied by the caller.
     * @param insert Insert value supplied by the caller.
     */
    void addBadge(String code, boolean insert);

    /**
     * Adds badge data to this player contract.
     *
     * @param code Code value supplied by the caller.
     * @param insert Insert value supplied by the caller.
     * @param sendAlert Send alert value supplied by the caller.
     */
    void addBadge(String code, boolean insert, boolean sendAlert);

    /**
     * Indicates whether this player contract has badge.
     *
     * @param code Code value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasBadge(String code);

    /**
     * Removes badge data from this player contract.
     *
     * @param code Code value supplied by the caller.
     * @param delete Delete value supplied by the caller.
     */
    void removeBadge(String code, boolean delete);

    /**
     * Removes badge data from this player contract.
     *
     * @param code Code value supplied by the caller.
     * @param delete Delete value supplied by the caller.
     * @param sendAlert Send alert value supplied by the caller.
     * @param sendUpdate Send update value supplied by the caller.
     */
    void removeBadge(String code, boolean delete, boolean sendAlert, boolean sendUpdate);

    /**
     * Executes the achievement badge operation for this player contract.
     *
     * @param achievement Achievement value supplied by the caller.
     * @param level Level value supplied by the caller.
     */
    void achievementBadge(String achievement, int level);

    /**
     * Executes the reset badge slots operation for this player contract.
     */
    void resetBadgeSlots();

    /**
     * Executes the equipped badges operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    String[] equippedBadges();

    /**
     * Executes the add operation for this player contract.
     *
     * @param id Id value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     * @param extraData Extra data value supplied by the caller.
     * @param giftData Gift data value supplied by the caller.
     * @param limitedEditionItem Limited edition item value supplied by the caller.
     * @return Result produced by the mutation.
     */
    PlayerItem add(long id, int itemId, String extraData, IGiftData giftData, LimitedEditionItem limitedEditionItem);

    /**
     * Returns the songs associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<SongItem> getSongs();

    /**
     * Executes the add operation for this player contract.
     *
     * @param id Id value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     * @param extraData Extra data value supplied by the caller.
     * @param limitedEditionItem Limited edition item value supplied by the caller.
     */
    void add(long id, int itemId, String extraData, LimitedEditionItem limitedEditionItem);

    /**
     * Adds item data to this player contract.
     *
     * @param item Item value supplied by the caller.
     */
    void addItem(PlayerItem item);

    /**
     * Removes item data from this player contract.
     *
     * @param item Item value supplied by the caller.
     */
    void removeItem(PlayerItem item);

    /**
     * Removes item data from this player contract.
     *
     * @param itemId Item id value supplied by the caller.
     */
    void removeItem(long itemId);

    /**
     * Indicates whether this player contract has item.
     *
     * @param id Id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasItem(long id);

    /**
     * Indicates whether this player contract has base item.
     *
     * @param id Id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasBaseItem(long id);

    /**
     * Returns the item associated with this player contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerItem getItem(long id);

    /**
     * Returns the total size associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getTotalSize();

    /**
     * Returns the inventory items associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Long, PlayerItem> getInventoryItems();

    /**
     * Returns the badges associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<String, Integer> getBadges();

    /**
     * Indicates whether this player contract has effect.
     *
     * @param effectId Effect id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasEffect(int effectId);

    /**
     * Returns the effects associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<Integer> getEffects();

    /**
     * Returns the equipped effect associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getEquippedEffect();

    /**
     * Updates the equipped effect value for this player contract.
     *
     * @param effectId Effect id value supplied by the caller.
     */
    void setEquippedEffect(int effectId);

    /**
     * Executes the items loaded operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean itemsLoaded();

    /**
     * Indicates whether viewing inventory is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isViewingInventory();

    /**
     * Executes the viewing inventory user id operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    int viewingInventoryUserId();
}
