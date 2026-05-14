package com.cometproject.games.snowwar;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes message writer behavior for the Snow War game subsystem.
 */
public class MessageWriter {
    public List<Integer> savedPositions = new ArrayList<>();
    public boolean isReady;
    private boolean bytesReady;
    public int writer;

    private byte[] bytes;

    public int debugId;

    /**
     * Creates a message writer instance for the Snow War game subsystem.
     *
     * @param Size Size supplied by the caller.
     */
    public MessageWriter(final int Size) {
        bytes = new byte[Size];
    }

    /**
     * Creates a message writer instance for the Snow War game subsystem.
     */
    public MessageWriter() {
        this(1000); // default size
    }

    /**
     * Returns the message for this Snow War game contract.
     *
     * @return Value exposed by the contract.
     * @throws Exception When the operation cannot complete.
     */
    public byte[] getMessage() throws Exception {
        if (!isReady) {
            throw new Exception("Not ended MessageWriter!");
        }

        if (bytesReady) {
            return bytes;
        }

        final byte[] rtn = new byte[writer];
        for (int i = 0; i < writer; i++) {
            rtn[i] = bytes[i];
        }

        bytes = rtn;
        bytesReady = true;

        return rtn;
    }

    /**
     * Returns the message for this Snow War game contract.
     *
     * @param asdf Asdf supplied by the caller.
     * @return Value exposed by the contract.
     * @throws Exception When the operation cannot complete.
     */
    public ByteBuf getMessage(int asdf) throws Exception {
        if (!isReady) {
            throw new Exception("Not ended MessageWriter!");
        }

        if (bytesReady) {
            ByteBuf buf = Unpooled.buffer(bytes.length);
            buf.writeBytes(bytes);
            return buf;
        }

        final byte[] rtn = new byte[writer];
        if (writer >= 0) System.arraycopy(bytes, 0, rtn, 0, writer);

        bytes = rtn;
        bytesReady = true;
        ByteBuf buf = Unpooled.buffer(rtn.length);
        buf.writeBytes(rtn);
        return buf;
    }

    /**
     * Executes write int32 for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeInt32(int in) {
        bytes[writer++] = (byte) ((in >>> 24) & 0xFF);
        bytes[writer++] = (byte) ((in >>> 16) & 0xFF);
        bytes[writer++] = (byte) ((in >>> 8) & 0xFF);
        bytes[writer++] = (byte) ((in) & 0xFF);
    }

    /**
     * Executes write int16 for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeInt16(int in) {
        bytes[writer++] = (byte) ((in >>> 8) & 0xFF);
        bytes[writer++] = (byte) ((in) & 0xFF);
    }

    /**
     * Executes write boolean for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeBoolean(boolean in) {
        bytes[writer++] = in ? (byte) 1 : (byte) 0;
    }

    /**
     * Executes write char for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeChar(char in) {
        bytes[writer++] = (byte) (in & 0xFF);
    }

    /**
     * Executes write byte for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeByte(byte in) {
        bytes[writer++] = in;
    }

    /**
     * Executes write string for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeString(String in) {
        final int len = in.length();
        writeInt16(len);
        for (int i = 0; i < len; i++) {
            bytes[writer++] = (byte) (in.charAt(i) & 0xff);
        }
    }

    /**
     * Executes write bytes for this Snow War game contract.
     *
     * @param in Inbound byte buffer being decoded.
     */
    public void writeBytes(byte[] in) {
        final int len = in.length;
        writeInt16(len);
        for (byte b : in) {
            bytes[writer++] = b;
        }
    }

    /* Saving positions and writing later... */
    /**
     * Stores the current writer position for a value that will be written later.
     *
     * @param add Value returned to the caller after recording the position.
     * @return The supplied value so callers can inline position tracking.
     */
    public Object setSaved(final Object add) {
        savedPositions.add(writer);
        return add;
    }

    /**
     * Executes write saved for this Snow War game contract.
     *
     * @param add Add supplied by the caller.
     */
    public void writeSaved(final Object add) {
        if (add instanceof Integer) {
            final int tmp = writer;
            writer = savedPositions.remove(savedPositions.size() - 1);
            writeInt32((Integer) add);
            writer = tmp;
        } else if (add instanceof Boolean) {
            final int tmp = writer;
            writer = savedPositions.remove(savedPositions.size() - 1);
            writeBoolean((Boolean) add);
            writer = tmp;
        } else {
            throw new UnsupportedOperationException("Bad Param in SetWriter " + add.getClass());
        }
    }
}
