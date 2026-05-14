package com.cometproject.server.game.rooms.bundles.types;

import com.cometproject.api.game.catalog.types.bundles.IRoomBundle;
import com.cometproject.api.game.catalog.types.bundles.RoomBundleConfig;
import com.cometproject.api.game.catalog.types.bundles.RoomBundleItem;
import com.cometproject.api.game.rooms.models.CustomFloorMapData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.objects.items.types.floor.SoundMachineFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.TeleporterFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.types.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes room bundle behavior for the room subsystem.
 */
public class RoomBundle implements IRoomBundle {
    private int id;
    private int roomId;
    private String alias;
    private CustomFloorMapData roomModelData;
    private List<RoomBundleItem> roomBundleData;
    private RoomBundleConfig config;

    private int costCredits;
    private int costSeasonal;
    private int costVip;
    private int costActivityPoints;

    /**
     * Creates a room bundle instance for the room subsystem.
     *
     * @param id Id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param alias Alias supplied by the caller.
     * @param roomModel Room model supplied by the caller.
     * @param bundleData Bundle data supplied by the caller.
     * @param costCredits Cost credits supplied by the caller.
     * @param costSeasonal Cost seasonal supplied by the caller.
     * @param costVip Cost vip supplied by the caller.
     * @param costActivityPoints Cost activity points supplied by the caller.
     * @param config Config supplied by the caller.
     */
    public RoomBundle(int id, int roomId, String alias, CustomFloorMapData roomModel, List<RoomBundleItem> bundleData, int costCredits, int costSeasonal, int costVip, int costActivityPoints, RoomBundleConfig config) {
        this.id = id;
        this.roomId = roomId;
        this.alias = alias;
        this.roomModelData = roomModel;
        this.roomBundleData = bundleData;
        this.costCredits = costCredits;
        this.costSeasonal = costSeasonal;
        this.costVip = costVip;
        this.costActivityPoints = costActivityPoints;
        this.config = config;
    }

    /**
     * Executes create for this room contract.
     *
     * @param room Room participating in the operation.
     * @param alias Alias supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static RoomBundle create(Room room, String alias) {
        CustomFloorMapData modelData = new CustomFloorMapData(
                room.getModel().getDoorX(), room.getModel().getDoorY(),
                room.getModel().getDoorRotation(), room.getModel().getMap(), room.getModel().getRoomModelData().getWallHeight());

        List<RoomBundleItem> bundleItems = new ArrayList<>();

        for (RoomItemFloor floorItem : room.getItems().getFloorItems().values()) {
            if (floorItem instanceof SoundMachineFloorItem || floorItem instanceof TeleporterFloorItem || floorItem instanceof WiredFloorItem) {
                continue;
            }

            bundleItems.add(new RoomBundleItem(floorItem.getItemData().getItemId(),
                    floorItem.getPosition().getX(), floorItem.getPosition().getY(),
                    floorItem.getPosition().getZ(), floorItem.getRotation(), null,
                    floorItem.getDataObject()
            ));
        }

        for (RoomItemWall wallItem : room.getItems().getWallItems().values()) {
            bundleItems.add(new RoomBundleItem(wallItem.getItemData().getItemId(),
                    -1, -1, -1, -1, wallItem.getWallPosition(),
                    wallItem.getItemData().getData()
            ));
        }

        return new RoomBundle(-1, room.getId(), alias, modelData, bundleItems, 20, 0, 0, 0, new RoomBundleConfig("%username%'s new room", room.getData().getDecorationString(), room.getData().getWallThickness(), room.getData().getFloorThickness(), room.getData().getHideWalls()));
    }

    /**
     * Returns the id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Updates the id for this room contract.
     *
     * @param id Id supplied by the caller.
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the alias for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * Updates the alias for this room contract.
     *
     * @param alias Alias supplied by the caller.
     */
    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Returns the room model data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public CustomFloorMapData getRoomModelData() {
        return roomModelData;
    }

    /**
     * Updates the room model data for this room contract.
     *
     * @param roomModelData Room model data supplied by the caller.
     */
    @Override
    public void setRoomModelData(CustomFloorMapData roomModelData) {
        this.roomModelData = roomModelData;
    }

    /**
     * Returns the room bundle data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<RoomBundleItem> getRoomBundleData() {
        return roomBundleData;
    }

    /**
     * Updates the room bundle data for this room contract.
     *
     * @param roomBundleData Room bundle data supplied by the caller.
     */
    @Override
    public void setRoomBundleData(List<RoomBundleItem> roomBundleData) {
        this.roomBundleData = roomBundleData;
    }

    /**
     * Returns the room id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRoomId() {
        return roomId;
    }

    /**
     * Updates the room id for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    @Override
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the cost credits for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCostCredits() {
        return costCredits;
    }

    /**
     * Updates the cost credits for this room contract.
     *
     * @param costCredits Cost credits supplied by the caller.
     */
    @Override
    public void setCostCredits(int costCredits) {
        this.costCredits = costCredits;
    }

    /**
     * Returns the cost seasonal for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCostSeasonal() {
        return costSeasonal;
    }

    /**
     * Updates the cost seasonal for this room contract.
     *
     * @param costSeasonal Cost seasonal supplied by the caller.
     */
    @Override
    public void setCostSeasonal(int costSeasonal) {
        this.costSeasonal = costSeasonal;
    }

    /**
     * Returns the cost VIP for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCostVip() {
        return costVip;
    }

    /**
     * Updates the cost VIP for this room contract.
     *
     * @param costVip Cost vip supplied by the caller.
     */
    @Override
    public void setCostVip(int costVip) {
        this.costVip = costVip;
    }

    /**
     * Returns the cost activity points for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCostActivityPoints() {
        return costActivityPoints;
    }

    /**
     * Updates the cost activity points for this room contract.
     *
     * @param costActivityPoints Cost activity points supplied by the caller.
     */
    @Override
    public void setCostActivityPoints(int costActivityPoints) {
        this.costActivityPoints = costActivityPoints;
    }

    /**
     * Returns the config for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomBundleConfig getConfig() {
        return config;
    }

    /**
     * Updates the config for this room contract.
     *
     * @param config Config supplied by the caller.
     */
    @Override
    public void setConfig(RoomBundleConfig config) {
        this.config = config;
    }
}
