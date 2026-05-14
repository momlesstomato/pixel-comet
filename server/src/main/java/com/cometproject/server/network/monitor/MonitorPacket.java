package com.cometproject.server.network.monitor;

/**
 * Describes monitor packet behavior for the networking subsystem.
 */
public class MonitorPacket {
    private String name;
    private String message;

    /**
     * Creates a monitor packet instance for the networking subsystem.
     *
     * @param name Name supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public MonitorPacket(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * Returns the name for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Updates the name for this networking contract.
     *
     * @param name Name supplied by the caller.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the message for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Updates the message for this networking contract.
     *
     * @param message Message supplied by the caller.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
