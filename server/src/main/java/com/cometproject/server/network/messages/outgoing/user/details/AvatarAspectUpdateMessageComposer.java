package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the avatar aspect update message for the Pixel Protocol client.
 */
public class AvatarAspectUpdateMessageComposer extends MessageComposer {

    private final String figure;
    private final String gender;

    /**
     * Creates a avatar aspect update message composer instance for the network message subsystem.
     *
     * @param figure Figure supplied by the caller.
     * @param gender Gender supplied by the caller.
     */
    public AvatarAspectUpdateMessageComposer(String figure, String gender) {
        this.figure = figure;
        this.gender = gender;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AvatarAspectUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.figure);
        msg.writeString(this.gender);
    }
}
