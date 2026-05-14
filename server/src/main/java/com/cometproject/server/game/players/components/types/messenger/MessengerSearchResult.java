package com.cometproject.server.game.players.components.types.messenger;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.PlayerManager;


/**
 * Describes messenger search result behavior for the player subsystem.
 */
public class MessengerSearchResult {
    private int id;
    private String username;
    private String figure;
    private String motto;
    private String lastOnline;

    /**
     * Creates a messenger search result instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param motto Motto supplied by the caller.
     * @param lastOnline Last online supplied by the caller.
     */
    public MessengerSearchResult(int id, String username, String figure, String motto, String lastOnline) {
        this.id = id;
        this.username = username;
        this.figure = figure;
        this.motto = motto;
        this.lastOnline = lastOnline;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeInt(id);
        msg.writeString(username);
        msg.writeString(motto);
        msg.writeBoolean(PlayerManager.getInstance().isOnline(id)); // is online
        msg.writeBoolean(false);
        msg.writeString("");
        msg.writeInt(0);
        msg.writeString(figure);
        msg.writeString(lastOnline);
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
     * Returns the motto for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMotto() {
        return motto;
    }

    /**
     * Returns the last online for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getLastOnline() {
        return lastOnline;
    }
}
