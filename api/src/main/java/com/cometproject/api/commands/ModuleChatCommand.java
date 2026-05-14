package com.cometproject.api.commands;

import com.cometproject.api.networking.sessions.ISession;

/**
 * Describes module chat command behavior for the Comet subsystem.
 */
public abstract class ModuleChatCommand {
    /**
     * Executes the execute operation for this Comet contract.
     *
     * @param client Client value supplied by the caller.
     * @param args Args value supplied by the caller.
     */
    public abstract void execute(ISession client, String[] args);

    /**
     * Returns the description associated with this Comet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public abstract String getDescription();

    /**
     * Returns the permission associated with this Comet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public abstract String getPermission();

    /**
     * Indicates whether hidden applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isHidden() {
        return false;
    }

    /**
     * Indicates whether async applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isAsync() {
        return false;
    }

    /**
     * Executes the merge operation for this Comet contract.
     *
     * @param params Params value supplied by the caller.
     * @param begin Begin value supplied by the caller.
     * @return Result produced by the operation.
     */
    public final String merge(String[] params, int begin) {
        final StringBuilder mergedParams = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            if (i >= begin) {
                mergedParams.append(params[i]).append(" ");
            }
        }

        return mergedParams.toString();
    }

    /**
     * Executes the merge operation for this Comet contract.
     *
     * @param params Params value supplied by the caller.
     * @return Result produced by the operation.
     */
    public final String merge(String[] params) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (String s : params) {
            if (!params[params.length - 1].equals(s))
                stringBuilder.append(s).append(" ");
            else
                stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }
}
