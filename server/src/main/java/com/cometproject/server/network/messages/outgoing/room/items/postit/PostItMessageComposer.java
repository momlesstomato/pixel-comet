package com.cometproject.server.network.messages.outgoing.room.items.postit;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the post it message for the Pixel Protocol client.
 */
public class PostItMessageComposer extends MessageComposer {
    private final int id;
    private final String data;

    /**
     * Creates a post it message composer instance for the network message subsystem.
     *
     * @param id Id supplied by the caller.
     * @param data Data supplied by the caller.
     */
    public PostItMessageComposer(final int id, final String data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.StickyNoteMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.id + "");
        msg.writeString(this.data);
    }
}
