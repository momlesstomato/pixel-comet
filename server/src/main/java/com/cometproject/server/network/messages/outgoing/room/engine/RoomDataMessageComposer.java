package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.RoomWriter;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room data message for the Pixel Protocol client.
 */
public class RoomDataMessageComposer extends MessageComposer {
    private final Room room;
    private final boolean checkEntry;
    private final boolean canMute;
    private final boolean isLoading;

    /**
     * Creates a room data message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     * @param checkEntry Check entry supplied by the caller.
     * @param canMute Can mute supplied by the caller.
     */
    public RoomDataMessageComposer(final Room room, boolean checkEntry, boolean canMute) {
        this.room = room;
        this.checkEntry = checkEntry;
        this.canMute = canMute;
        this.isLoading = false;
    }

    /**
     * Creates a room data message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     * @param checkEntry Check entry supplied by the caller.
     * @param canMute Can mute supplied by the caller.
     * @param isLoading Is loading supplied by the caller.
     */
    public RoomDataMessageComposer(final Room room, boolean checkEntry, boolean canMute, boolean isLoading) {
        this.room = room;
        this.checkEntry = checkEntry;
        this.canMute = canMute;
        this.isLoading = isLoading;
    }

    /**
     * Creates a room data message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     */
    public RoomDataMessageComposer(final Room room) {
        this.room = room;
        this.checkEntry = true;
        this.canMute = false;
        this.isLoading = true;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GetGuestRoomResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        RoomWriter.entryData(room.getData(), msg, this.isLoading, this.checkEntry, false, this.canMute);
    }
}
