package com.cometproject.server.network.messages.outgoing.user.club;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.components.SubscriptionComponent;
import com.cometproject.server.protocol.messages.MessageComposer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Serializes the subscription center info message for the Pixel Protocol client.
 */
public class SubscriptionCenterInfoMessageComposer
        extends MessageComposer {
    private final SubscriptionComponent subscriptionComponent;

    /**
     * Creates a subscription center info message composer instance for the network message subsystem.
     *
     * @param subscriptionComponent Subscription component supplied by the caller.
     */
    public SubscriptionCenterInfoMessageComposer(SubscriptionComponent subscriptionComponent) {
        this.subscriptionComponent = subscriptionComponent;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    public short getId() {
        return 3277;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        int timeLeft = this.subscriptionComponent.getExpire() - (int)Comet.getTime();
        long timeLeftLong = (this.subscriptionComponent.getExpire() - (int)Comet.getTime()) * 1000L;
        int days = timeLeft / 86400;
        msg.writeInt(days);
        msg.writeString(new SimpleDateFormat("dd-MM-yyyy").format(new Date((long)this.subscriptionComponent.getStart() * 1000L)));
        msg.writeDouble(5.0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
    }
}
