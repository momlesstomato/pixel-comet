//package com.cometproject.server.logging.old.types;

//
//import com.cometproject.server.logging.old.AbstractLogEntry;
//import com.cometproject.server.logging.old.LogType;


//
/**
 * Describes room chat log entry behavior for the logging subsystem.
 */
//public class RoomChatLogEntry extends AbstractLogEntry {
//
//    private int roomId;
//    private int userId;
//    private String message;
//    private String timestamp;
//
/**
 * Creates a room chat log entry instance for the Snow War game subsystem.
 *
 * @param roomId Room id supplied by the caller.
 * @param userId User id supplied by the caller.
 * @param message Message supplied by the caller.
 */
//    public RoomChatLogEntry(int roomId, int userId, String message) {
//        this.roomId = roomId;
//        this.userId = userId;
//        this.message = message;
//        this.timestamp = String.valueOf((int) (System.currentTimeMillis() / 1000L));
//    }
//
//    @Override
/**
 * Returns the type for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public LogType getType() {
//        return LogType.ROOM_CHATLOGS;
//    }
//
//    @Override
/**
 * Returns the string for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public String getString() {
//        return this.message;
//    }
//
//    @Override
/**
 * Returns the timestamp for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public String getTimestamp() {
//        return this.timestamp;
//    }
//
//    @Override
/**
 * Returns the room id for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public int getRoomId() {
//        return this.roomId;
//    }
//
//    @Override
/**
 * Returns the player id for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public int getPlayerId() {
//        return this.userId;
//    }
//}
