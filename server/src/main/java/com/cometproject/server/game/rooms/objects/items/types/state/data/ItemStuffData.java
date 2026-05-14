package com.cometproject.server.game.rooms.objects.items.types.state.data;

import com.cometproject.api.networking.messages.IComposer;

/**
 * Defines the item stuff data contract for the room subsystem.
 */
public interface ItemStuffData {
    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getType();

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    void compose(IComposer msg);
}
