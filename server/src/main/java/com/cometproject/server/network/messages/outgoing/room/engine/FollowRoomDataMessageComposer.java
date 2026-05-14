package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.RoomWriter;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the follow room data message for the Pixel Protocol client.
 */
public class FollowRoomDataMessageComposer extends MessageComposer {
    private final IRoomData roomData;
    private final boolean isLoading;
    private final boolean checkEntry;
    private final boolean skipAuth;
    private final boolean canMute;

    /**
     * Creates a follow room data message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     * @param isLoading Is loading supplied by the caller.
     * @param checkEntry Check entry supplied by the caller.
     * @param skipAuth Skip auth supplied by the caller.
     * @param canMute Can mute supplied by the caller.
     */
    public FollowRoomDataMessageComposer(final IRoomData room, boolean isLoading, boolean checkEntry, boolean skipAuth, boolean canMute) {
        this.roomData = room;
        this.isLoading = isLoading;
        this.checkEntry = checkEntry;
        this.skipAuth = skipAuth;
        this.canMute = canMute;
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
     * @param composer Composer supplied by the caller.
     */
    @Override
    public void compose(IComposer composer) {
        RoomWriter.entryData(this.roomData, composer, this.isLoading, this.checkEntry, this.skipAuth, this.canMute);
    }
}
