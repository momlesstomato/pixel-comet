package com.cometproject.server.game.moderation.chatlog;

import com.cometproject.server.logging.entries.RoomChatLogEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * Describes user chatlog container behavior for the moderation subsystem.
 */
public class UserChatlogContainer {
    //private Map<Integer, List<RoomChatLogEntry>> logs;
    private List<LogSet> logs;

    /**
     * Creates a user chatlog container instance for the moderation subsystem.
     */
    public UserChatlogContainer() {
        this.logs = new ArrayList<>();
    }

    /**
     * Adds all to this moderation contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param chatlogs Chatlogs supplied by the caller.
     */
    public void addAll(int roomId, List<RoomChatLogEntry> chatlogs) {
        if (chatlogs.size() < 1)
            return;

        this.logs.add(new LogSet(roomId, chatlogs));
    }

    /**
     * Releases resources owned by this moderation component.
     */
    public void dispose() {
        for (LogSet logSet : logs) {
            logSet.getLogs().clear();
        }

        this.logs.clear();
    }

    /**
     * Executes size for this moderation contract.
     *
     * @return Result produced by the operation.
     */
    public int size() {
        return logs.size();
    }

    /**
     * Returns the logs for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public List<LogSet> getLogs() {
        return this.logs;
    }

    /**
     * Describes log set behavior for the moderation subsystem.
     */
    public class LogSet {
        private int roomId;

        private List<RoomChatLogEntry> logs;

        /**
         * Executes log set for this moderation contract.
         *
         * @param roomId Room identifier used by the operation.
         * @param logs Logs supplied by the caller.
         */
        public LogSet(int roomId, List<RoomChatLogEntry> logs) {
            this.roomId = roomId;
            this.logs = logs;
        }

        /**
         * Returns the room id for this moderation contract.
         *
         * @return Value exposed by the contract.
         */
        public int getRoomId() {
            return roomId;
        }

        /**
         * Returns the logs for this moderation contract.
         *
         * @return Value exposed by the contract.
         */
        public List<RoomChatLogEntry> getLogs() {
            return logs;
        }
    }
}
