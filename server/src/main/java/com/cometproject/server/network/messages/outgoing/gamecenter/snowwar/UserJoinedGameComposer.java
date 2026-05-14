package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2Player;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes user joined game composer behavior for the network message subsystem.
 */
public class UserJoinedGameComposer extends MessageComposer {
    private final Session session;

    /**
     * Creates a user joined game composer instance for the network message subsystem.
     *
     * @param session Session participating in the operation.
     */
    public UserJoinedGameComposer(Session session) {
        this.session = session;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        SerializeGame2Player.parse(msg, this.session);
        msg.writeBoolean(false);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowStormQueuePlayerAddedComposer;
    }
}
