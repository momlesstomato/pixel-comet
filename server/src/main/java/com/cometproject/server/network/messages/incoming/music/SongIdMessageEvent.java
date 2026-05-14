package com.cometproject.server.network.messages.incoming.music;

import com.cometproject.api.game.furniture.types.IMusicData;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.music.SongIdMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the song id message event published by the network message subsystem.
 */
public class SongIdMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String songName = msg.readString();

        IMusicData musicData = ItemManager.getInstance().getMusicDataByName(songName);

        if (musicData != null) {
            client.send(new SongIdMessageComposer(musicData.getName(), musicData.getSongId()));
        }
    }
}