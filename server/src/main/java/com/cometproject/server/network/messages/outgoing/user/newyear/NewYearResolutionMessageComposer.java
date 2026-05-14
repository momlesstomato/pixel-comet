package com.cometproject.server.network.messages.outgoing.user.newyear;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the new year resolution message for the Pixel Protocol client.
 */
public class NewYearResolutionMessageComposer extends MessageComposer {
    private final int winStreak;
    /**
     * Creates a new year resolution message composer instance for the network message subsystem.
     *
     * @param winStreak Win streak supplied by the caller.
     */
    public NewYearResolutionMessageComposer(int winStreak){
        this.winStreak = winStreak;
    }
    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NewYearResolutionMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(230); // time
        msg.writeInt(40); // size to foreach

        for(int i = 0; i< 41; i++) {
            msg.writeInt(i); // ID
            msg.writeInt(this.winStreak); // ???
            msg.writeString(i + "BET"); // Name
            msg.writeInt(0); // ???
            msg.writeInt(0); // ???
        }

        msg.writeInt(1000); // ???
    }
}
