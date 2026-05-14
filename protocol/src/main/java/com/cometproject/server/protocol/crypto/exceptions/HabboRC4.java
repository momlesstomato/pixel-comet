package com.cometproject.server.protocol.crypto.exceptions;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Describes habbo RC4 behavior for the protocol crypto subsystem.
 */
public class HabboRC4 {
    private static final int POOL_SIZE = 256;
    private int i = 0;
    private int j = 0;
    private final int[] table;

    /**
     * Creates a habbo rc4 instance for the protocol subsystem.
     *
     * @param key Key value supplied by the caller.
     */
    public HabboRC4(byte[] key) {
        this.table = new int[POOL_SIZE];

        this.init(key);
    }

    /**
     * Executes the init operation for this protocol contract.
     *
     * @param key Key value supplied by the caller.
     */
    public void init(byte[] key) {
        for (i = 0; i < POOL_SIZE; ++i) {
            this.table[i] = (byte) i;
        }

        this.i = 0;
        this.j = 0;

        for (i = 0; i < POOL_SIZE; ++i) {
            j = (j + table[i] + key[i % key.length]) & (POOL_SIZE - 1);
            swap(i, j);
        }

        this.i = 0;
        this.j = 0;
    }

    /**
     * Executes the swap operation for this protocol contract.
     *
     * @param a A value supplied by the caller.
     * @param b B value supplied by the caller.
     */
    public void swap(int a, int b) {
        int k = this.table[a];
        this.table[a] = this.table[b];
        this.table[b] = k;
    }

    /**
     * Executes the next operation for this protocol contract.
     *
     * @return Result produced by the operation.
     */
    public byte next() {
        i = ++i & (POOL_SIZE - 1);
        j = (j + table[i]) & (POOL_SIZE - 1);
        swap(i, j);

        return (byte) table[(table[i] + table[j]) & (POOL_SIZE - 1)];
    }

    /**
     * Executes the decipher operation for this protocol contract.
     *
     * @param bytes Bytes value supplied by the caller.
     * @return Result produced by the operation.
     */
    public ByteBuf decipher(ByteBuf bytes) {
        ByteBuf result = Unpooled.buffer();

        while (bytes.isReadable())
            result.writeByte((byte) (bytes.readByte() ^ next()));

        return result;
    }
}