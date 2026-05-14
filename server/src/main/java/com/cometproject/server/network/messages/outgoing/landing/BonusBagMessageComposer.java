package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the bonus bag message for the Pixel Protocol client.
 */
public class BonusBagMessageComposer extends MessageComposer {

    private final int xp;
    private final int level;
    private String reward = "";
    private final int spriteId;
    private int flush;

    /**
     * Creates a bonus bag message composer instance for the network message subsystem.
     *
     * @param config Config supplied by the caller.
     * @param xp Xp supplied by the caller.
     * @param level Level supplied by the caller.
     */
    public BonusBagMessageComposer(String config, int xp, int level) {
        String[] c = config.split(",");
        this.reward = c[0];
        this.spriteId = Integer.parseInt(c[1]);
        this.xp = xp;
        this.level = level;
    }


    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.BonusBagMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        int coefficient = 1500 * this.level;
        int resting = coefficient - this.xp;
        msg.writeString(this.reward);
        msg.writeInt(this.spriteId);
        msg.writeInt(coefficient);
        msg.writeInt(Math.max(resting, 0));
    }
}
