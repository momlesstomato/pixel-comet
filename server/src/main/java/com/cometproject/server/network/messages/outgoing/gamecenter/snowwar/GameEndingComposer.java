package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.SnowWar;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2GameResult;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2SnowWarGameStats;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2TeamScoreData;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game ending composer behavior for the network message subsystem.
 */
public class GameEndingComposer extends MessageComposer {
    private final SnowWarRoom arena;

    /**
     * Creates a game ending composer instance for the network message subsystem.
     *
     * @param arena Arena supplied by the caller.
     */
    public GameEndingComposer(SnowWarRoom arena) {
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
        SerializeGame2GameResult.parse(msg, this.arena);
        msg.writeInt(SnowWar.TEAMS.length);
        for (int teamId : SnowWar.TEAMS) {
            SerializeGame2TeamScoreData.parse(msg, teamId, this.arena.teamScore[teamId - 1], this.arena.TeamPlayers.get(teamId).values());
        }
        SerializeGame2SnowWarGameStats.parse(msg, this.arena);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return 0;
    }
}
