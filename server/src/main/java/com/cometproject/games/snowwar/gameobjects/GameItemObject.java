package com.cometproject.games.snowwar.gameobjects;

import com.cometproject.games.snowwar.*;
import com.cometproject.games.snowwar.items.BaseItem;

/**
 * Describes game item object behavior for the Snow War game subsystem.
 */
public abstract class GameItemObject {
    public static final int SNOWBALL = 1;
    public static final int TREE = 2;
    public static final int PILE = 3;
    public static final int MACHINE = 4;
    public static final int HUMAN = 5;
    public BaseItem BaseItem;
    public Object Data;
    public int objectId;
    public boolean _active = false;
    public final int variablesCount;

    /**
     * Executes location3 d for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public abstract PlayerTile location3D();

    /**
     * Executes direction360 for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public abstract Direction360 direction360();

    /**
     * Returns the variable for this Snow War game contract.
     *
     * @param paramInt Param int supplied by the caller.
     * @return Value exposed by the contract.
     */
    public abstract int getVariable(int paramInt);

    /**
     * Executes bounding data for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public abstract int[] boundingData();

    /**
     * Handles the remove callback for this Snow War game contract.
     */
    public void onRemove() {
    }

    /**
     * Executes subturn for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     */
    public void subturn(SynchronizedGameStage _arg1) {
    }

    /**
     * Handles the snow ball hit callback for this Snow War game contract.
     *
     * @param _arg2 Arg2 supplied by the caller.
     */
    public void onSnowBallHit(SnowBallGameObject _arg2) {
    }

    /**
     * Creates a game item object instance for the Snow War game subsystem.
     *
     * @param varCount Var count supplied by the caller.
     */
    protected GameItemObject(int varCount) {
        this.variablesCount = varCount;
    }

    /**
     * Executes generate checksum for this Snow War game contract.
     *
     * @param arena Arena supplied by the caller.
     * @param multipler Multipler supplied by the caller.
     */
    public void GenerateCHECKSUM(SnowWarRoom arena, int multipler) {
        for (int i = 0; i < this.variablesCount; ) {
            arena.checksum += (getVariable(i) * ++i) * multipler;
        }
    }

    /**
     * Executes 3au for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public int _3au() {
        return -(this.objectId + 1);
    }

    /**
     * Executes collision height for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public int collisionHeight() {
        return boundingData()[0];
    }

    /**
     * Executes test snow ball collision for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean testSnowBallCollision(SnowBallGameObject _arg1) {
        return (_arg1.location3D().z() < collisionHeight() && CollisionDetection._pU(this, _arg1));
    }
}