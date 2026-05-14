package com.cometproject.server.network.messages.outgoing.user.club;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.components.SubscriptionComponent;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the club status message for the Pixel Protocol client.
 */
public class ClubStatusMessageComposer extends MessageComposer {
    private final SubscriptionComponent subscriptionComponent;

    /**
     * Creates a club status message composer instance for the network message subsystem.
     *
     * @param subscriptionComponent Subscription component supplied by the caller.
     */
    public ClubStatusMessageComposer(final SubscriptionComponent subscriptionComponent) {
        this.subscriptionComponent = subscriptionComponent;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ScrSendUserInfoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        int timeLeft = 0;
        int days = 0;
        int months = 0;

        if (subscriptionComponent.isValid()) {
            timeLeft = subscriptionComponent.getExpire() - (int) Comet.getTime();
            days = (int) Math.ceil(timeLeft / 86400);
            months = days / 31;
        } else {
            if (subscriptionComponent.exists()) {
                subscriptionComponent.delete();
            }
        }

        msg.writeString("habbo_club");

        msg.writeInt(subscriptionComponent.isValid() ? days : 1);
        msg.writeInt(2);
        msg.writeInt(subscriptionComponent.isValid() ? months : 1);
        msg.writeInt(1);
        msg.writeBoolean(subscriptionComponent.isValid());
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(subscriptionComponent.isValid() ? days : 1);
        msg.writeInt(subscriptionComponent.isValid() ? days : 1);
    }
}
