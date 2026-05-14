package com.cometproject.server.network.websockets.packets.outgoing;

/**
 * Describes survival sound effect web packet behavior for the networking subsystem.
 */
public class SurvivalSoundEffectWebPacket {
    private String handle;
    private String sound;

    /**
     * Creates a survival sound effect web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param sound Sound supplied by the caller.
     */
    public SurvivalSoundEffectWebPacket(String handle, String sound) {
        this.handle = handle;
        this.sound = sound;
    }
}
