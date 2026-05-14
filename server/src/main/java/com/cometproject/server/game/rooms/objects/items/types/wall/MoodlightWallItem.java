package com.cometproject.server.game.rooms.objects.items.types.wall;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.objects.items.data.MoodlightData;
import com.cometproject.server.game.rooms.objects.items.data.MoodlightPresetData;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.storage.queries.items.MoodlightDao;


/**
 * Describes moodlight wall item behavior for the room subsystem.
 */
public class MoodlightWallItem extends RoomItemWall {
    private MoodlightData moodlightData = null;

    /**
     * Creates a moodlight wall item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public MoodlightWallItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);
    }

    /**
     * Indicates whether valid colour applies to this room contract.
     *
     * @param colour Colour supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean isValidColour(String colour) {
        switch (colour) {
            case "#000000":
            case "#0053F7":
            case "#EA4532":
            case "#82F349":
            case "#74F5F5":
            case "#E759DE":
            case "#F2F851":
                return true;

            default:
                return false;
        }
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
        return true;
    }

    /**
     * Handles the load callback for this room contract.
     */
    @Override
    public void onLoad() {
        this.onPlaced();
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        if (this.getRoom().getItems().setMoodlight(this.getId())) {
            this.moodlightData = MoodlightDao.getMoodlightData(this.getId());
        }
    }

    /**
     * Handles the unload callback for this room contract.
     */
    @Override
    public void onUnload() {
        this.onPickup();
    }

    /**
     * Handles the pickup callback for this room contract.
     */
    @Override
    public void onPickup() {
        if (this.getRoom().getItems().isMoodlightMatches(this)) {
            this.getRoom().getItems().removeMoodlight();
        }
    }

    /**
     * Executes generate extra data for this room contract.
     *
     * @return Result produced by the operation.
     */
    public String generateExtraData() {
        MoodlightPresetData preset = (this.getMoodlightData().getPresets().size() >= this.getMoodlightData().getActivePreset())
                ? this.getMoodlightData().getPresets().get(this.getMoodlightData().getActivePreset() - 1)
                : new MoodlightPresetData(true, "#000000", 255);

        StringBuilder sb = new StringBuilder();
        if (this.getMoodlightData().isEnabled()) {
            sb.append(2);
        } else {
            sb.append(1);
        }

        sb.append(",");
        sb.append(this.getMoodlightData().getActivePreset());
        sb.append(",");

        if (preset.backgroundOnly) {
            sb.append(2);
        } else {
            sb.append(1);
        }

        sb.append(",");
        sb.append(preset.colour);
        sb.append(",");
        sb.append(preset.intensity);
        return sb.toString();
    }

    /**
     * Returns the moodlight data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public MoodlightData getMoodlightData() {
        return this.moodlightData;
    }
}