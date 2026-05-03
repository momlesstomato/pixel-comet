package com.cometproject.api.messaging.console;

public class ConsoleCommandRequest {
    private final String command;

    public ConsoleCommandRequest(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
