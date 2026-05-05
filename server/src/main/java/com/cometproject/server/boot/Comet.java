package com.cometproject.server.boot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.cometproject.api.stats.CometStats;
import com.cometproject.server.boot.utils.ConsoleCommands;
import com.cometproject.server.boot.utils.ShutdownProcess;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.utilities.CometRuntime;
import com.cometproject.server.utilities.TimeSpan;


public class Comet {
    public static String instanceId = UUID.randomUUID().toString();
    /**
     * The time the server was started
     */
    public static long start;
    /**
     * Is a debugger attached?
     */
    public static volatile boolean isDebugging = false;
    /**
     * Is Comet running?
     */
    public static volatile boolean isRunning = true;
    /**
     * Whether or not we want to show the GUI
     */
    public static boolean showGui = false;
    /**
     * Whether we're running Comet in daemon mode or not
     */
    public static boolean daemon = false;
    /**
     * Logging during start-up & console commands
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Comet.class);
    /**
     * The main server instance
     */
    private static CometServer server;

    /**
     * Start the server!
     *
     * @param args The arguments passed from the run command
     */
    public static void run(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        start = System.currentTimeMillis();

        LOGGER.info("Comet Server - " + getBuild());

        if (isDebugging) {
            LOGGER.debug("Debugging enabled.");
        }

        server = new CometServer(null);
        server.init();

        if (!daemon) {
            ConsoleCommands.init();
        }

        ShutdownProcess.init();
    }

    /**
     * Exit the comet server
     *
     * @param message The message to display to the console
     */
    public static void exit(String message) {
        LOGGER.error("Comet has shutdown. Reason: \"" + message + "\"");
        System.exit(0);
    }

    /**
     * Get the instance time in seconds
     *
     * @return The time in seconds
     */
    public static long getTime() {
        return (System.currentTimeMillis() / 1000L);
    }

    /**
     * Get the instance date [HH:MM:SS]
     *
     * @return The date
     */
    public static String getDate() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static Random getRandom() {
        return new Random();
    }

    /**
     * Get the instance build of Comet
     *
     * @return The instance build of Comet
     */
    public static String getBuild() {
        return Comet.class.getPackage().getImplementationVersion() == null ? "Comet-DEV" : Comet.class.getPackage().getImplementationVersion();
    }

    /**
     * Gets the instance server stats
     *
     * @return Server stats object
     */
    public static CometStats getStats() {
        CometStats statsInstance = new CometStats();

        statsInstance.setPlayers(NetworkManager.getInstance().getSessions().getUsersOnlineCount());
        statsInstance.setRooms(RoomManager.getInstance().getRoomInstances().size());
        statsInstance.setUptime(TimeSpan.millisecondsToDate(System.currentTimeMillis() - Comet.start));

        statsInstance.setProcessId(CometRuntime.processId);
        statsInstance.setAllocatedMemory((Runtime.getRuntime().totalMemory() / 1024) / 1024);
        statsInstance.setUsedMemory(statsInstance.getAllocatedMemory() - (Runtime.getRuntime().freeMemory() / 1024) / 1024);
        statsInstance.setOperatingSystem(CometRuntime.operatingSystem + " (" + CometRuntime.operatingSystemArchitecture + ")");
        statsInstance.setCpuCores(Runtime.getRuntime().availableProcessors());

        return statsInstance;
    }

    /**
     * Get the main server instance
     *
     * @return The main server instance
     */
    public static CometServer getServer() {
        return server;
    }
}
