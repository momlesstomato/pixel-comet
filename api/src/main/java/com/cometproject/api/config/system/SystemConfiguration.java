package com.cometproject.api.config.system;

import java.util.Map;

/**
 * Declares thread-pool and system configuration keys and defaults.
 */
public final class SystemConfiguration {
    public static final String THREADS = "comet.system.threads";
    public static final String TASK_ROOM_THREADS = "comet.system.taskRoomThreads";

    private SystemConfiguration() {
    }

    /**
     * Returns the default values for the system configuration group.
     *
     * @return The default system values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                THREADS, "16",
                TASK_ROOM_THREADS, "8"
        );
    }
}