package com.cometproject.stresstest.connections.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.protocol.headers.Events;

/**
 * Serializes the SSO ticket message for the Pixel Protocol client.
 */
public class SSOTicketMessageComposer extends MessageComposer {

    private final String ssoTicket;

    /**
     * Creates a SSO ticket message composer instance for the tooling subsystem.
     *
     * @param ssoTicket Sso ticket supplied by the caller.
     */
    public SSOTicketMessageComposer(String ssoTicket) {
        this.ssoTicket = ssoTicket;
    }

    /**
     * Returns the id for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Events.SSOTicketMessageEvent;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.ssoTicket);
    }
}
