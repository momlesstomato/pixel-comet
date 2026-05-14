package com.cometproject.server.network.messages.outgoing.help;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Describes sanction status composer behavior for the network message subsystem.
 */
public class SanctionStatusComposer extends MessageComposer {

    /**
     * Creates a sanction status composer instance for the network message subsystem.
     */
    public SanctionStatusComposer() {

    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SanctionStatusMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeString("rocks");
        msg.writeInt(0);//Hours
        msg.writeInt(0);
        msg.writeString("cfh.reason.EMPTY"); // => No sanctions :-)
        msg.writeString("rocks");
        msg.writeInt(0);
        msg.writeString("ALERT"); // => Next sanction = warning
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);//if true and second boolean is false it does something. - if false, we got banned, so true is mute
    }
}
