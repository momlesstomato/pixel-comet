package com.cometproject.games.snowwar.items;

import com.cometproject.api.networking.messages.IComposer;

/**
 * Describes extra data base behavior for the Snow War game subsystem.
 */
public abstract class ExtraDataBase {

    /**
     * Updates the extra data for this Snow War game contract.
     *
     * @param extraData Extra data supplied by the caller.
     */
    public void setExtraData(Object extraData) {}
    /**
     * Returns the wall legacy string for this Snow War game contract.
     *
     * @return Value exposed by the contract.
     */
    public String getWallLegacyString() {
/* 13 */     return "";
/*    */   }
    /**
     * Executes data for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public abstract byte[] data();
    /**
     * Returns the type for this Snow War game contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getType();
    /**
     * Executes serialize composer for this Snow War game contract.
     *
     * @param paramIComposer Param i composer supplied by the caller.
     */
    public abstract void serializeComposer(IComposer paramIComposer);
}