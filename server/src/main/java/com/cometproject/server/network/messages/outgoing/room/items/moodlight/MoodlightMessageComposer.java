package com.cometproject.server.network.messages.outgoing.room.items.moodlight;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.items.data.MoodlightPresetData;
import com.cometproject.server.game.rooms.objects.items.types.wall.MoodlightWallItem;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the moodlight message for the Pixel Protocol client.
 */
public class MoodlightMessageComposer extends MessageComposer {
    private final MoodlightWallItem moodlightWallItem;

    /**
     * Creates a moodlight message composer instance for the network message subsystem.
     *
     * @param moodlightWallItem Moodlight wall item supplied by the caller.
     */
    public MoodlightMessageComposer(final MoodlightWallItem moodlightWallItem) {
        this.moodlightWallItem = moodlightWallItem;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MoodlightConfigMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.moodlightWallItem.getMoodlightData().getPresets().size());
        msg.writeInt(this.moodlightWallItem.getMoodlightData().getActivePreset());

        int id = 1;

        for (MoodlightPresetData data : this.moodlightWallItem.getMoodlightData().getPresets()) {
            msg.writeInt(id++);
            msg.writeInt(data.backgroundOnly ? 2 : 1);
            msg.writeString(data.colour);
            msg.writeInt(data.intensity);
        }
    }
}
