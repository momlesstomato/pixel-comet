package com.cometproject.api.messaging.exec;

/**
 * Describes exec command response behavior for the Comet subsystem.
 */
public class ExecCommandResponse {

    private final String output;

    /**
     * Creates a exec command response instance for the messaging subsystem.
     *
     * @param output Output value supplied by the caller.
     */
    public ExecCommandResponse(String output) {
        this.output = output;
    }

    /**
     * Returns the output for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getOutput() {
        return output;
    }
}
