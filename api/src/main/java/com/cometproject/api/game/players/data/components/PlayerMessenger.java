package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.players.data.components.messenger.IMessengerFriend;
import com.cometproject.api.networking.messages.IMessageComposer;

import java.util.List;
import java.util.Map;

/**
 * Defines the player messenger contract for the player subsystem.
 */
public interface PlayerMessenger {
    /**
     * Executes the search operation for this player contract.
     *
     * @param query Query value supplied by the caller.
     * @return Result produced by the operation.
     */
    IMessageComposer search(String query);

    /**
     * Adds request data to this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     */
    void addRequest(int playerId);

    /**
     * Adds friend data to this player contract.
     *
     * @param friend Friend value supplied by the caller.
     */
    void addFriend(IMessengerFriend friend);

    /**
     * Removes friend data from this player contract.
     *
     * @param userId User id value supplied by the caller.
     */
    void removeFriend(int userId);

    /**
     * Returns the request by sender associated with this player contract.
     *
     * @param sender Sender value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Integer getRequestBySender(int sender);

    /**
     * Executes the broadcast operation for this player contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void broadcast(IMessageComposer msg);

    /**
     * Executes the broadcast operation for this player contract.
     *
     * @param friends Friends value supplied by the caller.
     * @param msg Msg value supplied by the caller.
     */
    void broadcast(List<Integer> friends, IMessageComposer msg);

    /**
     * Indicates whether this player contract has request from.
     *
     * @param playerId Player id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasRequestFrom(int playerId);

    /**
     * Returns the request avatars associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<PlayerAvatar> getRequestAvatars();

    /**
     * Executes the clear requests operation for this player contract.
     */
    void clearRequests();

    /**
     * Executes the send offline operation for this player contract.
     *
     * @param friend Friend value supplied by the caller.
     * @param online Online value supplied by the caller.
     * @param inRoom In room value supplied by the caller.
     */
    void sendOffline(int friend, boolean online, boolean inRoom);

    /**
     * Executes the send status operation for this player contract.
     *
     * @param online Online value supplied by the caller.
     * @param inRoom In room value supplied by the caller.
     */
    void sendStatus(boolean online, boolean inRoom);

    /**
     * Returns the friend by id associated with this player contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IMessengerFriend getFriendById(int id);

    /**
     * Returns the friends associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IMessengerFriend> getFriends();

    /**
     * Returns the requests associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getRequests();

    /**
     * Removes request data from this player contract.
     *
     * @param request Request value supplied by the caller.
     */
    void removeRequest(Integer request);

    /**
     * Updates the initialised value for this player contract.
     *
     * @param initialised Initialised value supplied by the caller.
     */
    void setInitialised(boolean initialised);

    /**
     * Indicates whether initialised is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isInitialised();
}
