package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the hotel view item message for the Pixel Protocol client.
 */
public class HotelViewItemMessageComposer extends MessageComposer {

    private final String campaignString;
    private final String campaignName;

    /**
     * Creates a hotel view item message composer instance for the network message subsystem.
     *
     * @param campaignString Campaign string supplied by the caller.
     * @param campaignName Campaign name supplied by the caller.
     */
    public HotelViewItemMessageComposer(String campaignString, String campaignName) {
        this.campaignString = campaignString;
        this.campaignName = campaignName;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CampaignMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.campaignString);
        msg.writeString(this.campaignName);
    }
}
