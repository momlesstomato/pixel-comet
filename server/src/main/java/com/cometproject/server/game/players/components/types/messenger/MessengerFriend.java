package com.cometproject.server.game.players.components.types.messenger;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.players.data.components.messenger.IMessengerFriend;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.data.PlayerAvatarData;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes messenger friend behavior for the player subsystem.
 */
public class MessengerFriend implements IMessengerFriend {
    private int userId;
    private PlayerAvatar playerAvatar;

    /**
     * Creates a messenger friend instance for the player subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public MessengerFriend(ResultSet data) throws SQLException {
        this.userId = data.getInt("user_two_id");
        this.playerAvatar = new PlayerAvatarData(this.userId, data.getString("username"), data.getString("figure"), data.getString("gender"), data.getString("motto"));
    }

    /**
     * Creates a messenger friend instance for the player subsystem.
     *
     * @param userId User id supplied by the caller.
     * @param playerAvatar Player avatar supplied by the caller.
     */
    public MessengerFriend(int userId, PlayerAvatar playerAvatar) {
        this.userId = userId;
        this.playerAvatar = playerAvatar;
    }

    /**
     * Indicates whether in room applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isInRoom() {
        if (!isOnline()) {
            return false;
        }

        Session client = NetworkManager.getInstance().getSessions().getByPlayerId(this.userId);

        // Could have these in 1 statement, but to make it easier to read - lets just leave it like this. :P
        if (client == null || client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return false;
        }

        return client.getPlayer().getEntity().isVisible();
    }

    /**
     * Returns the avatar for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PlayerAvatar getAvatar() {
        if (this.getSession() != null && this.getSession().getPlayer() != null) {
            return this.getSession().getPlayer().getData();
        }

        return this.playerAvatar;
    }

    /**
     * Returns the user id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getUserId() {
        return this.userId;
    }

    /**
     * Indicates whether online applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isOnline() {
        return PlayerManager.getInstance().isOnline(userId);
    }

    /**
     * Returns the session for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ISession getSession() {
        return NetworkManager.getInstance().getSessions().getByPlayerId(this.userId);
    }

    /**
     * Executes to JSON for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonObject toJson() {
        final JsonObject coreObject = new JsonObject();
        final JsonObject playerObject = new JsonObject();

        coreObject.addProperty("id", userId);
        coreObject.addProperty("inRoom", isInRoom());
        coreObject.addProperty("online", isOnline());

        playerObject.addProperty("username", playerAvatar.getUsername());
        playerObject.addProperty("figure", playerAvatar.getFigure());
        playerObject.addProperty("motto", playerAvatar.getMotto());
        playerObject.addProperty("gender", playerAvatar.getGender());

        coreObject.add("playerData", playerObject);

        return coreObject;
    }
}
