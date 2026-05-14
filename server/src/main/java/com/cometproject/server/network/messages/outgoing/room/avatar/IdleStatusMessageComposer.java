package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom.WiredTriggerCustomIdle;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the idle status message for the Pixel Protocol client.
 */
public class IdleStatusMessageComposer extends MessageComposer {
    private final int playerId;
    private final boolean isIdle;

    /**
     * Creates a idle status message composer instance for the network message subsystem.
     *
     * @param playerEntity Player entity supplied by the caller.
     * @param isIdle Is idle supplied by the caller.
     */
    public IdleStatusMessageComposer(final PlayerEntity playerEntity, final boolean isIdle) {
        this.playerId = playerEntity.getId();
        this.isIdle = isIdle;

        if(isIdle)
        WiredTriggerCustomIdle.executeTriggers(playerEntity);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SleepMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerId);
        msg.writeBoolean(this.isIdle);
    }
}
