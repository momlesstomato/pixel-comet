package com.cometproject.tools;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.tools.logger.PacketLogger;
import com.cometproject.tools.packets.PacketManager;
import com.cometproject.tools.ui.CometWindow;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;


/**
 * Describes comet tools behavior for the tooling subsystem.
 */
public class CometTools {
    private PacketManager packetManager;
    private PacketLogger packetLogger;
    private CometWindow cometWindow;

    /**
     * Creates a comet tools instance for the tooling subsystem.
     */
    public CometTools() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.packetLogger = new PacketLogger(this);
        this.cometWindow = new CometWindow(this);

        System.out.println("CometTools was active for: " + (((double) stopwatch.elapsed(TimeUnit.MILLISECONDS)) / 1000) + " seconds");
    }

    private static CometTools instance;

    /**
     * Executes main for this tooling contract.
     *
     * @param args Args supplied by the caller.
     */
    public static void main(String[] args) {
        instance = new CometTools();
    }

    /**
     * Returns the instance for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public static CometTools getInstance() {
        return instance;
    }

    /**
     * Returns the packet manager for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public PacketManager getPacketManager() {
        return packetManager;
    }

    /**
     * Updates the packet manager for this tooling contract.
     *
     * @param packetManager Packet manager supplied by the caller.
     */
    public void setPacketManager(PacketManager packetManager) {
        this.packetManager = packetManager;
    }

    /**
     * Returns the packet logger for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public PacketLogger getPacketLogger() {
        return packetLogger;
    }
}
