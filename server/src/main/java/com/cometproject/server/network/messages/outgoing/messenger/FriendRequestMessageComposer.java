package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the friend request message for the Pixel Protocol client.
 */
public class FriendRequestMessageComposer extends MessageComposer {
    private final PlayerAvatar playerAvatar;

    /**
     * Creates a friend request message composer instance for the network message subsystem.
     *
     * @param playerAvatar Player avatar supplied by the caller.
     */
    public FriendRequestMessageComposer(final PlayerAvatar playerAvatar) {
        this.playerAvatar = playerAvatar;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NewBuddyRequestMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerAvatar.getId());
        msg.writeString(this.playerAvatar.getUsername());
        msg.writeString(this.playerAvatar.getFigure());
    }
}