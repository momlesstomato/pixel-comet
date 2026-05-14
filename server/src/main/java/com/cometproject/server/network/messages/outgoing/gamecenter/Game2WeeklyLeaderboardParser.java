package com.cometproject.server.network.messages.outgoing.gamecenter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.data.GamePlayer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Describes game2 weekly leaderboard parser behavior for the network message subsystem.
 */
public class Game2WeeklyLeaderboardParser  extends MessageComposer {
    private int gameId;
    private List<GamePlayer> data;

    /**
     * Creates a game2 weekly leaderboard parser instance for the network message subsystem.
     *
     * @param gameId Game id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public Game2WeeklyLeaderboardParser(int gameId, int playerId){
        this.gameId = gameId;
        //this.data = BetDao.getLeaderBoard(gameId, playerId, false, false);
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        int i = 1;

        msg.writeInt(2012);
        msg.writeInt(0); // week
        msg.writeInt(0); // maxOffSet
        msg.writeInt(0); // Offser
        msg.writeInt(23); // Minutes until reset.

        /*msg.writeInt(this.data.size() > 3 ? 3 : this.data.size());
        for(final GamePlayer player : this.data) {
            msg.writeInt(1);
            msg.writeInt(player.getPoints());
            msg.writeInt(i++);
            msg.writeString(player.getUsername());
            msg.writeString(player.getFigure());
            msg.writeString(player.getGender().toUpperCase());
            if(i == 4) break;
        }

        msg.writeInt(1); // position start or end....
        msg.writeInt(gameId);*/
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.Game2WeeklyLeaderboardParser;
    }
}
