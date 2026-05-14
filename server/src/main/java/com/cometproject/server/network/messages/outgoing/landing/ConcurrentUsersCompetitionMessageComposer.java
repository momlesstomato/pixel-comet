package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the concurrent users competition message for the Pixel Protocol client.
 */
public class ConcurrentUsersCompetitionMessageComposer extends MessageComposer {

    private int phase;
    private int current;
    private int goal;

    /**
     * Creates a concurrent users competition message composer instance for the network message subsystem.
     *
     * @param phase Phase supplied by the caller.
     * @param current Current supplied by the caller.
     * @param goal Goal supplied by the caller.
     */
    public ConcurrentUsersCompetitionMessageComposer(int phase, int current, int goal) {
        this.phase = phase;
        this.current = current;
        this.goal = goal;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ConcurrentUsersCompetitionMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.phase);
        msg.writeInt(this.current);
        msg.writeInt(this.goal);
    }
}
