package com.cometproject.api.config;

import com.cometproject.api.commands.CommandInfo;

import java.util.Map;

/**
 * Describes module config behavior for the configuration subsystem.
 */
public class ModuleConfig {
    private final String name;
    private final String version;
    private final String entryPoint;

    private final Map<String, CommandInfo> commands;

    /**
     * Creates a module config instance for the configuration subsystem.
     *
     * @param name Name value supplied by the caller.
     * @param version Version value supplied by the caller.
     * @param entryPoint Entry point value supplied by the caller.
     * @param commands Commands value supplied by the caller.
     */
    public ModuleConfig(String name, String version, String entryPoint, Map<String, CommandInfo> commands) {
        this.name = name;
        this.version = version;
        this.entryPoint = entryPoint;
        this.commands = commands;
    }

    /**
     * Returns the name for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the version for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns the entry point for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    public String getEntryPoint() {
        return entryPoint;
    }

    /**
     * Returns the commands for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, CommandInfo> getCommands() {
        return commands;
    }
}
