package com.cometproject.games.snowwar.items;

/**
 * Describes item behavior for the Snow War game subsystem.
 */
public abstract class Item {
    public static final int UPDATE = 1;
    public static final int MOVE = 2;
    public static final int INSERT = 3;
    public static final int DELETE = 4;
    public int refId;
    private int mysqlAction;
    public int itemId;
    public BaseItem baseItem;
    public ExtraDataBase extraData;
    /**
     * Indicates whether this Snow War game contract has h code.
     *
     * @return Result produced by the operation.
     */
    public int hashCode() {
        return this.itemId;
    }
}
