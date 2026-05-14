package com.cometproject.server.network.messages.incoming.handshake;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.handshake.InitCryptoMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.crypto.exceptions.HabboCryptoException;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the init crypto message event published by the network message subsystem.
 */
public class InitCryptoMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws HabboCryptoException When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws HabboCryptoException {
        // encryption is disabled, so no idea what this client is trying to do
        if(client.getEncryption() == null) {
            client.disconnect();
            return;
        }

        client.send(new InitCryptoMessageComposer(
                client.getEncryption().getDiffie().getSignedPrime(),
                client.getEncryption().getDiffie().getSignedGenerator()));
    }
}