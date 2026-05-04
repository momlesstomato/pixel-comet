package com.cometproject.api.config.network;

import java.util.Map;

/**
 * Declares networking configuration keys and defaults.
 */
public final class NetworkConfiguration {
    public static final String HOST = "comet.network.host";
    public static final String PORT = "comet.network.port";
    public static final String BACKLOG = "comet.network.backlog";
    public static final String EPOLL = "comet.network.epoll";
    public static final String ACCEPT_GROUP_THREADS = "comet.network.acceptGroupThreads";
    public static final String IO_GROUP_THREADS = "comet.network.ioGroupThreads";
    public static final String CHANNEL_GROUP_THREADS = "comet.network.channelGroupThreads";
    public static final String IDLE_TIMER_ENABLED = "comet.network.idleTimer.enabled";
    public static final String IDLE_TIMER_READER_IDLE_TIME = "comet.network.idleTimer.readerIdleTime";
    public static final String IDLE_TIMER_WRITER_IDLE_TIME = "comet.network.idleTimer.writerIdleTime";
    public static final String IDLE_TIMER_ALL_IDLE_TIME = "comet.network.idleTimer.allIdleTime";
    public static final String ALTERNATIVE_PACKET_HANDLING_ENABLED = "comet.network.alternativePacketHandling.enabled";
    public static final String ALTERNATIVE_PACKET_HANDLING_TYPE = "comet.network.alternativePacketHandling.type";
    public static final String ALTERNATIVE_PACKET_HANDLING_CORE_SIZE = "comet.network.alternativePacketHandling.coreSize";
    public static final String ALTERNATIVE_PACKET_HANDLING_MAX_SIZE = "comet.network.alternativePacketHandling.maxSize";
    public static final String MAX_PACKET_SIZE = "comet.network.maxPacketSize";

    private NetworkConfiguration() {
    }

    /**
     * Returns the default values for the networking configuration group.
     *
     * @return The default networking values.
     */
    public static Map<String, String> defaults() {
        return Map.ofEntries(
                Map.entry(HOST, "0.0.0.0"),
                Map.entry(PORT, "2096"),
                Map.entry(BACKLOG, "1500"),
                Map.entry(EPOLL, "false"),
                Map.entry(ACCEPT_GROUP_THREADS, "2"),
                Map.entry(IO_GROUP_THREADS, "2"),
                Map.entry(CHANNEL_GROUP_THREADS, "4"),
                Map.entry(IDLE_TIMER_ENABLED, "false"),
                Map.entry(IDLE_TIMER_READER_IDLE_TIME, "60"),
                Map.entry(IDLE_TIMER_WRITER_IDLE_TIME, "30"),
                Map.entry(IDLE_TIMER_ALL_IDLE_TIME, "0"),
                Map.entry(ALTERNATIVE_PACKET_HANDLING_ENABLED, "false"),
                Map.entry(ALTERNATIVE_PACKET_HANDLING_TYPE, "threadpool"),
                Map.entry(ALTERNATIVE_PACKET_HANDLING_CORE_SIZE, "8"),
                Map.entry(ALTERNATIVE_PACKET_HANDLING_MAX_SIZE, "32"),
                Map.entry(MAX_PACKET_SIZE, "500000")
        );
    }
}