package com.cometproject.tools.logger;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.tools.CometTools;
import com.cometproject.tools.logger.proxy.Proxy;


/**
 * Describes packet logger behavior for the tooling subsystem.
 */
public class PacketLogger {
    private CometTools tools;
    private boolean isActive = false;

    /**
     * Creates a packet logger instance for the tooling subsystem.
     *
     * @param tools Tools supplied by the caller.
     */
    public PacketLogger(CometTools tools) {
        this.isActive = false;
    }

    /**
     * Starts this tooling component.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean start() {
        try {
            // Initiate the Proxy
            Proxy proxy = new Proxy();
            proxy.setHost("66.132.225.13");
            proxy.setPort(38101);
            proxy.setListeningIP("127.0.0.1");
            proxy.setListeningPort(38101);
            proxy.setDebug(true);

            // Initiate the proxy2
            Proxy proxy2 = new Proxy();
            proxy2.setHost("66.132.225.13");
            proxy2.setPort(993);
            proxy2.setListeningIP("127.0.0.1");
            proxy2.setListeningPort(993);
            proxy2.setDebug(true);

            // Start the proxy.
            proxy.start();

            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Stops this tooling component.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean stop() {
        try {
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Returns the is active for this tooling contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getIsActive() {
        return this.isActive;
    }
}
