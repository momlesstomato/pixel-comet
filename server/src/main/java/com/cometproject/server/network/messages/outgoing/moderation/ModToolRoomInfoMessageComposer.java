package com.cometproject.server.network.messages.outgoing.moderation;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the mod tool room info message for the Pixel Protocol client.
 */
public class ModToolRoomInfoMessageComposer extends MessageComposer {
    private final Room room;

    /**
     * Creates a mod tool room info message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     */
    public ModToolRoomInfoMessageComposer(final Room room) {
        this.room = room;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorRoomInfoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(room.getId());
        msg.writeInt(room.getEntities().playerCount());

        msg.writeBoolean(PlayerManager.getInstance().isOnline(room.getData().getOwnerId()));
        msg.writeInt(room.getData().getOwnerId());
        msg.writeString(room.getData().getOwner());

        msg.writeBoolean(true); // TODO: Allow for rooms that aren't active to show here ;-)

        msg.writeString(room.getData().getName());
        msg.writeString(room.getData().getDescription());
        msg.writeInt(room.getData().getTags().length);

        for (int i = 0; i < room.getData().getTags().length; i++) {
            msg.writeString(room.getData().getTags()[i]);
        }
    }
}
