package com.cometproject.api.networking.messages;

/**
 * Defines the i message event contract for the networking subsystem.
 */
public interface IMessageEvent {


    /**
     * Executes the read short operation for this networking contract.
     *
     * @return Result produced by the operation.
     */
    short readShort();

    /**
     * Executes the read int operation for this networking contract.
     *
     * @return Result produced by the operation.
     */
    int readInt();

    /**
     * Executes the read boolean operation for this networking contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean readBoolean();

    /**
     * Executes the read string operation for this networking contract.
     *
     * @return Result produced by the operation.
     */
    String readString();

    /**
     * Executes the read bytes operation for this networking contract.
     *
     * @param length Length value supplied by the caller.
     * @return Result produced by the operation.
     */
    byte[] readBytes(int length);

    /**
     * Executes the to raw bytes operation for this networking contract.
     *
     * @return Result produced by the operation.
     */
    byte[] toRawBytes();

    /**
     * Returns the id associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    short getId();

    /**
     * Returns the length associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLength();
}
