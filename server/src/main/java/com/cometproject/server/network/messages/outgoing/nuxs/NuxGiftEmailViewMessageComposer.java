package com.cometproject.server.network.messages.outgoing.nuxs;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the NUX gift email view message for the Pixel Protocol client.
 */
public class NuxGiftEmailViewMessageComposer extends MessageComposer {
    private String data;
    private static int amount = 0;
    private boolean status;
    private boolean unkown1;
    private boolean unkown2;

    /**
     * Creates a NUX gift email view message composer instance for the network message subsystem.
     *
     * @param data Data supplied by the caller.
     * @param amount Amount supplied by the caller.
     * @param status Status supplied by the caller.
     * @param unkown1 Unkown1 supplied by the caller.
     * @param unkown2 Unkown2 supplied by the caller.
     */
    public NuxGiftEmailViewMessageComposer(String data, int amount, boolean status, boolean unkown1, boolean unkown2) {
        this.data = data;
        this.amount = amount;
        this.status = status;
        this.unkown1 = unkown1;
        this.unkown2 = unkown2;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NuxGiftEmailViewMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {

        msg.writeString(data); // email
        msg.writeBoolean(unkown1); // unk 1
        msg.writeBoolean(unkown2); // unk 1
        msg.writeInt(amount);
        msg.writeBoolean(status); // Open / Closed
    }
}