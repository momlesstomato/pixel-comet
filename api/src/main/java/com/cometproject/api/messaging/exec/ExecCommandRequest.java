package com.cometproject.api.messaging.exec;

public class ExecCommandRequest {
    private final String command;

    public ExecCommandRequest(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
