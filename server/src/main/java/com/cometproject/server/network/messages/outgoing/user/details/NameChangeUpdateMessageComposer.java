package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the name change update message for the Pixel Protocol client.
 */
public class NameChangeUpdateMessageComposer extends MessageComposer {
    private String name;
    private int error;
    private List<String> tags;

    /**
     * Creates a name change update message composer instance for the network message subsystem.
     *
     * @param name Name supplied by the caller.
     * @param error Error supplied by the caller.
     * @param tags Tags supplied by the caller.
     */
    public NameChangeUpdateMessageComposer(String name, int error, List<String> tags) {
        this.name = name;
        this.error = error;
        this.tags = tags;
    }

    /**
     * Creates a name change update message composer instance for the network message subsystem.
     *
     * @param name Name supplied by the caller.
     * @param error Error supplied by the caller.
     */
    public NameChangeUpdateMessageComposer(String name, int error) {
        this.name = name;
        this.error = error;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NameChangeUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(error);
        msg.writeString(name);

        if (this.tags == null) {
            msg.writeInt(0);
        } else {
            msg.writeInt(tags.size());
            for (String tag : tags) {
                msg.writeString(name + tag);
            }
        }
    }
}
