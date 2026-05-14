package com.cometproject.server.network.messages.outgoing.quests.seasonal;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the seasonal calendar message for the Pixel Protocol client.
 */
public class SeasonalCalendarMessageComposer extends MessageComposer {
    private int type;

    /**
     * Creates a seasonal calendar message composer instance for the network message subsystem.
     *
     * @param type Type supplied by the caller.
     */
    public SeasonalCalendarMessageComposer(int type) {
        this.type = type;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SeasonalCalendarMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {

        msg.writeInt(1);

        msg.writeString("1");
        msg.writeInt(this.type); // status
        msg.writeInt(1); // status Additional string type?
        msg.writeInt(103); // Currency
        msg.writeInt(1); // ID
        msg.writeBoolean(false); // Accepted / Not
        msg.writeString("test"); // Accepted / Not
        msg.writeString("photo"); // Thumbnail quest
        msg.writeInt(50); // Amount of the prize.
        msg.writeString("addition"); // Thumbnail addition on status change?
        msg.writeInt(1); // Current day
        msg.writeInt(1); // Total days
        msg.writeInt(1); // Sort order

        msg.writeString("catalog"); // Catalog page name.
        msg.writeString("catalog"); // Quest name?.
        msg.writeBoolean(false); // Has hint.
        msg.writeInt(0); // Timestamp.
    }
}
