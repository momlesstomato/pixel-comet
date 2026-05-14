package com.cometproject.server.network.messages.outgoing.user.permissions;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the fuserights message for the Pixel Protocol client.
 */
public class FuserightsMessageComposer extends MessageComposer {
    private final boolean hasClub;
    private final int rank;

    /**
     * Creates a fuserights message composer instance for the network message subsystem.
     *
     * @param hasClub Has club supplied by the caller.
     * @param rank Rank supplied by the caller.
     */
    public FuserightsMessageComposer(final boolean hasClub, final int rank) {
        this.hasClub = hasClub;
        this.rank = rank;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserRightsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(2);
        msg.writeInt(this.rank);
        msg.writeBoolean(true);// Is ambassador!
    }
}
