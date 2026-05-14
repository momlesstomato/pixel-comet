package com.cometproject.networking.api.config;

import java.util.Set;

/**
 * Describes networking server config behavior for the networking subsystem.
 */
public class NetworkingServerConfig {

    private final String host;
    private final Set<Short> ports;
    private final boolean shouldEncrypt;

    /**
     * Creates a networking server config instance for the configuration subsystem.
     *
     * @param host Host value supplied by the caller.
     * @param ports Ports value supplied by the caller.
     * @param shouldEncrypt Should encrypt value supplied by the caller.
     */
    public NetworkingServerConfig(String host, Set<Short> ports, boolean shouldEncrypt) {
        this.host = host;
        this.ports = ports;
        this.shouldEncrypt = shouldEncrypt;
    }

    /**
     * Creates a networking server config instance for the configuration subsystem.
     *
     * @param host Host value supplied by the caller.
     * @param ports Ports value supplied by the caller.
     */
    public NetworkingServerConfig(String host, Set<Short> ports) {
        this(host, ports, true);
    }

    /**
     * Returns the host for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the ports for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Short> getPorts() {
        return ports;
    }

    /**
     * Executes the should encrypt operation for this configuration contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    public boolean shouldEncrypt() {
        return this.shouldEncrypt;
    }
}
