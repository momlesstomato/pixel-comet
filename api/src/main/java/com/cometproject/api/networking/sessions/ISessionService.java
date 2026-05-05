package com.cometproject.api.networking.sessions;

import java.util.Map;
import java.util.Set;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.messages.IMessageComposer;

public interface ISessionService {
    boolean disconnectByPlayerId(int id);

    ISession getByPlayerId(int id);

    Set<ISession> getByPlayerPermission(String permission);

    ISession getByPlayerUsername(String username);

    int getUsersOnlineCount();

    Map<Integer, ISession> getSessions();

    void broadcast(IMessageComposer msg);

    void broadcastToModerators(IMessageComposer messageComposer);

    void parseCommand(String[] message, Connection connection);
}
