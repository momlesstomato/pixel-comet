package com.cometproject.server.storage.cache.objects;

import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.game.pets.IPetData;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.server.storage.cache.CachableObject;
import com.cometproject.server.storage.cache.objects.items.FloorItemDataObject;
import com.cometproject.server.storage.cache.objects.items.WallItemDataObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * Describes room data object behavior for the storage subsystem.
 */
public class RoomDataObject extends CachableObject {
    private final long id;
    private final IRoomData data;
    private final List<Integer> rights;

    private final List<FloorItemDataObject> floorItems;
    private final List<WallItemDataObject> wallItems;

    private final List<IPetData> pets;
    private final List<IBotData> bots;

    /**
     * Creates a room data object instance for the storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param data Data supplied by the caller.
     * @param rights Rights supplied by the caller.
     * @param floorItems Floor items supplied by the caller.
     * @param wallItems Wall items supplied by the caller.
     * @param pets Pets supplied by the caller.
     * @param bots Bots supplied by the caller.
     */
    public RoomDataObject(long id, IRoomData data, List<Integer> rights, List<FloorItemDataObject> floorItems, List<WallItemDataObject> wallItems, List<IPetData> pets, List<IBotData> bots) {
        this.id = id;
        this.data = data;
        this.rights = rights;
        this.floorItems = floorItems;
        this.wallItems = wallItems;
        this.pets = pets;
        this.bots = bots;
    }

    /**
     * Returns the id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the data for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomData getData() {
        return data;
    }

    /**
     * Returns the rights for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Integer> getRights() {
        return rights;
    }

    /**
     * Returns the floor items for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<FloorItemDataObject> getFloorItems() {
        return floorItems;
    }

    /**
     * Returns the wall items for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<WallItemDataObject> getWallItems() {
        return wallItems;
    }

    /**
     * Returns the pets for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IPetData> getPets() {
        return pets;
    }

    /**
     * Returns the bots for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IBotData> getBots() {
        return bots;
    }

    /**
     * Executes to JSON for this storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonObject toJson() {
        final JsonObject coreObject = new JsonObject();
        final JsonObject roomDataObject = new JsonObject();
        final JsonArray rightsArray = new JsonArray();

        final JsonArray floorItems = new JsonArray();
        final JsonArray wallItems = new JsonArray();
        final JsonArray pets = new JsonArray();
        final JsonArray bots = new JsonArray();

        coreObject.addProperty("id", id);

        for (int rightsId : rights) {
            rightsArray.add(rightsId);
        }

        roomDataObject.addProperty("id", data.getId());
        roomDataObject.addProperty("type", data.getType().toString());
        roomDataObject.addProperty("name", data.getName());
        roomDataObject.addProperty("description", data.getDescription());
        roomDataObject.addProperty("ownerId", data.getOwnerId());
        roomDataObject.addProperty("owner", data.getOwner());
        roomDataObject.addProperty("category", data.getCategoryId());
        roomDataObject.addProperty("maxUsers", data.getMaxUsers());
        roomDataObject.addProperty("access", data.getAccess().toString());
        roomDataObject.addProperty("password", data.getPassword());
        roomDataObject.addProperty("tradeState", data.getTradeState().toString());
        roomDataObject.addProperty("score", data.getScore());

        final JsonArray tagsArray = new JsonArray();

        for (int i = 0; i < data.getTags().length; i++) {
            tagsArray.add(data.getTags()[i]);
        }

        roomDataObject.add("tags", tagsArray);

        final JsonObject decorationsObject = new JsonObject();

        for (Map.Entry<String, String> decoration : data.getDecorations().entrySet()) {
            decorationsObject.addProperty(decoration.getKey(), decoration.getValue());
        }

        roomDataObject.add("decorations", decorationsObject);
        roomDataObject.addProperty("model", data.getModel());
        roomDataObject.addProperty("hideWalls", data.getHideWalls());
        roomDataObject.addProperty("thicknessWall", data.getWallThickness());
        roomDataObject.addProperty("thicknessFloor", data.getFloorThickness());
        roomDataObject.addProperty("allowPets", data.isAllowPets());
        roomDataObject.addProperty("heightmap", data.getHeightmap());
        roomDataObject.addProperty("muteState", data.getMuteState().toString());
        roomDataObject.addProperty("kickState", data.getKickState().toString());
        roomDataObject.addProperty("banState", data.getBanState().toString());
        roomDataObject.addProperty("bubbleMode", data.getBubbleMode());
        roomDataObject.addProperty("bubbleType", data.getBubbleType());
        roomDataObject.addProperty("bubbleScroll", data.getBubbleScroll());
        roomDataObject.addProperty("chatDistance", data.getChatDistance());
        roomDataObject.addProperty("antiFloodSettings", data.getAntiFloodSettings());

        final JsonArray disabledCommands = new JsonArray();

        for (String disabledCommand : data.getDisabledCommands()) {
            disabledCommands.add(disabledCommand);
        }

        roomDataObject.add("disabledCommands", disabledCommands);
        roomDataObject.addProperty("groupId", data.getGroupId());
        roomDataObject.addProperty("requiredBadge", data.getRequiredBadge());

        for (FloorItemDataObject floorItemDataObject : this.floorItems) {
            floorItems.add(floorItemDataObject.toJsonObject());
        }

        for (WallItemDataObject wallItemDataObject : this.wallItems) {
            wallItems.add(wallItemDataObject.toJsonObject());
        }

        for (IPetData petData : this.pets) {
            pets.add(petData.toJsonObject());
        }

        for (IBotData botData : this.bots) {
            bots.add(botData.toJsonObject());
        }

        coreObject.add("data", roomDataObject);
        coreObject.add("floorItems", floorItems);
        coreObject.add("wallItems", wallItems);
        coreObject.add("pets", pets);

        return coreObject;
    }
}
