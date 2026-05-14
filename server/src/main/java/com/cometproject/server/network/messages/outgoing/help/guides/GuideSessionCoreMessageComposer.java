package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session core message for the Pixel Protocol client.
 */
public class GuideSessionCoreMessageComposer extends MessageComposer {
    private final HelpRequest request;

    /**
     * Creates a guide session core message composer instance for the network message subsystem.
     *
     * @param r R supplied by the caller.
     */
    public GuideSessionCoreMessageComposer(HelpRequest r) {
        this.request = r;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionStartedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.request.getPlayerSession().getPlayer().getId());
        msg.writeString(this.request.getPlayerSession().getPlayer().getData().getUsername());
        msg.writeString(this.request.getPlayerSession().getPlayer().getData().getFigure());

        msg.writeInt(this.request.getGuideSession().getPlayer().getId());
        msg.writeString(this.request.getGuideSession().getPlayer().getData().getUsername());
        msg.writeString(this.request.getGuideSession().getPlayer().getData().getFigure());
    }
}