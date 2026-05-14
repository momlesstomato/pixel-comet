package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.ComposerShit;
import com.cometproject.games.snowwar.MessageWriter;
import com.cometproject.games.snowwar.gameevents.UserMove;

/**
 * Describes serialize game2 event move behavior for the network message subsystem.
 */
public class SerializeGame2EventMove {
    /**
     * Executes parse for this network message contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param evt Evt supplied by the caller.
     */
    public static void parse(IComposer msg, UserMove evt) {
        msg.writeInt(evt.player.objectId);
        msg.writeInt(evt.x);
        msg.writeInt(evt.y);
    }

    /**
     * Executes parse for this network message contract.
     *
     * @param ClientMessage Client message supplied by the caller.
     * @param evt Evt supplied by the caller.
     */
    public static void parse(MessageWriter ClientMessage, UserMove evt) {
        ComposerShit.add(evt.player.objectId, ClientMessage);
        ComposerShit.add(evt.x, ClientMessage);
        ComposerShit.add(evt.y, ClientMessage);
    }
}