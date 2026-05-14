package com.cometproject.server.network.messages.outgoing.user.club;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the club presents notification message for the Pixel Protocol client.
 */
public class ClubPresentsNotificationMessageComposer extends MessageComposer {
    private final int notificationType;

    /**
     * Creates a club presents notification message composer instance for the network message subsystem.
     *
     * @param notificationType Notification type supplied by the caller.
     */
    public ClubPresentsNotificationMessageComposer(final int notificationType){
        this.notificationType = notificationType;
    }
    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ClubPresentsNotificationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.notificationType);
    }
}
