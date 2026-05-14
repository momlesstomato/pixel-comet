package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the food update message for the Pixel Protocol client.
 */
public class FoodUpdateMessageComposer extends MessageComposer {
    private int playerId;
    private int objectId;
    private int state;
    private int test;
    private int test2;

    /**
     * Creates a food update message composer instance for the protocol composer subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param objectId Object id supplied by the caller.
     * @param state State supplied by the caller.
     * @param test Test supplied by the caller.
     * @param test2 Test2 supplied by the caller.
     */
    public FoodUpdateMessageComposer(int playerId, int objectId, int state, int test, int test2) {
        this.playerId = playerId;
        this.objectId = objectId;
        this.state = state;
        this.test = test;
        this.test2 = test2;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 5;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(objectId);
        msg.writeInt(playerId);
        msg.writeInt(state);
        msg.writeInt(test);
        msg.writeInt(test2);
    }
}
