package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.components.types.RoomBan;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the room banned list message for the Pixel Protocol client.
 */
public class RoomBannedListMessageComposer extends MessageComposer {
    private final int roomId;
    private final Map<Integer, RoomBan> bans;

    /**
     * Creates a room banned list message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param bans Bans supplied by the caller.
     */
    public RoomBannedListMessageComposer(int roomId, Map<Integer, RoomBan> bans) {
        this.roomId = roomId;
        this.bans = bans;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GetRoomBannedUsersMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeInt(bans.size());

        for (RoomBan ban : bans.values()) {
            msg.writeInt(ban.getPlayerId());
            msg.writeString(ban.getPlayerName());
        }
    }
}
