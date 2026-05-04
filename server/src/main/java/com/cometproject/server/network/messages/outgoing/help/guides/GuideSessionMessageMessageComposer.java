package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

public class GuideSessionMessageMessageComposer extends MessageComposer {
    private final String message;
    private final int playerId;

    public GuideSessionMessageMessageComposer(String message, int playerId) {
        this.message = message;
        this.playerId = playerId;
    }

    @Override
    public short getId() {
        return Composers.GuideSessionMessageMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.message);
        msg.writeInt(this.playerId);
    }
}