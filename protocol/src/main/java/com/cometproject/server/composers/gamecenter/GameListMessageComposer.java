package com.cometproject.server.composers.gamecenter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the game list message for the Pixel Protocol client.
 */
public class GameListMessageComposer extends MessageComposer {
    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GameListMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1);

        msg.writeInt(3);
        msg.writeString("basejump");
        msg.writeString("68bbd2");
        msg.writeString("ffffff");
        msg.writeString("http://localhost/url2/swf/games/gamecenter_basejump/");
        msg.writeString("");
    }
}
