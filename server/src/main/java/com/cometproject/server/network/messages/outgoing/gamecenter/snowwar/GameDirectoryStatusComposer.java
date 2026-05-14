package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game directory status composer behavior for the network message subsystem.
 */
public class GameDirectoryStatusComposer extends MessageComposer {
    public static final int ENABLED = 0;
    public static final int UNKNOW1 = 1;
    public static final int UNKNOW2 = 2;
    public static final int UNKNOW3 = 3;
    private final int state;
    private final int timeTillNextGame;
    private final int userId;

    /**
     * Creates a game directory status composer instance for the network message subsystem.
     *
     * @param state State supplied by the caller.
     * @param timeTillNextGame Time till next game supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public GameDirectoryStatusComposer(int state, int timeTillNextGame, int userId) {
        this.state = state;
        this.timeTillNextGame = timeTillNextGame;
        this.userId = userId;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.state);
        msg.writeInt(this.timeTillNextGame);
        msg.writeInt(this.userId);
        msg.writeInt(-1);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowStormGamesInformationComposer;
    }
}
