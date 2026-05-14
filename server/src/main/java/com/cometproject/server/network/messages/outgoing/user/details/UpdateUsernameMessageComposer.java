package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the update username message for the Pixel Protocol client.
 */
public class UpdateUsernameMessageComposer extends MessageComposer {
    private String user;

    /**
     * Creates a update username message composer instance for the network message subsystem.
     *
     * @param user User supplied by the caller.
     */
    public UpdateUsernameMessageComposer(String user) {
        this.user = user;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UpdateUsernameMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
        msg.writeString(user);
        msg.writeInt(0);
    }
}
