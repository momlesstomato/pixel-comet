package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.RoomQueue;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game started composer behavior for the network message subsystem.
 */
public class GameStartedComposer extends MessageComposer {
private final RoomQueue queue;

/**
 * Creates a game started composer instance for the network message subsystem.
 *
 * @param queue Queue supplied by the caller.
 */
public GameStartedComposer(RoomQueue queue) {
    this.queue = queue;
}

/**
 * Writes this message body using the Pixel Protocol field order.
 *
 * @param msg Composer buffer that receives serialized protocol fields.
 */
@Override
public void compose(IComposer msg) {
/* 27 */     SerializeGame2.parse(msg, this.queue);
/*    */   }

/**
 * Returns the id for this network message contract.
 *
 * @return Value exposed by the contract.
 */
@Override
public short getId() {
/* 32 */     return Composers.SnowStormGameStartedComposer;
/*    */   }
}