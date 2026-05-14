package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the friend requests message for the Pixel Protocol client.
 */
public class FriendRequestsMessageComposer extends MessageComposer {
    private final List<PlayerAvatar> requests;

    /**
     * Creates a friend requests message composer instance for the network message subsystem.
     *
     * @param requests Requests supplied by the caller.
     */
    public FriendRequestsMessageComposer(final List<PlayerAvatar> requests) {
        this.requests = requests;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.BuddyRequestsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.requests.size());
        msg.writeInt(this.requests.size());

        for (PlayerAvatar avatar : this.requests) {
            msg.writeInt(avatar.getId());
            msg.writeString(avatar.getUsername());
            msg.writeString(avatar.getFigure());
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.requests.clear();
    }
}
