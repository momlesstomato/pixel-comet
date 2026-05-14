package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;

/**
 * Serializes the send hotel view looks message for the Pixel Protocol client.
 */
public class SendHotelViewLooksMessageComposer extends MessageComposer {

    private final String key;
    private final Map<PlayerAvatar, Integer> players;

    /**
     * Creates a send hotel view looks message composer instance for the network message subsystem.
     *
     * @param key Key supplied by the caller.
     * @param players Players supplied by the caller.
     */
    public SendHotelViewLooksMessageComposer(String key, Map<PlayerAvatar, Integer> players) {
        this.key = key;
        this.players = players;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SendHotelViewLooksMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(key);
        msg.writeInt(this.players.size());

        for (Map.Entry<PlayerAvatar, Integer> player : players.entrySet()) {
            msg.writeInt(player.getKey().getId());
            msg.writeString(player.getKey().getUsername());
            msg.writeString(player.getKey().getFigure());
            msg.writeInt(1);//?
            msg.writeInt(player.getValue());
        }

    }
}
