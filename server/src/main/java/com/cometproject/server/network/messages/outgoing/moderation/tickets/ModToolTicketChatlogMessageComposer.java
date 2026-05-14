package com.cometproject.server.network.messages.outgoing.moderation.tickets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.logging.entries.RoomChatLogEntry;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the mod tool ticket chatlog message for the Pixel Protocol client.
 */
public class ModToolTicketChatlogMessageComposer extends MessageComposer {

    private final static String ROOM_ID = "roomId";
    private final static String ROOM_NAME = "roomName";

    private final int roomId;
    private final String roomName;

    private final List<RoomChatLogEntry> roomChatLogEntries;
    private final HelpTicket helpTicket;

    /**
     * Creates a mod tool ticket chatlog message composer instance for the network message subsystem.
     *
     * @param helpTicket Help ticket supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param roomName Room name supplied by the caller.
     * @param chatLogs Chat logs supplied by the caller.
     */
    public ModToolTicketChatlogMessageComposer(HelpTicket helpTicket, final int roomId, final String roomName, final List<RoomChatLogEntry> chatLogs) {
        this.helpTicket = helpTicket;
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
        return Composers.ModeratorTicketChatlogMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.helpTicket.getId());
        msg.writeInt(this.helpTicket.getSubmitterId());
        msg.writeInt(this.helpTicket.getReportedId());
        msg.writeInt(this.helpTicket.getRoomId());

        msg.writeByte(1);
        msg.writeShort(2);

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
}
