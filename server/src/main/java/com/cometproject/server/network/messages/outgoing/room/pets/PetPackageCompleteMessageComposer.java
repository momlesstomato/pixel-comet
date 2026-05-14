package com.cometproject.server.network.messages.outgoing.room.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the pet package complete message for the Pixel Protocol client.
 */
public class PetPackageCompleteMessageComposer extends MessageComposer {

    private final int floorItem;
    private final int resultCode;
    private final String name;

    /**
     * Creates a pet package complete message composer instance for the network message subsystem.
     *
     * @param floorItem Floor item supplied by the caller.
     * @param resultCode Result code supplied by the caller.
     * @param name Name supplied by the caller.
     */
    public PetPackageCompleteMessageComposer(int floorItem, int resultCode, String name) {
        this.floorItem = floorItem;
        this.resultCode = resultCode;
        this.name = name;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetPackageOpenedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.floorItem);
        msg.writeInt(this.resultCode);
        msg.writeString(this.name);
    }
}
