package com.cometproject.server.network;

import com.cometproject.api.networking.transports.ConnectionTransport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class TransportManager {
    private final List<ConnectionTransport> transports = new ArrayList<>();

    void addTransport(final ConnectionTransport transport) {
        this.transports.add(transport);
    }

    void start() {
        for (ConnectionTransport transport : this.transports) {
            if (transport.isEnabled()) {
                transport.start();
            }
        }
    }

    void stop() {
        final List<ConnectionTransport> reverseOrder = new ArrayList<>(this.transports);
        Collections.reverse(reverseOrder);

        for (ConnectionTransport transport : reverseOrder) {
            transport.stop();
        }
    }
}