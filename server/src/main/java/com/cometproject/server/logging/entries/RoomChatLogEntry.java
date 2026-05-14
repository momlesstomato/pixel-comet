package com.cometproject.server.logging.entries;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.AbstractLogEntry;
import com.cometproject.server.logging.LogEntryType;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.server.utilities.TimeSpan;


/**
 * Describes room chat log entry behavior for the logging subsystem.
 */
public class RoomChatLogEntry extends AbstractLogEntry {
    private int roomId;
    private int userId;
    private String message;
    private int timestamp;

    /**
     * Creates a room chat log entry instance for the logging subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param userId User id supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public RoomChatLogEntry(int roomId, int userId, String message) {
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timestamp = (int) Comet.getTime();
    }

    /**
     * Creates a room chat log entry instance for the logging subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param userId User id supplied by the caller.
     * @param message Message supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    public RoomChatLogEntry(int roomId, int userId, String message, int timestamp) {
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeString(TimeSpan.millisecondsToDate((int) (Comet.getTime() - getTimestamp()) * 1000));

        msg.writeInt(this.getPlayerId());
        msg.writeString(PlayerDao.getUsernameByPlayerId(this.getPlayerId()));
        msg.writeString(this.getString());
        msg.writeBoolean(false);
    }

    /**
     * Returns the type for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public LogEntryType getType() {
        return LogEntryType.ROOM_CHATLOG;
    }

    /**
     * Returns the string for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getString() {
        return this.message;
    }

    /**
     * Returns the timestamp for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getTimestamp() {
        return this.timestamp;
    }

    /**
     * Returns the room id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Returns the player id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getPlayerId() {
        return this.userId;
    }
}
