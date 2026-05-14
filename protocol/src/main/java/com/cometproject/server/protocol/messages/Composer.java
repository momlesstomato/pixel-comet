package com.cometproject.server.protocol.messages;

import com.cometproject.api.networking.messages.IComposer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

import java.nio.charset.StandardCharsets;

/**
 * Describes composer behavior for the Comet subsystem.
 */
public class Composer implements IComposer, ByteBufHolder {
    protected final int id;
    protected final ByteBuf body;

    /**
     * Creates a composer instance for the protocol subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param body Body value supplied by the caller.
     */
    public Composer(short id, ByteBuf body) {
        this.id = id;
        this.body = body;

        try {
            this.body.writeInt(-1);
            this.body.writeShort(id);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    /**
     * Creates a composer instance for the protocol subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param body Body value supplied by the caller.
     */
    public Composer(int id, ByteBuf body) {
        this.id = id;
        this.body = body;
    }

    private void exceptionCaught(Exception e) {
        e.printStackTrace();
    }

    /**
     * Returns the underlying protocol buffer content.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ByteBuf content() {
        return this.body;
    }

    /**
     * Executes copy for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Composer copy() {
        return new Composer(this.id, this.body.copy());
    }

    /**
     * Executes duplicate for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Composer duplicate() {
        return new Composer(this.id, this.body.duplicate());
    }

    /**
     * Executes ref cnt for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int refCnt() {
        return this.body.refCnt();
    }

    /**
     * Executes retain for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Composer retain() {
        this.body.retain();
        return this;
    }

    /**
     * Executes retained duplicate for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Composer retainedDuplicate() {
        return new Composer(this.id, this.body.retainedDuplicate());
    }

    /**
     * Executes replace for this Comet contract.
     *
     * @param byteBuf Byte buf supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Composer replace(ByteBuf byteBuf) {
        return new Composer(this.id, byteBuf);
    }

    /**
     * Executes retain for this Comet contract.
     *
     * @param increment Increment supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Composer retain(int increment) {
        this.body.retain(increment);
        return this;
    }

    /**
     * Executes release for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean release() {
        return this.body.release();
    }

    /**
     * Executes release for this Comet contract.
     *
     * @param decrement Decrement supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean release(int decrement) {
        return this.body.release(decrement);
    }

    /**
     * Executes touch for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Composer touch() {
        this.body.touch();
        return this;
    }

    /**
     * Executes touch for this Comet contract.
     *
     * @param o O supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Composer touch(Object o) {
        this.body.touch(o);
        return this;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Executes clear for this Comet contract.
     */
    @Override
    public void clear() {
        this.body.clear();
    }

    /**
     * Indicates whether finalised applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isFinalised() {
        return (this.body.getInt(0) > -1);
    }

    /**
     * Executes write string for this Comet contract.
     *
     * @param obj Obj supplied by the caller.
     */
    @Override
    public void writeString(Object obj) {
        try {
            String string = "";

            if (obj != null) {
                string = String.valueOf(obj);
            }

            byte[] dat = string.getBytes(StandardCharsets.UTF_8);
            this.body.writeShort(dat.length);
            this.body.writeBytes(dat);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    /**
     * Executes write double for this Comet contract.
     *
     * @param d D supplied by the caller.
     */
    @Override
    public void writeDouble(double d) {
        this.writeString(Double.toString(d));
    }

    /**
     * Executes write int for this Comet contract.
     *
     * @param i I supplied by the caller.
     */
    @Override
    public void writeInt(int i) {
        try {
            this.body.writeInt(i);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    /**
     * Executes write long for this Comet contract.
     *
     * @param i I supplied by the caller.
     */
    @Override
    public void writeLong(long i) {
        try {
            this.body.writeLong(i);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    /**
     * Executes write boolean for this Comet contract.
     *
     * @param b B supplied by the caller.
     */
    @Override
    public void writeBoolean(Boolean b) {
        try {
            this.body.writeByte(b ? 1 : 0);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    /**
     * Executes write byte for this Comet contract.
     *
     * @param b B supplied by the caller.
     */
    @Override
    public void writeByte(int b) {
        try {
            this.body.writeByte(b);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    /**
     * Executes write short for this Comet contract.
     *
     * @param s S supplied by the caller.
     */
    @Override
    public void writeShort(int s) {
        try {
            this.body.writeShort((short) s);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }
}