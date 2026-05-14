package com.cometproject.server.game.players.components.types.messenger;

/**
 * Carries messenger friend data data for the player subsystem.
 */
public class MessengerFriendData {
    private String username;
    private String figure;
    private String motto;

    /**
     * Creates a messenger friend data instance for the player subsystem.
     *
     * @param username Username supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param motto Motto supplied by the caller.
     */
    public MessengerFriendData(String username, String figure, String motto) {
        this.username = username;
        this.figure = figure;
        this.motto = motto;
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
     * Updates the username for this player contract.
     *
     * @param username Username supplied by the caller.
     */
    public void setUsername(String username) {
        this.username = username;
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
     * Updates the figure for this player contract.
     *
     * @param figure Figure supplied by the caller.
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Returns the motto for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMotto() {
        return motto;
    }

    /**
     * Updates the motto for this player contract.
     *
     * @param motto Motto supplied by the caller.
     */
    public void setMotto(String motto) {
        this.motto = motto;
    }
}
