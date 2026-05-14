package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the send floor transformation message for the Pixel Protocol client.
 */
public class SendFloorTransformationMessageComposer extends MessageComposer {
    private final PlayerEntity p;

    /**
     * Creates a send floor transformation message composer instance for the network message subsystem.
     *
     * @param p P supplied by the caller.
     */
    public SendFloorTransformationMessageComposer(PlayerEntity p) {
        this.p = p;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ObjectAddMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(2147418112 - p.getId());
        msg.writeInt(Integer.parseInt(p.getAttribute("item").toString()));
        msg.writeInt(p.getPosition().getX());
        msg.writeInt(p.getPosition().getY());
        msg.writeInt(2);

        msg.writeString(p.getPosition().getZ());
        msg.writeString(0);

        msg.writeInt(1);
        msg.writeInt(0);
        msg.writeString("1");

        msg.writeInt(-1);
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeString(p.getUsername());
    }
}
