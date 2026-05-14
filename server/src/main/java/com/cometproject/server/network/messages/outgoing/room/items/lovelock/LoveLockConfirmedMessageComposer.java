package com.cometproject.server.network.messages.outgoing.room.items.lovelock;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the love lock confirmed message for the Pixel Protocol client.
 */
public class LoveLockConfirmedMessageComposer extends MessageComposer {
    private final int itemId;

    /**
     * Creates a love lock confirmed message composer instance for the network message subsystem.
     *
     * @param itemId Item id supplied by the caller.
     */
    public LoveLockConfirmedMessageComposer(final int itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.LoveLockDialogueSetLockedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(itemId);
    }
}
