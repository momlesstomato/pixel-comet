package com.cometproject.server.game.rooms.objects.entities.types.data;

import com.cometproject.api.game.bots.BotMode;
import com.cometproject.api.game.bots.BotType;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.bots.BotData;


/**
 * Carries player bot data data for the room subsystem.
 */
public class PlayerBotData extends BotData {
    private Position position;

    /**
     * Creates a player bot data instance for the room subsystem.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param motto Motto supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param messages Messages supplied by the caller.
     * @param automaticChat Automatic chat supplied by the caller.
     * @param chatDelay Chat delay supplied by the caller.
     * @param botType Bot type supplied by the caller.
     * @param mode Mode supplied by the caller.
     * @param data Data supplied by the caller.
     */
    public PlayerBotData(int id, String username, String motto, String figure, String gender, String ownerName, int ownerId, String messages, boolean automaticChat, int chatDelay, BotType botType, BotMode mode, String data) {
        super(id, username, motto, figure, gender, ownerName, ownerId, messages, automaticChat, chatDelay, botType, mode, data);
    }

    /**
     * Returns the position for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the position for this room contract.
     *
     * @param position Position supplied by the caller.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}
