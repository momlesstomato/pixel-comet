/*    */ package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
/*    */ 
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2PlayerData;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes arena entered composer behavior for the network message subsystem.
 */
public class ArenaEnteredComposer extends MessageComposer {
    private final HumanGameObject player;

    /**
     * Creates a arena entered composer instance for the network message subsystem.
     *
     * @param player Player participating in the operation.
     */
    public ArenaEnteredComposer(HumanGameObject player) {
        this.player = player;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        SerializeGame2PlayerData.parse(msg, this.player);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowArenaEnteredMessageComposer;
    }
}