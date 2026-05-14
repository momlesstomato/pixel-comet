package com.cometproject.server.network.messages.outgoing.user.purse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the currencies message for the Pixel Protocol client.
 */
public class CurrenciesMessageComposer extends MessageComposer {
    private final Map<Integer, Integer> currencies;

    /**
     * Creates a currencies message composer instance for the network message subsystem.
     *
     * @param currencies Currencies supplied by the caller.
     */
    public CurrenciesMessageComposer(final Map<Integer, Integer> currencies) {
        this.currencies = currencies;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ActivityPointsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(currencies.size());

        for (Map.Entry<Integer, Integer> currency : currencies.entrySet()) {
            msg.writeInt(currency.getKey());
            msg.writeInt(currency.getValue());
        }
    }
}
