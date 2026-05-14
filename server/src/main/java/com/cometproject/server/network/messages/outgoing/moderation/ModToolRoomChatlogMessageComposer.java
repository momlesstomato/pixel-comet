package com.cometproject.server.network.messages.outgoing.moderation;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.logging.entries.RoomChatLogEntry;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the mod tool room chatlog message for the Pixel Protocol client.
 */
public class ModToolRoomChatlogMessageComposer extends MessageComposer {

    private final static String ROOM_ID = "roomId";
    private final static String ROOM_NAME = "roomName";

    private final int roomId;
    private final String roomName;

    private final List<RoomChatLogEntry> roomChatLogEntries;

    /**
     * Creates a mod tool room chatlog message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param roomName Room name supplied by the caller.
     * @param chatLogs Chat logs supplied by the caller.
     */
    public ModToolRoomChatlogMessageComposer(final int roomId, final String roomName, final List<RoomChatLogEntry> chatLogs) {
        this.roomId = roomId;
        this.roomName = roomName;

        this.roomChatLogEntries = chatLogs;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorRoomChatlogMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeByte(1);
        msg.writeShort(3);
        msg.writeString("");

        msg.writeByte(0);
        /**
         * Executes write boolean for this network message contract.
         *
         * @param false False supplied by the caller.
         * @return Result produced by the operation.
         */
        msg.writeBoolean(false); // Is public

        msg.writeString(ROOM_ID);
        msg.writeByte(1);
        msg.writeInt(roomId);

        msg.writeString(ROOM_NAME);
        msg.writeByte(2);
        msg.writeString(roomName);

        msg.writeShort(roomChatLogEntries.size());

        for (RoomChatLogEntry entry : roomChatLogEntries) {
            entry.compose(msg);
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.roomChatLogEntries.clear();
    }
}
