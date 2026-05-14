package com.cometproject.server.network.messages.outgoing.user.purse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the update activity points message for the Pixel Protocol client.
 */
public class UpdateActivityPointsMessageComposer extends MessageComposer {

    private int activityPoints;
    private int change;
    private int type;

    /**
     * Creates a update activity points message composer instance for the network message subsystem.
     *
     * @param activityPoints Activity points supplied by the caller.
     * @param change Change supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public UpdateActivityPointsMessageComposer(int activityPoints, int change, int type) {
        this.activityPoints = activityPoints;
        this.change = change;
        this.type = type;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.HabboActivityPointNotificationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.activityPoints);
        msg.writeInt(this.change);
        msg.writeInt(this.type);
    }
}
