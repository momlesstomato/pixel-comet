package com.cometproject.server.game.rooms.objects.items.data;

/**
 * Carries background toner data data for the room subsystem.
 */
public class BackgroundTonerData {
    private int hue;
    private int saturation;
    private int lightness;

    /**
     * Creates a background toner data instance for the room subsystem.
     *
     * @param hue Hue supplied by the caller.
     * @param saturation Saturation supplied by the caller.
     * @param lightness Lightness supplied by the caller.
     */
    public BackgroundTonerData(int hue, int saturation, int lightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.lightness = lightness;
    }

    /**
     * Executes get for this room contract.
     *
     * @param extradata Extradata supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static BackgroundTonerData get(String extradata) {
        if (!extradata.contains(";#;")) {
            return null;
        }

        String[] data = extradata.split(";#;");

        if (data.length < 3) {
            return null;
        }

        return new BackgroundTonerData(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    /**
     * Executes get for this room contract.
     *
     * @param data Data supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static String get(BackgroundTonerData data) {
        return data.getHue() + ";#;" + data.getSaturation() + ";#;" + data.getLightness();
    }

    /**
     * Returns the hue for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getHue() {
        return hue;
    }

    /**
     * Updates the hue for this room contract.
     *
     * @param hue Hue supplied by the caller.
     */
    public void setHue(int hue) {
        this.hue = hue;
    }

    /**
     * Returns the saturation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSaturation() {
        return saturation;
    }

    /**
     * Updates the saturation for this room contract.
     *
     * @param saturation Saturation supplied by the caller.
     */
    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    /**
     * Returns the lightness for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLightness() {
        return lightness;
    }

    /**
     * Updates the lightness for this room contract.
     *
     * @param lightness Lightness supplied by the caller.
     */
    public void setLightness(int lightness) {
        this.lightness = lightness;
    }
}
