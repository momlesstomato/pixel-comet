package com.cometproject.server.network.websockets.packets.outgoing.roleplay;

/**
 * Describes rp weapon sync web packet behavior for the networking subsystem.
 */
public class RPWeaponSyncWebPacket {
    private String handle;
    private String weapon1;
    private String weapon2;
    private String weapon3;
    private String bandage;
    private String currentSlot;

    /**
     * Creates a rp weapon sync web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param weapon1 Weapon1 supplied by the caller.
     * @param weapon2 Weapon2 supplied by the caller.
     * @param weapon3 Weapon3 supplied by the caller.
     * @param bandage Bandage supplied by the caller.
     * @param currentSlot Current slot supplied by the caller.
     */
    public RPWeaponSyncWebPacket(String handle, String weapon1, String weapon2, String weapon3, String bandage, String currentSlot) {
        this.handle = handle;
        this.weapon1 = weapon1;
        this.weapon2 = weapon2;
        this.weapon3 = weapon3;
        this.bandage = bandage;
        this.currentSlot = currentSlot;
    }
}