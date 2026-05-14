package com.cometproject.server.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Describes comet runtime behavior for the Comet subsystem.
 */
public class CometRuntime {
    public static final String operatingSystem = System.getProperty("os.name");
    public static final String operatingSystemArchitecture = System.getProperty("os.arch");
    private static final Logger LOGGER = LoggerFactory.getLogger(CometRuntime.class.getName());
    public static int processId = 0;

    static {
        processId = Math.toIntExact(ProcessHandle.current().pid());

        if (processId < 1)
            LOGGER.warn("Failed to get process identifier - OS: " + operatingSystem + " (" + operatingSystemArchitecture + ")");
    }
}
