package com.cometproject.server.network.messages.outgoing.moderation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.logging.entries.RoomVisitLogEntry;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the mod tool room visits message for the Pixel Protocol client.
 */
public class ModToolRoomVisitsMessageComposer extends MessageComposer {
    private final int playerId;
    private final String playerUsername;
    private final List<RoomVisitLogEntry> roomVisitLogEntries;

    /**
     * Creates a mod tool room visits message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param playerUsername Player username supplied by the caller.
     * @param roomVisits Room visits supplied by the caller.
     */
    public ModToolRoomVisitsMessageComposer(final int playerId, final String playerUsername, final List<RoomVisitLogEntry> roomVisits) {
        this.playerId = playerId;
        this.playerUsername = playerUsername;
        this.roomVisitLogEntries = roomVisits;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorUserRoomVisitsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeString(playerUsername);

        msg.writeInt(roomVisitLogEntries.size());

        for (RoomVisitLogEntry roomVisit : roomVisitLogEntries) {
            IRoomData roomData = GameContext.getCurrent().getRoomService().getRoomData(roomVisit.getRoomId());
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(roomVisit.getEntryTime()), ZoneId.systemDefault());

            msg.writeInt(roomData == null ? 0 : roomData.getId());
            msg.writeString(roomData == null ? "Unknown Room" : roomData.getName());

            msg.writeInt(dateTime.getHour());
            msg.writeInt(dateTime.getMinute());
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.roomVisitLogEntries.clear();
    }
}
