package com.cometproject.server.network.messages.outgoing.rentables;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the rentable data message for the Pixel Protocol client.
 */
public class RentableDataMessageComposer extends MessageComposer {
    private boolean rented;
    private int data;
    private String username;
    private int userId;
    private int price;

    /**
     * Creates a rentable data message composer instance for the network message subsystem.
     *
     * @param rented Rented supplied by the caller.
     * @param data Data supplied by the caller.
     * @param userId User id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param price Price supplied by the caller.
     */
    public RentableDataMessageComposer(boolean rented, int data, int userId, String username, int price) {
        this.rented = rented;
        this.data = data;
        this.userId = userId;
        this.username = username;
        this.price = price;
    }

    /**
     * Creates a rentable data message composer instance for the network message subsystem.
     *
     * @param rented Rented supplied by the caller.
     * @param data Data supplied by the caller.
     * @param price Price supplied by the caller.
     */
    public RentableDataMessageComposer(boolean rented, int data, int price) {
        this.rented = rented;
        this.data = data;
        this.price = price;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RentableDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.rented);
        msg.writeInt(this.data);
        msg.writeInt(this.userId);
        msg.writeString(this.username);
        msg.writeInt(9999999); // Time of expire.
        msg.writeInt(this.price);
    }
}
