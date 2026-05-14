package com.cometproject.server.network.messages.incoming.gamecenter.snowwar.game;

import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.games.snowwar.data.SnowWarPlayerData;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Describes game chat parser behavior for the network message subsystem.
 */
public class GameChatParser implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    public void handle(Session client, MessageEvent msg) throws Exception {
        SnowWarPlayerData snowPlayer = client.snowWarPlayerData;
        if (snowPlayer == null) {
            return;
        }

        SnowWarRoom room = snowPlayer.currentSnowWar;
        if (room == null) {
            return;
        }

        final String say = msg.readString();

        room.broadcast(new TalkMessageComposer(snowPlayer.humanObject.objectId, say, ChatEmotion.NONE, snowPlayer.humanObject.team == 1 ? 4 : 3));
    }
}