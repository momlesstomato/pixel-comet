package com.cometproject.server.game.rooms.objects.items.types.floor.wired.data;

import com.cometproject.server.utilities.comporators.HighscoreComparator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Carries scoreboard item data data for the room subsystem.
 */
public class ScoreboardItemData {
    private final static HighscoreComparator comparator = new HighscoreComparator();
    private final List<HighscoreEntry> entries;
    private final Map<String, Integer> points;
    private int scoreType;
    private int clearType;

    /**
     * Creates a scoreboard item data instance for the room subsystem.
     *
     * @param scoreType Score type supplied by the caller.
     * @param clearType Clear type supplied by the caller.
     * @param entries Entries supplied by the caller.
     * @param points Points supplied by the caller.
     */
    public ScoreboardItemData(int scoreType, int clearType, List<HighscoreEntry> entries, Map<String, Integer> points) {
        this.scoreType = scoreType;
        this.clearType = clearType;
        this.entries = entries;

        this.points = points;
    }

    /**
     * Returns the entries for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<HighscoreEntry> getEntries() {
        return this.entries;
    }

    /**
     * Returns the points for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, Integer> getPoints() {
        return this.points;
    }

    /**
     * Adds entry to this room contract.
     *
     * @param users Users supplied by the caller.
     * @param score Score supplied by the caller.
     */
    public void addEntry(List<String> users, int score) {
        synchronized (this.entries) {
            this.entries.add(new HighscoreEntry(users, score));

            Collections.sort(this.entries, comparator);
        }
    }

    /**
     * Adds point to this room contract.
     *
     * @param identifier Identifier supplied by the caller.
     * @param score Score supplied by the caller.
     */
    public void addPoint(String identifier, int score) {
        if (!this.points.containsKey(identifier)) {
            this.points.put(identifier, score);
            return;
        }

        this.points.replace(identifier, this.points.get(identifier) + score);
    }

    /**
     * Returns the score type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getScoreType() {
        return scoreType;
    }

    /**
     * Updates the score type for this room contract.
     *
     * @param scoreType Score type supplied by the caller.
     */
    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    /**
     * Returns the clear type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getClearType() {
        return clearType;
    }

    /**
     * Updates the clear type for this room contract.
     *
     * @param clearType Clear type supplied by the caller.
     */
    public void setClearType(int clearType) {
        this.clearType = clearType;
    }

    /**
     * Describes highscore entry behavior for the room subsystem.
     */
    public class HighscoreEntry {
        private List<String> users;
        private int score;

        /**
         * Executes highscore entry for this room contract.
         *
         * @param users Users supplied by the caller.
         * @param score Score supplied by the caller.
         */
        public HighscoreEntry(List<String> users, int score) {
            this.users = users;
            this.score = score;
        }

        /**
         * Returns the users for this room contract.
         *
         * @return Value exposed by the contract.
         */
        public List<String> getUsers() {
            return users;
        }

        /**
         * Updates the users for this room contract.
         *
         * @param users Users supplied by the caller.
         */
        public void setUsers(List<String> users) {
            this.users = users;
        }

        /**
         * Returns the score for this room contract.
         *
         * @return Value exposed by the contract.
         */
        public int getScore() {
            return score;
        }

        /**
         * Updates the score for this room contract.
         *
         * @param score Score supplied by the caller.
         */
        public void setScore(int score) {
            this.score = score;
        }

        /**
         * Executes reset entries for this room contract.
         */
        public void resetEntries() { users.clear(); }
    }
}
