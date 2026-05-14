package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the slide object bundle message for the Pixel Protocol client.
 */
public class SlideObjectBundleMessageComposer extends MessageComposer {
    private final Position from;
    private final Position to;
    private final int rollerItemId;
    private final int avatarId;
    private final int itemId;
    private final Map<Integer, Double> items;

    /**
     * Creates a slide object bundle message composer instance for the network message subsystem.
     *
     * @param from From supplied by the caller.
     * @param to To supplied by the caller.
     * @param rollerItemId Roller item id supplied by the caller.
     * @param avatarId Avatar id supplied by the caller.
     * @param itemId Item id supplied by the caller.
     */
    public SlideObjectBundleMessageComposer(Position from, Position to, int rollerItemId, int avatarId, int itemId) {
        this.from = from;
        this.to = to;
        this.rollerItemId = rollerItemId;
        this.avatarId = avatarId;
        this.itemId = itemId;
        this.items = null;
    }

    /**
     * Creates a slide object bundle message composer instance for the network message subsystem.
     *
     * @param from From supplied by the caller.
     * @param to To supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public SlideObjectBundleMessageComposer(Position from, Position to, int playerId) {
        this.from = from;
        this.to = to;
        this.rollerItemId = 0;
        this.avatarId = 0;
        this.itemId = 2147418112 + playerId;
        this.items = null;
    }

    /**
     * Creates a slide object bundle message composer instance for the network message subsystem.
     *
     * @param from From supplied by the caller.
     * @param to To supplied by the caller.
     * @param rollerItemId Roller item id supplied by the caller.
     * @param avatarId Avatar id supplied by the caller.
     * @param items Items supplied by the caller.
     */
    public SlideObjectBundleMessageComposer(Position from, Position to, int rollerItemId, int avatarId,
                                            Map<Integer, Double> items) {
        this.from = from;
        this.to = to;
        this.rollerItemId = rollerItemId;
        this.avatarId = avatarId;
        this.itemId = 0;
        this.items = items;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SlideObjectBundleMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.items == null) {
            composeLegacy(msg);
            return;
        }

        msg.writeInt(from.getX());
        msg.writeInt(from.getY());

        msg.writeInt(to.getX());
        msg.writeInt(to.getY());

        msg.writeInt(this.items.size());

        for (Map.Entry<Integer, Double> item : this.items.entrySet()) {
            msg.writeInt(item.getKey());

            // we want to ensure we slide to the same height as we were previously at.
            msg.writeDouble(item.getValue());
            msg.writeDouble(item.getValue());
        }

        msg.writeInt(this.rollerItemId);

        if (this.avatarId != 0) {
            // 1 = mv, 2 = std
            msg.writeInt(2);

            msg.writeInt(this.avatarId);
            msg.writeDouble(from.getZ());
            msg.writeDouble(to.getZ());
        }
    }

    private void composeLegacy(IComposer msg) {
        final boolean isItem = itemId > 0;

        msg.writeInt(from.getX());
        msg.writeInt(from.getY());

        msg.writeInt(to.getX());
        msg.writeInt(to.getY());

        msg.writeInt(isItem ? 1 : 0);

        if (isItem) {
            msg.writeInt(itemId);
        } else {
            msg.writeInt(rollerItemId);
            msg.writeInt(2);
            msg.writeInt(avatarId);
        }

        msg.writeDouble(from.getZ());
        msg.writeDouble(to.getZ());

        if (isItem) {
            msg.writeInt(rollerItemId);
        }
    }
}
