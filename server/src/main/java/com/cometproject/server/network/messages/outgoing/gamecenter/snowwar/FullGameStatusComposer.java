package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.SnowWar;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2GameObjects;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGameStatus;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes full game status composer behavior for the network message subsystem.
 */
public class FullGameStatusComposer extends MessageComposer {
    private final SnowWarRoom arena;

    /**
     * Creates a full game status composer instance for the network message subsystem.
     *
     * @param arena Arena supplied by the caller.
     */
    public FullGameStatusComposer(SnowWarRoom arena) {
        this.arena = arena;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        SerializeGame2GameObjects.parse(msg, this.arena);
        msg.writeInt(0);
        msg.writeInt(SnowWar.TEAMS.length);
        SerializeGameStatus.parse(msg, this.arena, true);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowStormFullGameStatusComposer;
    }
}