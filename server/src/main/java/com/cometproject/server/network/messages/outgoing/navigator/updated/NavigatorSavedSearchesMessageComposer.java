package com.cometproject.server.network.messages.outgoing.navigator.updated;

import com.cometproject.api.game.players.data.components.navigator.ISavedSearch;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;

/**
 * Serializes the navigator saved searches message for the Pixel Protocol client.
 */
public class NavigatorSavedSearchesMessageComposer extends MessageComposer {

    private final Map<Integer, ISavedSearch> savedSearches;

    /**
     * Creates a navigator saved searches message composer instance for the network message subsystem.
     *
     * @param savedSearches Saved searches supplied by the caller.
     */
    public NavigatorSavedSearchesMessageComposer(final Map<Integer, ISavedSearch> savedSearches) {
        this.savedSearches = savedSearches;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NavigatorSavedSearchesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.savedSearches.size());//count

        for (Map.Entry<Integer, ISavedSearch> savedSearch : this.savedSearches.entrySet()) {
            msg.writeInt(savedSearch.getKey());
            msg.writeString(savedSearch.getValue().getView());
            msg.writeString(savedSearch.getValue().getSearchQuery());
            msg.writeString("");
        }
    }
}
