package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.SnowWarRoom;

/**
 * Describes serialize game2 snow war game stats behavior for the network message subsystem.
 */
public class SerializeGame2SnowWarGameStats {
    /**
     * Executes parse for this network message contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param arena Arena supplied by the caller.
     */
    public static void parse(IComposer msg, SnowWarRoom arena) {
        msg.writeInt(arena.MostKills.userId);
        msg.writeInt(arena.MostHits.userId);
    }
}