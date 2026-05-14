package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the settings updated message for the Pixel Protocol client.
 */
public class SettingsUpdatedMessageComposer extends MessageComposer {

    private final int roomId;

    /**
     * Creates a settings updated message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     */
    public SettingsUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomSettingsSavedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.roomId);
    }
}
