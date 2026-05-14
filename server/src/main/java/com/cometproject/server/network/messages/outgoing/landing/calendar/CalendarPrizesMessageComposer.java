package com.cometproject.server.network.messages.outgoing.landing.calendar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.landing.calendar.CalendarDay;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the calendar prizes message for the Pixel Protocol client.
 */
public class CalendarPrizesMessageComposer extends MessageComposer {
    private CalendarDay c;

    /**
     * Creates a calendar prizes message composer instance for the network message subsystem.
     *
     * @param c C supplied by the caller.
     */
    public CalendarPrizesMessageComposer(CalendarDay c) {
        this.c = c;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CalendarPrizesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true); // enabled
        msg.writeString(c.getProduct()); // productdata
        msg.writeString(c.getImage());
        msg.writeString(c.getItem());
    }
}
