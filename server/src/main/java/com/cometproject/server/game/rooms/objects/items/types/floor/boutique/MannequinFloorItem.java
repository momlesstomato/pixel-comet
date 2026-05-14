package com.cometproject.server.game.rooms.objects.items.types.floor.boutique;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes mannequin floor item behavior for the room subsystem.
 */
public class MannequinFloorItem extends RoomItemFloor {
    private String name = "New Mannequin";
    private String figure = "ch-210-62.lg-270-62";
    private String gender = "m";

    /**
     * Creates a mannequin floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public MannequinFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        if (!this.getItemData().getData().isEmpty()) {
            String[] splitData = this.getItemData().getData().split(";#;");
            if (splitData.length != 3) return;

            this.name = splitData[0];
            this.figure = splitData[1];
            this.gender = splitData[2];

            String[] figureParts = this.figure.split("\\.");
            StringBuilder finalFigure = new StringBuilder();

            for (String figurePart : figureParts) {
                if (!figurePart.contains("hr") && !figurePart.contains("hd") && !figurePart.contains("he") && !figurePart.contains("ha")) {
                    finalFigure.append(figurePart).append(".");
                }
            }

            this.figure = finalFigure.substring(0, finalFigure.length() - 1);
        }
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void composeItemData(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(3);

        msg.writeString("GENDER");
        msg.writeString(this.getGender());
        msg.writeString("FIGURE");
        msg.writeString(this.getFigure());
        msg.writeString("OUTFIT_NAME");
        msg.writeString(this.getName());
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
        if (isWiredTrigger || !(entity instanceof PlayerEntity))
            return isWiredTrigger;

        PlayerEntity playerEntity = (PlayerEntity) entity;

        if (this.name == null || this.gender == null || this.figure == null) return false;

        if (!this.gender.equals(playerEntity.getGender())) return false;

        String newFigure = "";

        for (String playerFigurePart : playerEntity.getFigure().split("\\.")) {
            if (!playerFigurePart.startsWith("ch") && !playerFigurePart.startsWith("lg"))
                newFigure += playerFigurePart + ".";
        }

        String newFigureParts = "";

        switch (playerEntity.getGender().toUpperCase()) {
            case "M":
                if (this.figure.equals("")) return false;
                newFigureParts = this.figure;
                break;

            case "F":
                if (this.figure.equals("")) return false;
                newFigureParts = this.figure;
                break;
        }

        for (String newFigurePart : newFigureParts.split("\\.")) {
            if (newFigurePart.startsWith("hd"))
                newFigureParts = newFigureParts.replace(newFigurePart, "");
        }

        if (newFigureParts.equals("")) return false;

        final String figure = newFigure + newFigureParts;

        if (figure.length() > 512)
            return false;

        playerEntity.getPlayer().getData().setFigure(figure);
        playerEntity.getPlayer().getData().setGender(this.gender);

        playerEntity.getPlayer().getData().save();
        playerEntity.getPlayer().poof();

        return true;
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDataObject() {
        return this.name + ";#;" + this.figure + ";#;" + this.gender;
    }

    /**
     * Returns the name for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name for this room contract.
     *
     * @param name Name supplied by the caller.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the figure for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getFigure() {
        return figure;
    }

    /**
     * Updates the figure for this room contract.
     *
     * @param figure Figure supplied by the caller.
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Returns the gender for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Updates the gender for this room contract.
     *
     * @param gender Gender supplied by the caller.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}

