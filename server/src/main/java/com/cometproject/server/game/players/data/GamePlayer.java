package com.cometproject.server.game.players.data;

/**
 * Describes game player behavior for the player subsystem.
 */
public class GamePlayer {

    private int id;
    private String username;
    private String figure;
    private String gender;
    private int points;
    private int lastPoints;

    /**
     * Creates a game player instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param points Points supplied by the caller.
     * @param lastPoints Last points supplied by the caller.
     */
    public GamePlayer(int id, String username, String figure, String gender, int points, int lastPoints) {
        this.id = id;
        this.username = username;
        this.figure = figure;
        this.gender = gender;
        this.points = points;
        this.lastPoints = lastPoints;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the points for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the last points for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastPoints() {
        return lastPoints;
    }

    /**
     * Returns the username for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the figure for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getFigure() {
        return figure;
    }

    /**
     * Returns the gender for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGender() {
        return gender;
    }
}
