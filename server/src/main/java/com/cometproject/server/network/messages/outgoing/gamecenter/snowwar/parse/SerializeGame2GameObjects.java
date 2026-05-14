package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.games.snowwar.gameobjects.GameItemObject;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;

/**
 * Describes serialize game2 game objects behavior for the network message subsystem.
 */
public class SerializeGame2GameObjects {
    /**
     * Executes parse for this network message contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param arena Arena supplied by the caller.
     */
    public static void parse(IComposer msg, SnowWarRoom arena) {
        synchronized (arena.gameObjects) {
            msg.writeInt(arena.gameObjects.size());
            for (GameItemObject gameItemObject : arena.gameObjects.values()) {
                for (int i = 0; i < gameItemObject.variablesCount; i++) {
                    msg.writeInt(gameItemObject.getVariable(i));
                }

                if (gameItemObject.getVariable(0) == 5) {
                    HumanGameObject Player = (HumanGameObject) gameItemObject;
                    msg.writeString(Player.userName);
                    msg.writeString(Player.motto);
                    msg.writeString(Player.look);
                    msg.writeString(Player.sex);
                }
            }
        }
    }
}