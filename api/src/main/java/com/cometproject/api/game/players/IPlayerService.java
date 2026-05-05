package com.cometproject.api.game.players;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.utilities.Startable;

public interface IPlayerService extends Startable {
    void submitLoginRequest(ISession client, String ticket);

    PlayerAvatar getAvatarByPlayerId(int playerId, byte mode);

    IPlayerData getDataByPlayerId(int playerId);

    int getPlayerCountByIpAddress(String ipAddress);

    void put(int playerId, int sessionId, String username, String ipAddress);

    void remove(int playerId, String username, int sessionId, String ipAddress);

    int getPlayerIdByUsername(String username);

    int getSessionIdByPlayerId(int playerId);

    void updateUsernameCache(String oldName, String newName);

    List<Integer> getPlayerIdsByIpAddress(String ipAddress);

    boolean isOnline(int playerId);

    boolean isOnline(String username);

    int size();

    Integer getPlayerIdByAuthToken(final String authToken);

    void createAuthToken(int playerId, String authToken);

    ExecutorService getPlayerLoadExecutionService();
}
