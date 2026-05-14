package com.cometproject.api.messaging.console;

/**
 * Describes console command request behavior for the Comet subsystem.
 */
public class ConsoleCommandRequest {
    private final String command;

    /**
     * Creates a console command request instance for the messaging subsystem.
     *
     * @param command Command value supplied by the caller.
     */
    public ConsoleCommandRequest(String command) {
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
