package com.cometproject.server.game.rooms.objects.items.data;

/**
 * Carries moodlight preset data data for the room subsystem.
 */
public class MoodlightPresetData {
    public boolean backgroundOnly;
    public String colour;
    public int intensity;

    /**
     * Creates a moodlight preset data instance for the room subsystem.
     *
     * @param backgroundOnly Background only supplied by the caller.
     * @param colour Colour supplied by the caller.
     * @param intensity Intensity supplied by the caller.
     */
    public MoodlightPresetData(boolean backgroundOnly, String colour, int intensity) {
        this.backgroundOnly = backgroundOnly;
        this.colour = colour;
        this.intensity = intensity;
    }
}
