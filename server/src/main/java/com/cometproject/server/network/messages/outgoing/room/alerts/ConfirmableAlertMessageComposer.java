package com.cometproject.server.network.messages.outgoing.room.alerts;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the confirmable alert message for the Pixel Protocol client.
 */
public class ConfirmableAlertMessageComposer extends MessageComposer {
    private final String username;
    private final int type;
    private final boolean isWiredTrigger;

    /**
     * Creates a confirmable alert message composer instance for the network message subsystem.
     *
     * @param username Username supplied by the caller.
     * @param type Type supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     */
    public ConfirmableAlertMessageComposer(final String username, final int type, final boolean isWiredTrigger) {
        this.username = username;
        this.type = type;
        this.isWiredTrigger = isWiredTrigger;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ConfirmableAlertMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.username);
        msg.writeInt(this.type);
        msg.writeBoolean(this.isWiredTrigger);
    }
}
