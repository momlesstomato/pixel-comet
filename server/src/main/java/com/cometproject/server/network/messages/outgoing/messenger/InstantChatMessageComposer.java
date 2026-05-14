package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the instant chat message for the Pixel Protocol client.
 */
public class InstantChatMessageComposer extends MessageComposer {
    private final String message;
    private final int fromId;

    private String username;
    private String figure;
    private int playerId;

    /**
     * Creates a instant chat message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     * @param fromId From id supplied by the caller.
     */
    public InstantChatMessageComposer(final String message, final int fromId) {
        this.message = message;
        this.fromId = fromId;
    }

    /**
     * Creates a instant chat message composer instance for the network message subsystem.
     *
     * @param message Message supplied by the caller.
     * @param fromId From id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public InstantChatMessageComposer(final String message, final int fromId, final String username, final String figure, final int playerId) {
        this.message = message;
        this.fromId = fromId;
        this.username = username;
        this.figure = figure;
        this.playerId = playerId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NewConsoleMessageMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(fromId);
        msg.writeString(message);
        msg.writeInt(0);

        if (this.username != null) { // we can assume the rest aren't null
            final String data = username + "/" + figure + "/" + playerId;
            msg.writeString(data);
        }
    }
}
