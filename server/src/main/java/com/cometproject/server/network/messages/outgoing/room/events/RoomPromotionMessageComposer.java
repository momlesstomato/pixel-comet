package com.cometproject.server.network.messages.outgoing.room.events;

import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.RoomPromotion;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room promotion message for the Pixel Protocol client.
 */
public class RoomPromotionMessageComposer extends MessageComposer {
    private final IRoomData roomData;
    private final RoomPromotion roomPromotion;

    /**
     * Creates a room promotion message composer instance for the network message subsystem.
     *
     * @param roomData Room data supplied by the caller.
     * @param roomPromotion Room promotion supplied by the caller.
     */
    public RoomPromotionMessageComposer(final IRoomData roomData, final RoomPromotion roomPromotion) {
        this.roomData = roomData;
        this.roomPromotion = roomPromotion;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomEventMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (roomData == null || roomPromotion == null) {
            msg.writeInt(-1);
            msg.writeInt(-1);
            msg.writeString("");
            msg.writeInt(0);
            msg.writeInt(0);
            msg.writeString("");
            msg.writeString("");
            msg.writeInt(0);
            msg.writeInt(0);
            msg.writeInt(0);
            return;
        }

        msg.writeInt(roomData.getId());
        msg.writeInt(roomData.getOwnerId());
        msg.writeString(roomData.getOwner());

        msg.writeInt(roomData.getOwnerId());
        msg.writeInt(1);

        msg.writeString(roomPromotion.getPromotionName());
        msg.writeString(roomPromotion.getPromotionDescription());

        msg.writeInt((int) roomPromotion.getTimestampStart());
        msg.writeInt((int) roomPromotion.getTimestampFinish());
        msg.writeInt(1);

    }
}
