package com.cometproject.server.network.messages.outgoing.navigator;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the navigator meta data message for the Pixel Protocol client.
 */
public class NavigatorMetaDataMessageComposer extends MessageComposer {
    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NavigatorMetaDataParserMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeInt(4);

        msg.writeString("official_view");
        msg.writeInt(0);

        msg.writeString("hotel_view");
        msg.writeInt(0);

        msg.writeString("new_ads");
        msg.writeInt(0);

        msg.writeString("myworld_view");
        msg.writeInt(0);
    }
}
