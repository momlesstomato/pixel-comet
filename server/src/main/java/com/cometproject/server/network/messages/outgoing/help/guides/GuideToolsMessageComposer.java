package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.guides.GuideManager;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide tools message for the Pixel Protocol client.
 */
public class GuideToolsMessageComposer extends MessageComposer {

    private final boolean onDuty;

    /**
     * Creates a guide tools message composer instance for the network message subsystem.
     *
     * @param onDuty On duty supplied by the caller.
     */
    public GuideToolsMessageComposer(final boolean onDuty) {
        this.onDuty = onDuty;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideToolsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.onDuty);
        msg.writeInt(0);
        msg.writeInt(GuideManager.getInstance().getActiveGuideCount());//active guides
        msg.writeInt(GuideManager.getInstance().getActiveGuardianCount());//active guardians
    }
}
