package com.cometproject.api.config.game;

import java.util.Map;

/**
 * Declares game-specific configuration keys and defaults.
 */
public final class GameConfiguration {
    public static final String ROOMS_DATA_MAX = "comet.game.rooms.data.max";
    public static final String ROOMS_DATA_LOWER_WATERMARK = "comet.game.rooms.data.lowerWatermark";
    public static final String LOGGING_ENABLED = "comet.game.logging.enabled";
    public static final String RECIBIDOR_ID = "comet.recibidor.id";

    private GameConfiguration() {
    }

    /**
     * Returns the default values for the game configuration group.
     *
     * @return The default game values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                ROOMS_DATA_MAX, "5000",
                ROOMS_DATA_LOWER_WATERMARK, "0",
                LOGGING_ENABLED, "true",
                RECIBIDOR_ID, "7211"
        );
    }
}