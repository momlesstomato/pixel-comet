package com.cometproject.server.game.rooms.objects.items.types.floor.wired.highscore;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.data.ScoreboardItemData;
import com.cometproject.server.game.rooms.types.Room;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Describes highscore classic floor item behavior for the room subsystem.
 */
public class HighscoreClassicFloorItem extends RoomItemFloor {

    private final ScoreboardItemData itemData;
    private boolean state;

    /**
     * Creates a highscore classic floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public HighscoreClassicFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        final String data = roomItemData.getData();

        if (roomItemData.getData().startsWith("1{") || roomItemData.getData().startsWith("0{")) {
            this.state = data.startsWith("1");
            this.itemData = JsonUtil.getInstance().fromJson(data.substring(1), ScoreboardItemData.class);
        } else {
            this.state = false;
            this.itemData = new ScoreboardItemData(1, 0, Lists.newArrayList(), new LinkedHashMap<>());
        }
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        msg.writeInt(0);
        this.composeHighscoreData(msg);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (!isWiredTrigger) {
            if (!(entity instanceof PlayerEntity)) {
                return false;
            }

            PlayerEntity pEntity = (PlayerEntity) entity;

            if (!pEntity.getRoom().getRights().hasRights(pEntity.getPlayerId())
                    && !pEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                return false;
            }
        }

        this.state = !this.state;

        this.sendUpdate();
        this.saveData();
        return true;
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDataObject() {
        return (this.state ? "1" : "0") + JsonUtil.getInstance().toJson(this.itemData);
    }

    /**
     * Adds entry to this room contract.
     *
     * @param users Users supplied by the caller.
     * @param score Score supplied by the caller.
     */
    public void addEntry(List<String> users, int score) {
        this.itemData.addEntry(users, score);
        this.sendUpdate();

        this.saveData();
    }

    /**
     * Adds point to this room contract.
     *
     * @param identifier Identifier supplied by the caller.
     * @param score Score supplied by the caller.
     */
    public void addPoint(String identifier, int score) {
        this.itemData.addPoint(identifier, score);

        this.sendUpdate();
        this.saveData();
    }

    /**
     * Returns the score data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public ScoreboardItemData getScoreData() {
        return itemData;
    }

    /**
     * Executes reset scoreboard for this room contract.
     */
    public void resetScoreboard() {
        this.itemData.getPoints().clear();
        this.itemData.getEntries().clear();
        this.sendUpdate();
        this.save();
    }

    /**
     * Executes compose highscore data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void composeHighscoreData(IComposer msg) {
        msg.writeInt(6);

        msg.writeString(this.state ? "1" : "0");
        msg.writeInt(this.getScoreData().getScoreType());
        msg.writeInt(this.getScoreData().getClearType());

        msg.writeInt(Math.min(this.getScoreData().getPoints().size(), 50));

        int x = 0;

        List<Map.Entry<String, Integer>> list = new ArrayList<>(this.getScoreData().getPoints().entrySet());

        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        for (Map.Entry<String, Integer> entry : list) {
            x++;

            if (x > 50) break;

            msg.writeInt(entry.getValue());
            msg.writeInt(1);

            //for (String name : entry.getUsers()) {
            msg.writeString(entry.getKey());
            //}
        }
    }
}
