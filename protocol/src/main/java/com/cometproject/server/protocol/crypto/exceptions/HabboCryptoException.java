package com.cometproject.server.protocol.crypto.exceptions;

/**
 * Describes habbo crypto exception behavior for the protocol crypto subsystem.
 */
public class HabboCryptoException extends Exception {

    /**
     * Creates a habbo crypto exception instance for the protocol subsystem.
     *
     * @param message Message value supplied by the caller.
     */
    public HabboCryptoException(String message) {
        super(message);
    }

    /**
     * Creates a habbo crypto exception instance for the protocol subsystem.
     *
     * @param message Message value supplied by the caller.
     * @param cause Cause value supplied by the caller.
     */
    public HabboCryptoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a habbo crypto exception instance for the protocol subsystem.
     *
     * @param cause Cause value supplied by the caller.
     */
    public HabboCryptoException(Throwable cause) {
        super(cause);
    }

}