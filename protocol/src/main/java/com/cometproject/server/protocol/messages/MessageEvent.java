package com.cometproject.server.protocol.messages;

import com.cometproject.api.networking.messages.IMessageEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Represents the message event published by the Comet subsystem.
 */
public final class MessageEvent implements IMessageEvent  {
    private final short id;
    private final ByteBuf buffer;

    /**
     * Creates a message event instance for the protocol subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param buf Buf value supplied by the caller.
     */
    public MessageEvent(short id, ByteBuf buf) {
        this.buffer = (buf != null) && (buf.readableBytes() > 0) ? buf : Unpooled.EMPTY_BUFFER;
        this.id = id;
    }

    /**
     * Reads the next short from the inbound protocol body.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short readShort() {
        return this.content().readShort();
    }

    /**
     * Reads the next integer from the inbound protocol body.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int readInt() {
        try {
            return this.content().readInt();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Reads the next boolean from the inbound protocol body.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean readBoolean() {
        return this.content().readByte() == 1;
    }

    /**
     * Reads the next string from the inbound protocol body.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String readString() {
        final int length = this.readShort();
        final byte[] data = new byte[length];
        this.content().readBytes(data);
        return new String(data);
    }

    /**
     * Executes read bytes for this Comet contract.
     *
     * @param length Length supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public byte[] readBytes(int length) {
        final byte[] bytes = new byte[length];
        this.content().readBytes(bytes);
        return bytes;
    }

    /**
     * Executes to raw bytes for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public byte[] toRawBytes() {
        byte[] complete = this.buffer.array();
        return Arrays.copyOfRange(complete, 6, complete.length);
    }

    /**
     * Executes the to string operation for this protocol contract.
     *
     * @return Result produced by the operation.
     */
    public String toString() {
        String body = this.content().toString((Charset.defaultCharset()));

        for (int i = 0; i < 13; i++) {
            body = body.replace(Character.toString((char) i), "[" + i + "]");
        }

        return body;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return this.id;
    }

    private ByteBuf content() {
        return this.buffer;
    }

    /**
     * Returns the length for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLength() {
        return buffer.readableBytes();
    }
}