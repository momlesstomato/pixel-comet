package com.cometproject.api.game.players;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.utilities.Startable;

/**
 * Defines the i player service contract for the player subsystem.
 */
public interface IPlayerService extends Startable {
    /**
     * Executes the submit login request operation for this player contract.
     *
     * @param client Client value supplied by the caller.
     * @param ticket Ticket value supplied by the caller.
     */
    void submitLoginRequest(ISession client, String ticket);

    /**
     * Returns the avatar by player id associated with this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param mode Mode value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerAvatar getAvatarByPlayerId(int playerId, byte mode);

    /**
     * Returns the data by player id associated with this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayerData getDataByPlayerId(int playerId);

    /**
     * Returns the player count by IP address associated with this player contract.
     *
     * @param ipAddress Ip address value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPlayerCountByIpAddress(String ipAddress);

    /**
     * Executes the put operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param sessionId Session id value supplied by the caller.
     * @param username Username value supplied by the caller.
     * @param ipAddress Ip address value supplied by the caller.
     */
    void put(int playerId, int sessionId, String username, String ipAddress);

    /**
     * Executes the remove operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param username Username value supplied by the caller.
     * @param sessionId Session id value supplied by the caller.
     * @param ipAddress Ip address value supplied by the caller.
     */
    void remove(int playerId, String username, int sessionId, String ipAddress);

    /**
     * Returns the player id by username associated with this player contract.
     *
     * @param username Username value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPlayerIdByUsername(String username);

    /**
     * Returns the session id by player id associated with this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSessionIdByPlayerId(int playerId);

    /**
     * Updates username cache data for this player contract.
     *
     * @param oldName Old name value supplied by the caller.
     * @param newName New name value supplied by the caller.
     */
    void updateUsernameCache(String oldName, String newName);

    /**
     * Returns the player ids by IP address associated with this player contract.
     *
     * @param ipAddress Ip address value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getPlayerIdsByIpAddress(String ipAddress);

    /**
     * Indicates whether online is enabled for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isOnline(int playerId);

    /**
     * Indicates whether online is enabled for this player contract.
     *
     * @param username Username value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isOnline(String username);

    /**
     * Executes the size operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    int size();

    /**
     * Returns the player id by auth token associated with this player contract.
     *
     * @param authToken Auth token value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Integer getPlayerIdByAuthToken(final String authToken);

    /**
     * Creates auth token data for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param authToken Auth token value supplied by the caller.
     */
    void createAuthToken(int playerId, String authToken);

    /**
     * Returns the player load execution service associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ExecutorService getPlayerLoadExecutionService();
}
