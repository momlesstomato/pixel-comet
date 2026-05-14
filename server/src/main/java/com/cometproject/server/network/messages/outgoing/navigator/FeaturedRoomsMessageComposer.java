package com.cometproject.server.network.messages.outgoing.navigator;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.navigator.types.featured.FeaturedRoom;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Collection;


/**
 * Serializes the featured rooms message for the Pixel Protocol client.
 */
public class FeaturedRoomsMessageComposer extends MessageComposer {
    private final Collection<FeaturedRoom> featuredRooms;

    /**
     * Creates a featured rooms message composer instance for the network message subsystem.
     *
     * @param featuredRooms Featured rooms supplied by the caller.
     */
    public FeaturedRoomsMessageComposer(Collection<FeaturedRoom> featuredRooms) {
        this.featuredRooms = featuredRooms;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return 0;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.featuredRooms.size());

        for (FeaturedRoom room : this.featuredRooms) {
            if (room.getCategoryId() > 0)
                continue;

            room.compose(msg);

            for (FeaturedRoom room1 : this.featuredRooms) {
                if (room1.getCategoryId() != room.getId()) {
                    continue;
                }

                room1.compose(msg);
            }
        }

        for (FeaturedRoom room : this.featuredRooms) {
            if (!room.isCategory() && room.isRecommended()) {
                msg.writeInt(1);
                room.compose(msg);
                msg.writeInt(0);

                return;
            }
        }

        msg.writeInt(0);
        msg.writeInt(0);
    }
}
