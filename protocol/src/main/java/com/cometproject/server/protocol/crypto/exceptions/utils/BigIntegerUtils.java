package com.cometproject.server.protocol.crypto.exceptions.utils;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Describes big integer utils behavior for the protocol crypto subsystem.
 */
public class BigIntegerUtils {

    /**
     * Executes the to unsigned byte array operation for this protocol contract.
     *
     * @param bigInteger Big integer value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static byte[] toUnsignedByteArray(BigInteger bigInteger) {
        byte[] bytes = bigInteger.toByteArray();
        if (bytes[0] == 0) {
            bytes = Arrays.copyOfRange(bytes, 1, bytes.length);
        }

        return bytes;
    }

}