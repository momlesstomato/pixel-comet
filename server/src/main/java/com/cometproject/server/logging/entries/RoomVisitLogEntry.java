package com.cometproject.server.logging.entries;

import com.cometproject.server.boot.Comet;


/**
 * Describes room visit log entry behavior for the logging subsystem.
 */
public class RoomVisitLogEntry {
    private int id;
    private int playerId;
    private int roomId;
    private int entryTime;
    private int exitTime;

    /**
     * Creates a room visit log entry instance for the logging subsystem.
     *
     * @param id Id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     * @param time Time supplied by the caller.
     */
    public RoomVisitLogEntry(int id, int playerId, int roomId, int time) {
        this.id = id;
        this.playerId = playerId;
        this.roomId = roomId;
        this.entryTime = time;
        this.exitTime = 0;
    }

    /**
     * Creates a room visit log entry instance for the logging subsystem.
     *
     * @param id Id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     * @param timeEnter Time enter supplied by the caller.
     * @param timeExit Time exit supplied by the caller.
     */
    public RoomVisitLogEntry(int id, int playerId, int roomId, int timeEnter, int timeExit) {
        this.id = id;
        this.playerId = playerId;
        this.roomId = roomId;
        this.entryTime = timeEnter;
        this.exitTime = timeExit == 0 ? (int) Comet.getTime() : timeExit;
    }

    /**
     * Returns the player id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Updates the player id for this logging contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the room id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Updates the room id for this logging contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the entry time for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEntryTime() {
        return entryTime;
    }

    /**
     * Updates the entry time for this logging contract.
     *
     * @param entryTime Entry time supplied by the caller.
     */
    public void setEntryTime(int entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * Returns the exit time for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getExitTime() {
        return exitTime;
    }

    /**
     * Updates the exit time for this logging contract.
     *
     * @param exitTime Exit time supplied by the caller.
     */
    public void setExitTime(int exitTime) {
        this.exitTime = exitTime;
    }

    /**
     * Returns the id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the id for this logging contract.
     *
     * @param id Id supplied by the caller.
     */
    public void setId(int id) {
        this.id = id;
    }
}
