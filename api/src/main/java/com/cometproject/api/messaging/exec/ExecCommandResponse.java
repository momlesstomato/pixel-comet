package com.cometproject.api.messaging.exec;

public class ExecCommandResponse {

    private final String output;

    public ExecCommandResponse(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
