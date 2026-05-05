package com.cometproject.networking.api.sessions;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;

public interface INetSession<T> {

    Connection getConnection();

    IMessageHandler getMessageHandler();

    T getGameSession();

}
