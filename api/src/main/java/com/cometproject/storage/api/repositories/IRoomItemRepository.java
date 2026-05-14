package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.catalog.types.purchase.CatalogPurchase;
import com.cometproject.api.game.rooms.objects.IRoomItemData;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Defines the i room item repository contract for the storage subsystem.
 */
public interface IRoomItemRepository {

    /**
     * Returns the items by room id associated with this storage contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @param itemConsumer Item consumer value supplied by the caller.
     */
    void getItemsByRoomId(final int roomId, Consumer<List<RoomItemData>> itemConsumer);

    /**
     * Removes item from room data from this storage contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     * @param finalState Final state value supplied by the caller.
     */
    void removeItemFromRoom(final long itemId, final int playerId, final String finalState);

    /**
     * Deletes item data for this storage contract.
     *
     * @param itemId Item id value supplied by the caller.
     */
    void deleteItem(long itemId);

    /**
     * Persists data data for this storage contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @param data Data value supplied by the caller.
     */
    void saveData(long itemId, String data);

    /**
     * Returns the room id by item id associated with this storage contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @param idConsumer Id consumer value supplied by the caller.
     */
    void getRoomIdByItemId(long itemId, Consumer<Integer> idConsumer);

    /**
     * Persists item position data for this storage contract.
     *
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     * @param height Height value supplied by the caller.
     * @param rotation Rotation value supplied by the caller.
     * @param id Id value supplied by the caller.
     */
    void saveItemPosition(int x, int y, double height, int rotation, long id);

    /**
     * Executes the place floor item operation for this storage contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     * @param height Height value supplied by the caller.
     * @param rot Rot value supplied by the caller.
     * @param data Data value supplied by the caller.
     * @param baseItem Base item value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     */
    void placeFloorItem(long roomId, int x, int y, double height, int rot, String data, int baseItem, long itemId);

    /**
     * Executes the place wall item operation for this storage contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @param wallPosition Wall position value supplied by the caller.
     * @param data Data value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     */
    void placeWallItem(int roomId, String wallPosition, String data, long itemId);

    /**
     * Executes the place bundle operation for this storage contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @param bundle Bundle value supplied by the caller.
     */
    void placeBundle(int roomId, final Set<IRoomItemData> bundle);

    /**
     * Persists item data for this storage contract.
     *
     * @param itemData Item data value supplied by the caller.
     */
    void saveItem(IRoomItemData itemData);

    /**
     * Creates item data for this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     * @param data Data value supplied by the caller.
     * @param idConsumer Id consumer value supplied by the caller.
     */
    void createItem(int playerId, int itemId, String data, Consumer<Long> idConsumer);

    /**
     * Executes the purchase items operation for this storage contract.
     *
     * @param purchases Purchases value supplied by the caller.
     * @param idConsumer Id consumer value supplied by the caller.
     * @param viewingUser Viewing user value supplied by the caller.
     */
    void purchaseItems(List<CatalogPurchase> purchases, Consumer<List<Long>> idConsumer, int viewingUser);

    /**
     * Persists item batch data for this storage contract.
     *
     * @param data Data value supplied by the caller.
     */
    void saveItemBatch(final Set<IRoomItemData> data);

    /**
     * Updates the base item value for this storage contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @param baseId Base id value supplied by the caller.
     */
    void setBaseItem(long itemId, int baseId);

    /**
     * Persists reward data for this storage contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     * @param rewardData Reward data value supplied by the caller.
     */
    void saveReward(long itemId, int playerId, String rewardData);

    /**
     * Returns the given rewards associated with this storage contract.
     *
     * @param id Id value supplied by the caller.
     * @param rewardsConsumer Rewards consumer value supplied by the caller.
     */
    void getGivenRewards(long id, Consumer<Map<Integer, Set<String>>> rewardsConsumer);
}
