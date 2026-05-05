package com.cometproject.api.networking.sessions;

import org.slf4j.Logger;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.messages.IMessageComposer;

public interface ISession {
    IPlayer getPlayer();

    void disconnect();

    ISession send(IMessageComposer messageComposer);

    ISession sendQueue(IMessageComposer messageComposer);

    String getIpAddress();

    Logger getLogger();

    Connection getConnection();

    void flush();
}
