package com.cometproject.server.protocol.messages;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.api.networking.messages.IMessageComposer;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serializes the value message for the Pixel Protocol client.
 */
public abstract class MessageComposer implements IMessageComposer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageComposer.class);

    /**
     * Creates a message composer instance for the protocol subsystem.
     */
    public MessageComposer() {
    }

    /**
     * Executes the write message operation for this protocol contract.
     *
     * @param buf Buf value supplied by the caller.
     * @return Result produced by the operation.
     */
    public final IComposer writeMessage(ByteBuf buf) {
        return this.writeMessageImpl(buf);
    }

    /**
     * Executes the write message impl operation for this protocol contract.
     *
     * @param buffer Buffer value supplied by the caller.
     * @return Result produced by the operation.
     */
    public final Composer writeMessageImpl(ByteBuf buffer) {
        final Composer composer = new Composer(this.getId(), buffer);

        // Do anything we need to do with the buffer.
        try {
            this.compose(composer);
        } catch (Exception e) {
            LOGGER.error("Error composing message " + this.getId() + " / " + this.getClass().getSimpleName(), e);
            throw e;
        } finally {
            this.dispose();
        }

        return composer;
    }

    /**
     * Returns the id associated with this protocol contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public abstract short getId();

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Msg value supplied by the caller.
     */
    public abstract void compose(IComposer msg);

    /**
     * Executes the dispose operation for this protocol contract.
     */
    public void dispose() {

    }
}
