package com.cometproject.server.game.rooms.objects.items.data;

import java.util.List;


/**
 * Carries moodlight data data for the room subsystem.
 */
public class MoodlightData {
    private boolean enabled;
    private int activePreset;
    private List<MoodlightPresetData> presets;

    /**
     * Creates a moodlight data instance for the room subsystem.
     *
     * @param enabled Enabled supplied by the caller.
     * @param activePreset Active preset supplied by the caller.
     * @param presets Presets supplied by the caller.
     */
    public MoodlightData(boolean enabled, int activePreset, List<MoodlightPresetData> presets) {
        this.enabled = enabled;
        this.activePreset = activePreset;
        this.presets = presets;
    }

    /**
     * Updates preset for this room contract.
     *
     * @param presetIndex Preset index supplied by the caller.
     * @param bgOnly Bg only supplied by the caller.
     * @param color Color supplied by the caller.
     * @param intensity Intensity supplied by the caller.
     */
    public void updatePreset(int presetIndex, boolean bgOnly, String color, int intensity) {
        if (presets.get(presetIndex) == null) {
            return;
        }
        MoodlightPresetData data = presets.get(presetIndex);

        data.backgroundOnly = bgOnly;
        data.colour = color;
        data.intensity = intensity;
    }

    /**
     * Returns the presets for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<MoodlightPresetData> getPresets() {
        return presets;
    }

    /**
     * Indicates whether enabled applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Updates the enabled for this room contract.
     *
     * @param enabled Enabled supplied by the caller.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the active preset for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getActivePreset() {
        return activePreset;
    }

    /**
     * Updates the active preset for this room contract.
     *
     * @param activePreset Active preset supplied by the caller.
     */
    public void setActivePreset(int activePreset) {
        this.activePreset = activePreset;
    }
}
