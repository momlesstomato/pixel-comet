package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the send shadow message for the Pixel Protocol client.
 */
public class SendShadowMessageComposer extends MessageComposer {
    private final int playerId;
    private final Position position;

    /**
     * Creates a send shadow message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param position Position supplied by the caller.
     */
    public SendShadowMessageComposer(int playerId, Position position) {
        this.playerId = playerId;
        this.position = position;
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
        msg.writeInt(2147418112 + this.playerId);
        msg.writeInt(20555);
        msg.writeInt(this.position.getX());
        msg.writeInt(this.position.getY());
        msg.writeInt(0); // rotation
        msg.writeString(this.position.getZ());
        msg.writeString("0");

        msg.writeInt(0);
        msg.writeInt(1);
        String state = "state\t0\timageUrl\t//localhost/swf/c_images/notifications/shadow.png\tclickUrl\t\toffsetX\t-2740\toffsetY\t-1200\toffsetZ\t9000";

        String[] adsData = state.split(String.valueOf((char) 9));
        int count = adsData.length;

        msg.writeInt(count / 2);

        for (int i = 0; i <= count - 1; i++) {
            msg.writeString(adsData[i]);
        }

        msg.writeInt(-1);
        msg.writeInt(0);
        msg.writeInt(this.playerId);
        msg.writeString("");
    }
}
