package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session error message for the Pixel Protocol client.
 */
public class GuideSessionErrorMessageComposer extends MessageComposer {
    public static final int SOMETHING_WRONG_REQUEST = 0;
    public static final int NO_HELPERS_AVAILABLE = 1;
    public static final int NO_GUARDIANS_AVAILABLE = 2;
    public static final int TEST_3 = 3;
    public static final int TEST_4 = 4;

    private final int errorCode;

    /**
     * Creates a guide session error message composer instance for the network message subsystem.
     *
     * @param errorCode Error code supplied by the caller.
     */
    public GuideSessionErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.errorCode);
    }
}