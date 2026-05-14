package com.cometproject.server.network.messages.outgoing.crafting;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the market place config message for the Pixel Protocol client.
 */
public class MarketPlaceConfigMessageComposer extends MessageComposer {

    /**
     * Creates a market place config message composer instance for the network message subsystem.
     */
    public MarketPlaceConfigMessageComposer(){
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MarketPlaceConfigMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true);
        msg.writeInt(1);
        msg.writeInt(10);
        msg.writeInt(5);
        msg.writeInt(1);
        msg.writeInt(1000000);
        msg.writeInt(48);
        msg.writeInt(7);
    }
}
