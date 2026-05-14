package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.ComposerShit;
import com.cometproject.games.snowwar.MessageWriter;
import com.cometproject.games.snowwar.gameevents.BallThrowToHuman;

/**
 * Describes serialize game2 event ball throw to human behavior for the network message subsystem.
 */
public class SerializeGame2EventBallThrowToHuman {
    /**
     * Executes parse for this network message contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param evt Evt supplied by the caller.
     */
    public static void parse(IComposer msg, BallThrowToHuman evt) {
        msg.writeInt(evt.attacker.objectId);
        msg.writeInt(evt.victim.objectId);
        msg.writeInt(evt.type);
    }

    /**
     * Executes parse for this network message contract.
     *
     * @param ClientMessage Client message supplied by the caller.
     * @param evt Evt supplied by the caller.
     */
    public static void parse(MessageWriter ClientMessage, BallThrowToHuman evt) {
        ComposerShit.add(evt.attacker.objectId, ClientMessage);
        ComposerShit.add(evt.victim.objectId, ClientMessage);
        ComposerShit.add(evt.type, ClientMessage);
    }
}