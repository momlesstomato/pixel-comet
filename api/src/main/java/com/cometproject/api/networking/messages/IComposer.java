package com.cometproject.api.networking.messages;

/**
 * Defines the i composer contract for the networking subsystem.
 */
public interface IComposer {
    /**
     * Returns the id associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Executes the clear operation for this networking contract.
     */
    void clear();

    /**
     * Indicates whether finalised is enabled for this networking contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isFinalised();

    /**
     * Executes the write string operation for this networking contract.
     *
     * @param obj Obj value supplied by the caller.
     */
    void writeString(Object obj);

    /**
     * Executes the write double operation for this networking contract.
     *
     * @param d D value supplied by the caller.
     */
    void writeDouble(double d);

    /**
     * Executes the write int operation for this networking contract.
     *
     * @param i I value supplied by the caller.
     */
    void writeInt(int i);

    /**
     * Executes the write long operation for this networking contract.
     *
     * @param i I value supplied by the caller.
     */
    void writeLong(long i);

    /**
     * Executes the write boolean operation for this networking contract.
     *
     * @param b B value supplied by the caller.
     */
    void writeBoolean(Boolean b);

    /**
     * Executes the write byte operation for this networking contract.
     *
     * @param b B value supplied by the caller.
     */
    void writeByte(int b);

    /**
     * Executes the write short operation for this networking contract.
     *
     * @param s S value supplied by the caller.
     */
    void writeShort(int s);
}
