package com.cometproject.server.network.messages.outgoing.gamecenter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.gamecenter.GameCenterManager;
import com.cometproject.server.game.players.data.GamePlayer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Describes weekly leaderboard composer behavior for the network message subsystem.
 */
public class WeeklyLeaderboardComposer extends MessageComposer {
    private List<GamePlayer> data;
    private int gameId;

    /**
     * Creates a weekly leaderboard composer instance for the network message subsystem.
     *
     * @param gameId Game id supplied by the caller.
     */
    public WeeklyLeaderboardComposer(int gameId) {
        this.gameId = gameId;
        this.data = GameCenterManager.getInstance().getLeaderboardByWeek(true);
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
        msg.writeInt(0); // week offset?
        msg.writeInt(0); // week offset limit?
        msg.writeInt(0); // 0 = this week, other = prev week
        msg.writeInt(23); // day
        msg.writeInt(data.size());
        for(final GamePlayer player : data) {
            msg.writeInt(player.getId());
            msg.writeInt(player.getPoints());
            msg.writeInt(i++);
            msg.writeString(player.getUsername());
            msg.writeString(player.getFigure());
            msg.writeString(player.getGender().toUpperCase());
        }
        msg.writeInt(0); // position start or end....
        msg.writeInt(gameId);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.WeeklyLeaderboardComposer;
    }
}
