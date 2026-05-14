package com.cometproject.api.messaging.exec;

/**
 * Describes exec command request behavior for the Comet subsystem.
 */
public class ExecCommandRequest {
    private final String command;

    /**
     * Creates a exec command request instance for the messaging subsystem.
     *
     * @param command Command value supplied by the caller.
     */
    public ExecCommandRequest(String command) {
        this.command = command;
    }

    /**
     * Returns the command for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCommand() {
        return command;
    }
}
