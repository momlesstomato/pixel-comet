package com.cometproject.server.network.messages.outgoing.crafting;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the crafting display tooltip message for the Pixel Protocol client.
 */
public class CraftingDisplayTooltipMessageComposer extends MessageComposer {
    private final int alertId;

    /**
     * Creates a crafting display tooltip message composer instance for the network message subsystem.
     *
     * @param alertId Alert id supplied by the caller.
     */
    public CraftingDisplayTooltipMessageComposer(int alertId) {
        this.alertId = alertId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CraftingDisplayTooltipMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.alertId);
    }
}
