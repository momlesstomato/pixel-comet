package com.cometproject.games;

/**
 * Describes game base behavior for the Comet subsystem.
 */
public class GameBase {
    public boolean isEnabled;
    public final int gameId;
    public final String gameName;
    public final String bgColor;
    public final String textColor;
    public final String imagesPath;

    /**
     * Creates a game base instance for the Comet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param code Code supplied by the caller.
     * @param bgcolor Bgcolor supplied by the caller.
     * @param textcolor Textcolor supplied by the caller.
     * @param imagespath Imagespath supplied by the caller.
     */
    public GameBase(int id, String code, String bgcolor, String textcolor, String imagespath) {
        this.gameId = id;
        this.gameName = code;
        this.bgColor = bgcolor;
        this.textColor = textcolor;
        this.imagesPath = imagespath;

        GamesLeaderboard.leaderboards.put(this.gameId, new GamesLeaderboard(this.gameId));
    }
}