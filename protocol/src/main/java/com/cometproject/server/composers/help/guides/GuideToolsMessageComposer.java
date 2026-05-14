package com.cometproject.server.composers.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide tools message for the Pixel Protocol client.
 */
public class GuideToolsMessageComposer extends MessageComposer {

    private final boolean onDuty;
    private final int activeGuideCount;
    private final int activeGuardianCount;

    /**
     * Creates a guide tools message composer instance for the protocol composer subsystem.
     *
     * @param onDuty On duty value supplied by the caller.
     * @param activeGuideCount Active guide count value supplied by the caller.
     * @param activeGuardianCount Active guardian count value supplied by the caller.
     */
    public GuideToolsMessageComposer(final boolean onDuty, int activeGuideCount, int activeGuardianCount) {
        this.onDuty = onDuty;
        this.activeGuardianCount = activeGuardianCount;
        this.activeGuideCount = activeGuideCount;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
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
        msg.writeInt(this.activeGuideCount);//active guides
        msg.writeInt(this.activeGuardianCount);//active guardians
    }
}
