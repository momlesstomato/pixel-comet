package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.components.types.messenger.MessengerSearchResult;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the messenger search results message for the Pixel Protocol client.
 */
public class MessengerSearchResultsMessageComposer extends MessageComposer {
    private final List<MessengerSearchResult> currentFriends;
    private final List<MessengerSearchResult> otherPeople;


    /**
     * Creates a messenger search results message composer instance for the network message subsystem.
     *
     * @param currentFriends Current friends supplied by the caller.
     * @param otherPeople Other people supplied by the caller.
     */
    public MessengerSearchResultsMessageComposer(final List<MessengerSearchResult> currentFriends, final List<MessengerSearchResult> otherPeople) {
        this.currentFriends = currentFriends;
        this.otherPeople = otherPeople;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.HabboSearchResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(currentFriends.size());

        for (MessengerSearchResult result : currentFriends) {
            result.compose(msg);
        }

        msg.writeInt(otherPeople.size());

        for (MessengerSearchResult result : otherPeople) {
            result.compose(msg);
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.currentFriends.clear();
        this.otherPeople.clear();
    }
}
