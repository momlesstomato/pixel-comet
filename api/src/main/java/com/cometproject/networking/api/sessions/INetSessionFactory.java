package com.cometproject.networking.api.sessions;

import com.cometproject.api.networking.connections.Connection;

public interface INetSessionFactory {

    INetSession createSession(Connection connection);

    void disposeSession(INetSession session);

}
