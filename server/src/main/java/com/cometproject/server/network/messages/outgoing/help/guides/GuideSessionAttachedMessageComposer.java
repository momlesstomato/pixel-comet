package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session attached message for the Pixel Protocol client.
 */
public class GuideSessionAttachedMessageComposer extends MessageComposer {
    private final HelpRequest helpRequest;

    private final boolean isGuide;

    /**
     * Creates a guide session attached message composer instance for the network message subsystem.
     *
     * @param helpRequest Help request supplied by the caller.
     * @param isGuide Is guide supplied by the caller.
     */
    public GuideSessionAttachedMessageComposer(final HelpRequest helpRequest, final boolean isGuide) {
        this.helpRequest = helpRequest;
        this.isGuide = isGuide;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionAttachedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.isGuide);
        msg.writeInt(1);//type
        msg.writeString(helpRequest.getMessage());
        msg.writeInt(60);//avg waiting time
    }
}
