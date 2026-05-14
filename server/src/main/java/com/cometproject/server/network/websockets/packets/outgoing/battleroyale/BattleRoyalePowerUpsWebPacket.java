package com.cometproject.server.network.websockets.packets.outgoing.battleroyale;

/**
 * Describes battle royale power ups web packet behavior for the networking subsystem.
 */
public class BattleRoyalePowerUpsWebPacket {
    private String handle;
    private String gun;
    private String sniper;
    private String bandage;

    /**
     * Creates a battle royale power ups web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param gun Gun supplied by the caller.
     * @param sniper Sniper supplied by the caller.
     * @param bandage Bandage supplied by the caller.
     */
    public BattleRoyalePowerUpsWebPacket(String handle, String gun, String sniper, String bandage) {
        this.handle = handle;
        this.gun = gun;
        this.sniper = sniper;
        this.bandage = bandage;
    }
}