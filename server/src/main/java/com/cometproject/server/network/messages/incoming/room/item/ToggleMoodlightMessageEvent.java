package com.cometproject.server.network.messages.incoming.room.item;

import com.cometproject.server.game.rooms.objects.items.types.wall.MoodlightWallItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.items.MoodlightDao;


/**
 * Represents the toggle moodlight message event published by the network message subsystem.
 */
public class ToggleMoodlightMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }
        if (!room.getRights().hasRights(client.getPlayer().getEntity().getPlayerId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            client.disconnect();
            return;
        }

        MoodlightWallItem moodlight = room.getItems().getMoodlight();
        if (moodlight == null) {
            return;
        }

        if (moodlight.getMoodlightData() == null) {
            return;
        }

        moodlight.getMoodlightData().setEnabled(!moodlight.getMoodlightData().isEnabled());

        // save the data!
        MoodlightDao.updateMoodlight(moodlight);

        // set the mood!
        moodlight.getItemData().setData(moodlight.generateExtraData());
        moodlight.saveData();
        moodlight.sendUpdate();
    }
}