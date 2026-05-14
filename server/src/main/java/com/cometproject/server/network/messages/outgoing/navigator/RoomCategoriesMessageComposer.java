package com.cometproject.server.network.messages.outgoing.navigator;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.navigator.types.Category;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the room categories message for the Pixel Protocol client.
 */
public class RoomCategoriesMessageComposer extends MessageComposer {
    private final List<Category> categories;
    private final int rank;

    /**
     * Creates a room categories message composer instance for the network message subsystem.
     *
     * @param categories Categories supplied by the caller.
     * @param rank Rank supplied by the caller.
     */
    public RoomCategoriesMessageComposer(final List<Category> categories, final int rank) {
        this.categories = categories;
        this.rank = rank;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserFlatCatsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.categories.size());

        for (Category cat : this.categories) {
            msg.writeInt(cat.getId());
            msg.writeString(cat.getPublicName());
            msg.writeBoolean(cat.getRequiredRank() <= this.rank);
            msg.writeBoolean(false);
            msg.writeString("");
            msg.writeString("");
            msg.writeBoolean(false);
        }
    }
}
