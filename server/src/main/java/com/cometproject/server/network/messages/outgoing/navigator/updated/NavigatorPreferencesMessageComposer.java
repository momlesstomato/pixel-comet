package com.cometproject.server.network.messages.outgoing.navigator.updated;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.types.PlayerSettings;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the navigator preferences message for the Pixel Protocol client.
 */
public class NavigatorPreferencesMessageComposer extends MessageComposer {

    private final PlayerSettings playerSettings;

    /**
     * Creates a navigator preferences message composer instance for the network message subsystem.
     *
     * @param playerSettings Player settings supplied by the caller.
     */
    public NavigatorPreferencesMessageComposer(final PlayerSettings playerSettings) {
        this.playerSettings = playerSettings;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NavigatorPreferencesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerSettings.getNavigatorX());
        msg.writeInt(this.playerSettings.getNavigatorY());
        msg.writeInt(this.playerSettings.getNavigatorWidth());
        msg.writeInt(this.playerSettings.getNavigatorHeight());
        msg.writeBoolean(this.playerSettings.getNavigatorShowSearches());

        msg.writeInt(0);//?
    }
}
