package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes account game status composer behavior for the network message subsystem.
 */
public class AccountGameStatusComposer extends MessageComposer {
    private final int gameTypeId;

    /**
     * Creates a account game status composer instance for the network message subsystem.
     *
     * @param gameTypeId Game type id supplied by the caller.
     */
    public AccountGameStatusComposer(int gameTypeId) {
        this.gameTypeId = gameTypeId;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.gameTypeId);
        msg.writeInt(-1);
        msg.writeInt(0);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GameAccountStatusMessageComposer;
    }
}