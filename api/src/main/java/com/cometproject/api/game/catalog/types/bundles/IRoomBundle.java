package com.cometproject.api.game.catalog.types.bundles;

import com.cometproject.api.game.rooms.models.CustomFloorMapData;

import java.util.List;

/**
 * Defines the i room bundle contract for the catalog subsystem.
 */
public interface IRoomBundle {
    /**
     * Returns the id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Updates the id value for this catalog contract.
     *
     * @param id Id value supplied by the caller.
     */
    void setId(int id);

    /**
     * Returns the alias associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getAlias();

    /**
     * Updates the alias value for this catalog contract.
     *
     * @param alias Alias value supplied by the caller.
     */
    void setAlias(String alias);

    /**
     * Returns the room model data associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    CustomFloorMapData getRoomModelData();

    /**
     * Updates the room model data value for this catalog contract.
     *
     * @param roomModelData Room model data value supplied by the caller.
     */
    void setRoomModelData(CustomFloorMapData roomModelData);

    /**
     * Returns the room bundle data associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<RoomBundleItem> getRoomBundleData();

    /**
     * Updates the room bundle data value for this catalog contract.
     *
     * @param roomBundleData Room bundle data value supplied by the caller.
     */
    void setRoomBundleData(List<RoomBundleItem> roomBundleData);

    /**
     * Returns the room id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRoomId();

    /**
     * Updates the room id value for this catalog contract.
     *
     * @param roomId Room id value supplied by the caller.
     */
    void setRoomId(int roomId);

    /**
     * Returns the cost credits associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostCredits();

    /**
     * Updates the cost credits value for this catalog contract.
     *
     * @param costCredits Cost credits value supplied by the caller.
     */
    void setCostCredits(int costCredits);

    /**
     * Returns the cost seasonal associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostSeasonal();

    /**
     * Updates the cost seasonal value for this catalog contract.
     *
     * @param costSeasonal Cost seasonal value supplied by the caller.
     */
    void setCostSeasonal(int costSeasonal);

    /**
     * Returns the cost VIP associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostVip();

    /**
     * Updates the cost VIP value for this catalog contract.
     *
     * @param costVip Cost vip value supplied by the caller.
     */
    void setCostVip(int costVip);

    /**
     * Returns the cost activity points associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCostActivityPoints();

    /**
     * Updates the cost activity points value for this catalog contract.
     *
     * @param costActivityPoints Cost activity points value supplied by the caller.
     */
    void setCostActivityPoints(int costActivityPoints);

    /**
     * Returns the config associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomBundleConfig getConfig();

    /**
     * Updates the config value for this catalog contract.
     *
     * @param config Config value supplied by the caller.
     */
    void setConfig(RoomBundleConfig config);
}
