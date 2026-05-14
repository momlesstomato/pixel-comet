package com.cometproject.server.game.rooms.types;

import com.cometproject.server.boot.Comet;


/**
 * Describes room promotion behavior for the room subsystem.
 */
public class RoomPromotion {
    public static final int DEFAULT_PROMO_LENGTH = 120;

    private int roomId;

    private String promotionName;
    private String promotionDescription;

    private long timestampStart;
    private long timestampFinish;

    /**
     * Creates a room promotion instance for the room subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     */
    public RoomPromotion(int roomId, String name, String description) {
        this.roomId = roomId;
        this.promotionName = name;
        this.promotionDescription = description;

        this.timestampStart = Comet.getTime();
        this.timestampFinish = this.timestampStart + (DEFAULT_PROMO_LENGTH * 60);
    }

    /**
     * Creates a room promotion instance for the room subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     * @param start Start supplied by the caller.
     * @param finish Finish supplied by the caller.
     */
    public RoomPromotion(int roomId, String name, String description, long start, long finish) {
        this.roomId = roomId;
        this.promotionName = name;
        this.promotionDescription = description;
        this.timestampStart = start;
        this.timestampFinish = finish;
    }

    /**
     * Returns the room id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Updates the room id for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the promotion name for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPromotionName() {
        return this.promotionName;
    }

    /**
     * Updates the promotion name for this room contract.
     *
     * @param promotionName Promotion name supplied by the caller.
     */
    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    /**
     * Returns the promotion description for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPromotionDescription() {
        return this.promotionDescription;
    }

    /**
     * Updates the promotion description for this room contract.
     *
     * @param promotionDescription Promotion description supplied by the caller.
     */
    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    /**
     * Indicates whether expired applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isExpired() {
        return Comet.getTime() >= this.timestampFinish;
    }

    /**
     * Returns the timestamp start for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getTimestampStart() {
        return this.timestampStart;
    }

    /**
     * Updates the timestamp start for this room contract.
     *
     * @param timestampStart Timestamp start supplied by the caller.
     */
    public void setTimestampStart(long timestampStart) {
        this.timestampStart = timestampStart;
    }

    /**
     * Returns the timestamp finish for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getTimestampFinish() {
        return this.timestampFinish;
    }

    /**
     * Updates the timestamp finish for this room contract.
     *
     * @param timestampFinish Timestamp finish supplied by the caller.
     */
    public void setTimestampFinish(long timestampFinish) {
        this.timestampFinish = timestampFinish;
    }

    /**
     * Executes minutes left for this room contract.
     *
     * @return Result produced by the operation.
     */
    public int minutesLeft() {
        return (int) Math.floor(this.getTimestampFinish() - Comet.getTime()) / 60;
    }
}
