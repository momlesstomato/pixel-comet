package com.cometproject.server.network.messages.outgoing.room.polls;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the quick poll message for the Pixel Protocol client.
 */
public class QuickPollMessageComposer extends MessageComposer {

    private final String question;

    /**
     * Creates a quick poll message composer instance for the network message subsystem.
     *
     * @param question Question supplied by the caller.
     */
    public QuickPollMessageComposer(String question) {
        this.question = question;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.QuickPollMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString("");
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(1);//duration
        msg.writeInt(-1);//id
        msg.writeInt(120);//number
        msg.writeInt(3);
        msg.writeString(this.question);
    }
}
