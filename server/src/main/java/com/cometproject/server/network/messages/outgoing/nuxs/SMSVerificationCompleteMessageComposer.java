package com.cometproject.server.network.messages.outgoing.nuxs;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the SMS verification complete message for the Pixel Protocol client.
 */
public class SMSVerificationCompleteMessageComposer extends MessageComposer {
    private int unknown1;
    private int unknown2;

    /**
     * Creates a SMS verification complete message composer instance for the network message subsystem.
     *
     * @param unknown1 Unknown1 supplied by the caller.
     * @param unknown2 Unknown2 supplied by the caller.
     */
    public SMSVerificationCompleteMessageComposer(int unknown1, int unknown2) {
        this.unknown1 = unknown1;
        this.unknown2 = unknown2;

    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SMSVerificationCompleteMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.unknown1); // idk
        msg.writeInt(this.unknown2); // idk
    }
}