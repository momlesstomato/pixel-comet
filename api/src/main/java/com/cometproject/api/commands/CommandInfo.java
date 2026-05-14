package com.cometproject.api.commands;

/**
 * Describes command info behavior for the Comet subsystem.
 */
public class CommandInfo {
    private final String description;
    private final String permission;

    /**
     * Creates a command info instance for the Comet subsystem.
     *
     * @param description Description value supplied by the caller.
     * @param permission Permission value supplied by the caller.
     */
    public CommandInfo(String description, String permission) {
        this.description = description;
        this.permission = permission;
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPermission() {
        return permission;
    }
}
