package com.cometproject.networking.api.messages;

import com.cometproject.api.networking.messages.IMessageEvent;
import com.cometproject.api.networking.messages.IMessageEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Describes message event handler behavior for the networking subsystem.
 */
public abstract class MessageEventHandler<T extends MessageParser> implements IMessageEventHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(MessageEventHandler.class);

    private final short messageId;
    private final Consumer<T> parserConsumer;

    private T parserTypeField;
    private Class<T> parserType;

    /**
     * Creates a message event handler instance for the networking subsystem.
     *
     * @param messageId Message id value supplied by the caller.
     * @param parserConsumer Parser consumer value supplied by the caller.
     */
    public MessageEventHandler(short messageId, Consumer<T> parserConsumer) {
        this.messageId = messageId;
        this.parserConsumer = parserConsumer;

        try {
            // Naughty as fuck but it works.. lol
            this.parserTypeField = null;
            this.parserType = ((Class<T>)
                    this.getClass().getDeclaredField("parserTypeField").getType());
        } catch (Exception e) {
            LOGGER.error("Failed to get parser type for event: " + this.getClass().getName(), e);
        }
    }

    /**
     * Executes the handle operation for this networking contract.
     *
     * @param eventData Event data value supplied by the caller.
     * @throws Exception When the implementation cannot complete the operation.
     */
    public void handle(IMessageEvent eventData) throws Exception {
        final T parser = this.parserType.newInstance();

        parser.parse(eventData);

        this.parserConsumer.accept(parser);
    }

    /**
     * Returns the message id for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public short getMessageId() {
        return messageId;
    }
}
