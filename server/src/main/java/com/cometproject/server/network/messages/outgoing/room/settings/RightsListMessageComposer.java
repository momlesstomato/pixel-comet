package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.storage.queries.player.PlayerDao;

import java.util.List;


/**
 * Serializes the rights list message for the Pixel Protocol client.
 */
public class RightsListMessageComposer extends MessageComposer {
    private final int roomId;
    private final List<Integer> playersWithRights;

    /**
     * Creates a rights list message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param playersWithRights Players with rights supplied by the caller.
     */
    public RightsListMessageComposer(int roomId, List<Integer> playersWithRights) {
        this.roomId = roomId;
        this.playersWithRights = playersWithRights;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomRightsListMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeInt(playersWithRights.size());

        for (Integer id : playersWithRights) {
            msg.writeInt(id);

            String username = PlayerDao.getUsernameByPlayerId(id);
            msg.writeString(username != null ? username : "Placeholder");
        }
    }
}
