package com.cometproject.stresstest.connections.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.headers.Events;

/**
 * Serializes the exit room message for the Pixel Protocol client.
 */
public class ExitRoomMessageComposer extends MessageComposer {
    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Events.GoToHotelViewMessageEvent;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {

    }
}
