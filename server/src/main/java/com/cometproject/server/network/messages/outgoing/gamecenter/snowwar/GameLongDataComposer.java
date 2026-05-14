package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.RoomQueue;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game long data composer behavior for the network message subsystem.
 */
public class GameLongDataComposer extends MessageComposer {
    private final RoomQueue lobby;

    /**
     * Creates a game long data composer instance for the network message subsystem.
     *
     * @param lobby Lobby supplied by the caller.
     */
    public GameLongDataComposer(RoomQueue lobby) {
        this.lobby = lobby;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        SerializeGame2.parse(msg, this.lobby);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GameSerializationMessageComposer;
    }
}