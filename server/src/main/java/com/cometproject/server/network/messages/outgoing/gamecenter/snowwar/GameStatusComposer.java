package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.ComposerShit;
import com.cometproject.games.snowwar.MessageWriter;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGameStatus;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game status composer behavior for the network message subsystem.
 */
public class GameStatusComposer extends MessageComposer {
    private final SnowWarRoom arena;

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param arena Arena supplied by the caller.
     * @return Result produced by the operation.
     */
    public static MessageWriter compose(SnowWarRoom arena) {
        MessageWriter ClientMessage = new MessageWriter(100 + arena.gameEvents.size() * 50);
        ComposerShit.initPacket(Composers.SnowGameStatusMessageComposer, ClientMessage);
        SerializeGameStatus.parseNew(ClientMessage, arena, false);
        ComposerShit.endPacket(ClientMessage);
        return ClientMessage;
    }

    /**
     * Creates a game status composer instance for the network message subsystem.
     *
     * @param arena Arena supplied by the caller.
     */
    public GameStatusComposer(SnowWarRoom arena) {
        this.arena = arena;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        SerializeGameStatus.parse(msg, this.arena, false);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowGameStatusMessageComposer;
    }
}