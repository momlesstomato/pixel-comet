package com.cometproject.server.game.players.login.queue;

import com.cometproject.server.network.sessions.Session;


/**
 * Describes player login queue entry behavior for the player subsystem.
 */
public class PlayerLoginQueueEntry {
    private Session connectingClient;

    private int playerId;
    private String ssoTicket;

    /**
     * Creates a player login queue entry instance for the player subsystem.
     *
     * @param client Client supplied by the caller.
     * @param id Id supplied by the caller.
     * @param sso Sso supplied by the caller.
     */
    public PlayerLoginQueueEntry(Session client, int id, String sso) {
        this.connectingClient = client;

        this.playerId = id;
        this.ssoTicket = sso;
    }

    /**
     * Returns the client for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Session getClient() {
        return this.connectingClient;
    }

    /**
     * Returns the player id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the SSO ticket for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getSsoTicket() {
        return ssoTicket;
    }
}
