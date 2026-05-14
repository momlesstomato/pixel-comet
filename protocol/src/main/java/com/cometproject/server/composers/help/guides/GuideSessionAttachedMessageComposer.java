package com.cometproject.server.composers.help.guides;

import com.cometproject.api.game.moderation.guides.IHelpRequest;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session attached message for the Pixel Protocol client.
 */
public class GuideSessionAttachedMessageComposer extends MessageComposer {
    private final IHelpRequest helpRequest;

    private final boolean isGuide;

    /**
     * Creates a guide session attached message composer instance for the protocol composer subsystem.
     *
     * @param helpRequest Help request value supplied by the caller.
     * @param isGuide Is guide value supplied by the caller.
     */
    public GuideSessionAttachedMessageComposer(final IHelpRequest helpRequest, final boolean isGuide) {
        this.helpRequest = helpRequest;
        this.isGuide = isGuide;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
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
