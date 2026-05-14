package com.cometproject.server.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.LogManager;
import com.cometproject.server.logging.database.queries.LogQueries;
import com.cometproject.server.storage.queries.system.StatisticsDao;


/**
 * Describes shutdown process behavior for the boot lifecycle subsystem.
 */
public class ShutdownProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownProcess.class);

    /**
     * Executes init for this boot lifecycle contract.
     */
    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            /**
             * Runs this boot lifecycle task.
             */
            @Override
            public void run() {
                shutdown(false);
            }
        });
    }

    /**
     * Executes shutdown for this boot lifecycle contract.
     *
     * @param exit Exit supplied by the caller.
     */
    public static void shutdown(boolean exit) {
        LOGGER.info("Comet is now shutting down");

        Comet.isRunning = false;

        LOGGER.info("Resetting statistics");
        StatisticsDao.saveStatistics(0, 0, Comet.getBuild());

        if (LogManager.ENABLED) {
            LOGGER.info("Updating room entry data");
            LogQueries.updateRoomEntries();
        }

        LOGGER.info("Closing all database connections");

        if (Comet.getServer() != null) {
            Comet.getServer().stop();
        }

        GameContext.setCurrent(null);

        if (exit) {
            System.exit(0);
        }
    }
}
