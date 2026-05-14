package com.cometproject.api.networking.messages;

import io.netty.buffer.ByteBuf;

/**
 * Defines the i message composer contract for the networking subsystem.
 */
public interface IMessageComposer {
    /**
     * Executes the write message operation for this networking contract.
     *
     * @param buffer Buffer value supplied by the caller.
     * @return Result produced by the operation.
     */
    IComposer writeMessage(ByteBuf buffer);

    /**
     * Returns the id associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    short getId();

    /**
     * Executes the compose operation for this networking contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void compose(IComposer msg);

    /**
     * Executes the dispose operation for this networking contract.
     */
    void dispose();
}
