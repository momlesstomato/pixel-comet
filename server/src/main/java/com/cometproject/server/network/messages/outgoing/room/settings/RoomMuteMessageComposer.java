package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the room mute message for the Pixel Protocol client.
 */
public class RoomMuteMessageComposer extends MessageComposer {

    private final boolean roomHasMute;

    /**
     * Creates a room mute message composer instance for the network message subsystem.
     *
     * @param roomHasMute Room has mute supplied by the caller.
     */
    public RoomMuteMessageComposer(boolean roomHasMute) {
        this.roomHasMute = roomHasMute;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomMuteSettingsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.roomHasMute);
    }
}
