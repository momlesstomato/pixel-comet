package com.cometproject.server.network.messages.outgoing.rentables;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the rent offer message for the Pixel Protocol client.
 */
public class RentOfferMessageComposer extends MessageComposer {
    private boolean rented;
    private String item;
    private boolean buyout;
    private int priceCredits;
    private int priceExtra;
    private int extraType;

    /**
     * Creates a rent offer message composer instance for the network message subsystem.
     *
     * @param rented Rented supplied by the caller.
     * @param item Item supplied by the caller.
     * @param buyout Buyout supplied by the caller.
     * @param priceCredits Price credits supplied by the caller.
     * @param pricExtra Pric extra supplied by the caller.
     * @param extraType Extra type supplied by the caller.
     */
    public RentOfferMessageComposer(boolean rented, String item, boolean buyout, int priceCredits, int pricExtra, int extraType) {
        this.rented = rented;
        this.item = item;
        this.buyout = buyout;
        this.priceCredits = priceCredits;
        this.priceExtra = pricExtra;
        this.extraType = extraType;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RentOfferMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.rented);
        msg.writeString(this.item);
        msg.writeBoolean(this.buyout);
        msg.writeInt(this.priceCredits);
        msg.writeInt(this.priceExtra);
        msg.writeInt(this.extraType);
    }
}
