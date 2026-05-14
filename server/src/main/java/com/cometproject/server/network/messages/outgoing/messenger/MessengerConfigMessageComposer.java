package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the messenger config message for the Pixel Protocol client.
 */
public class MessengerConfigMessageComposer extends MessageComposer {

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MessengerInitMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(CometSettings.messengerMaxFriends);
        msg.writeInt(300);
        msg.writeInt(800);
        // msg.writeInt(CometSettings.messengerMaxFriends);

        if (CometSettings.groupChatEnabled) {
            msg.writeInt(1);

            msg.writeInt(1);
            msg.writeString(Locale.getOrDefault("group.chats", "Group Chats"));
        } else {
            msg.writeInt(0);
        }
    }
}