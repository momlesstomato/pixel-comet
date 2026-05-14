package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.gamecenter.fastfood.net.FastFoodGameSession;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the my power ups message for the Pixel Protocol client.
 */
public class MyPowerUpsMessageComposer extends MessageComposer {

    private final FastFoodGameSession gameSession;

    /**
     * Creates a my power ups message composer instance for the protocol composer subsystem.
     *
     * @param gameSession Game session supplied by the caller.
     */
    public MyPowerUpsMessageComposer(final FastFoodGameSession gameSession) {
        this.gameSession = gameSession;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 14;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(3);

        msg.writeInt(0);
        msg.writeInt(this.gameSession.getParachutes());

        msg.writeInt(1);
        msg.writeInt(this.gameSession.getMissiles());

        msg.writeInt(2);
        msg.writeInt(this.gameSession.getShields());
    }
}
