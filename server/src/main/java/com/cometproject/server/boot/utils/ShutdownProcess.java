package com.cometproject.server.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.LogManager;
import com.cometproject.server.logging.database.queries.LogQueries;
import com.cometproject.server.storage.queries.system.StatisticsDao;


public class ShutdownProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownProcess.class);

    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown(false);
            }
        });
    }

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
