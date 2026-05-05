package com.cometproject.api.networking.transports;

import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.utilities.Startable;

/**
 * Defines a lifecycle-managed transport capable of accepting client connections.
 */
public interface ConnectionTransport extends Startable {
    /**
     * Returns the transport type managed by this transport.
     *
     * @return The transport type identifier.
     */
    ConnectionTransportType getType();

    /**
     * Returns whether the transport is enabled in the current configuration.
     *
     * @return True when the transport should be started.
     */
    boolean isEnabled();
}