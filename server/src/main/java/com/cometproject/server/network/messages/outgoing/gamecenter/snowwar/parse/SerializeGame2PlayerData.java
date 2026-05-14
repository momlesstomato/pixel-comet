package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
/**
 * Carries serialize game2 player data data for the network message subsystem.
 */
public class SerializeGame2PlayerData {
    /**
     * Executes parse for this network message contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param Player Player supplied by the caller.
     */
    public static void parse(IComposer msg, HumanGameObject Player) {
        msg.writeInt(Player.userId);
        msg.writeString(Player.userName);
        msg.writeString(Player.look);
        msg.writeString(Player.sex);
        msg.writeInt(Player.team);
    }
}