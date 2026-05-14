package com.cometproject.api.game.players.data.components.messenger;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.sessions.ISession;
import com.google.gson.JsonObject;

/**
 * Defines the i messenger friend contract for the player subsystem.
 */
public interface IMessengerFriend {
    /**
     * Indicates whether in room is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isInRoom();

    /**
     * Returns the avatar associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerAvatar getAvatar();

    /**
     * Returns the user id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getUserId();

    /**
     * Indicates whether online is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isOnline();

    /**
     * Returns the session associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISession getSession();

    /**
     * Executes the to JSON operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    JsonObject toJson();
}
