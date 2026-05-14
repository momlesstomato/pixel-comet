package com.cometproject.server.game.rooms.objects.items.types.wall;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.types.Room;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


/**
 * Describes post it wall item behavior for the room subsystem.
 */
public class PostItWallItem extends RoomItemWall {
    private String colour;
    private String message;

    /**
     * Creates a post it wall item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public PostItWallItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        if (this.isValidData(roomItemData.getData()))
            this.setExtraData(roomItemData.getData());
        else
            this.setExtraData("FFFF33 ");
    }

    /**
     * Returns the state for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getState() {
        return colour;
    }

    /**
     * Updates the extra data for this room contract.
     *
     * @param extraData Extra data supplied by the caller.
     */
    public void setExtraData(String extraData) {
        String[] data = extraData.split(" ");
        String colour = data[0];

        if (!this.isValidColour(colour)) {
            return;
        }

        this.getItemData().setData(extraData);

        this.colour = colour;
        this.message = StringUtils.join(Arrays.copyOfRange(data, 1, data.length), " ");
    }

    private boolean isValidColour(String colour) {
        switch (colour) {
            default:
                return false;

            case "FFFF33":
            case "FF9CFF":
            case "9CCEFF":
            case "9CFF9C":
                return true;
        }
    }

    private boolean isValidData(String data) {
        return data.contains(" ");
    }

    /**
     * Returns the colour for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getColour() {
        return colour;
    }

    /**
     * Returns the message for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }
}
