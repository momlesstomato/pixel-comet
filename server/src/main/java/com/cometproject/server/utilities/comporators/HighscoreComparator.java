package com.cometproject.server.utilities.comporators;

import com.cometproject.server.game.rooms.objects.items.types.floor.wired.data.ScoreboardItemData;

import java.util.Comparator;

/**
 * Describes highscore comparator behavior for the Comet subsystem.
 */
public class HighscoreComparator implements Comparator<ScoreboardItemData.HighscoreEntry> {
    /**
     * Executes compare for this Comet contract.
     *
     * @param o1 O1 supplied by the caller.
     * @param o2 O2 supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public int compare(ScoreboardItemData.HighscoreEntry o1, ScoreboardItemData.HighscoreEntry o2) {
        return o1.getScore() < o2.getScore() ? 1 : -1;
    }
}
