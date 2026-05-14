package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.SnowWar;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeArena;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2PlayerData;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes enter arena composer behavior for the network message subsystem.
 */
public class EnterArenaComposer extends MessageComposer {
    private final SnowWarRoom arena;

    /**
     * Creates a enter arena composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     */
    public EnterArenaComposer(SnowWarRoom room) {
        this.arena = room;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(this.arena.ArenaType.ArenaType);
        msg.writeInt(SnowWar.TEAMS.length);
        msg.writeInt(this.arena.players.size());
        for (HumanGameObject Player : this.arena.players.values()) {
            SerializeGame2PlayerData.parse(msg, Player);
        }
        SerializeArena.parse(msg, this.arena);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    public short getId() {
        return Composers.SnowEnterArenaMessageComposer;
    }
}