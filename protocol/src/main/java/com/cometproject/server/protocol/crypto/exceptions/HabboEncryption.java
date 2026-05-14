package com.cometproject.server.protocol.crypto.exceptions;

/**
 * Describes habbo encryption behavior for the protocol crypto subsystem.
 */
public class HabboEncryption {
    private final HabboRSACrypto crypto;
    private final HabboDiffieHellman diffie;

    /**
     * Creates a habbo encryption instance for the protocol subsystem.
     *
     * @param e E value supplied by the caller.
     * @param n N value supplied by the caller.
     * @param d D value supplied by the caller.
     */
    public HabboEncryption(String e, String n, String d) {
        this.crypto = new HabboRSACrypto(e, n, d);
        this.diffie = new HabboDiffieHellman(this.crypto);
    }

    /**
     * Returns the crypto for this protocol crypto contract.
     *
     * @return Value exposed by the contract.
     */
    public HabboRSACrypto getCrypto() {
        return crypto;
    }

    /**
     * Returns the diffie for this protocol crypto contract.
     *
     * @return Value exposed by the contract.
     */
    public HabboDiffieHellman getDiffie() {
        return diffie;
    }
}