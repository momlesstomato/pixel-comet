package com.cometproject.gamecenter.fastfood.net;

import com.cometproject.gamecenter.fastfood.FastFoodGame;
import com.cometproject.gamecenter.fastfood.objects.FoodPlate;

/**
 * Describes fast food game session behavior for the Fast Food game subsystem.
 */
public class FastFoodGameSession {

    private int playerId = -1;
    private String username;
    private String figure;
    private String gender;
    private String credits;
    private int gamesPlayed;
    private int parachutes = -1;
    private int missiles = 10;
    private int shields = 10;
    private int points;
    private FastFoodGame currentGame;
    private FoodPlate currentPlate;

    /**
     * Returns the player id for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Updates the player id for this Fast Food game contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the username for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username for this Fast Food game contract.
     *
     * @param username Username supplied by the caller.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the figure for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public String getFigure() {
        return figure;
    }

    /**
     * Updates the figure for this Fast Food game contract.
     *
     * @param figure Figure supplied by the caller.
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Returns the credits for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCredits() {
        return credits;
    }

    /**
     * Updates the credits for this Fast Food game contract.
     *
     * @param credits Credits supplied by the caller.
     */
    public void setCredits(String credits) {
        this.credits = credits;
    }

    /**
     * Returns the games played for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Updates the games played for this Fast Food game contract.
     *
     * @param gamesPlayed Games played supplied by the caller.
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Returns the parachutes for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getParachutes() {
        return parachutes;
    }

    /**
     * Updates the parachutes for this Fast Food game contract.
     *
     * @param parachutes Parachutes supplied by the caller.
     */
    public void setParachutes(int parachutes) {
        this.parachutes = parachutes;
    }

    /**
     * Returns the missiles for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMissiles() {
        return missiles;
    }

    /**
     * Updates the missiles for this Fast Food game contract.
     *
     * @param missiles Missiles supplied by the caller.
     */
    public void setMissiles(int missiles) {
        this.missiles = missiles;
    }

    /**
     * Returns the shields for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getShields() {
        return shields;
    }

    /**
     * Updates the shields for this Fast Food game contract.
     *
     * @param shields Shields supplied by the caller.
     */
    public void setShields(int shields) {
        this.shields = shields;
    }

    /**
     * Returns the points for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Updates the points for this Fast Food game contract.
     *
     * @param points Points supplied by the caller.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the current game for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public FastFoodGame getCurrentGame() {
        return currentGame;
    }

    /**
     * Updates the current game for this Fast Food game contract.
     *
     * @param currentGame Current game supplied by the caller.
     */
    public void setCurrentGame(FastFoodGame currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * Returns the gender for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Updates the gender for this Fast Food game contract.
     *
     * @param gender Gender supplied by the caller.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the current plate for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public FoodPlate getCurrentPlate() {
        return currentPlate;
    }

    /**
     * Updates the current plate for this Fast Food game contract.
     *
     * @param currentPlate Current plate supplied by the caller.
     */
    public void setCurrentPlate(FoodPlate currentPlate) {
        this.currentPlate = currentPlate;
    }
}
