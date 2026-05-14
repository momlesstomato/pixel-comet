package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.ComposerShit;
import com.cometproject.games.snowwar.MessageWriter;
import com.cometproject.games.snowwar.SnowWarRoom;
import com.cometproject.games.snowwar.gameevents.*;

/**
 * Describes serialize game status behavior for the network message subsystem.
 */
public class SerializeGameStatus {
    /**
     * Executes parse for this network message contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param arena Arena supplied by the caller.
     * @param isFull Is full supplied by the caller.
     */
    public static void parse(IComposer msg, SnowWarRoom arena, boolean isFull) {
        msg.writeInt(arena.Turn);
        msg.writeInt(seed(arena.Turn) + arena.checksum);
        msg.writeInt(1);
        msg.writeInt(arena.gameEvents.size());
        for (Event evt : arena.gameEvents) {
            msg.writeInt(evt.EventType);
            if (evt.EventType == 1) {
                SerializeGame2EventPlayerLeft.parse(msg, (PlayerLeft) evt);
            } else if (evt.EventType == 2) {
                SerializeGame2EventMove.parse(msg, (UserMove) evt);
            } else if (evt.EventType == 7) {
                SerializeGame2EventPickSnowBall.parse(msg, (MakeSnowBall) evt);
            } else if (evt.EventType == 8) {
                SerializeGame2EventCreateSnowBall.parse(msg, (CreateSnowBall) evt);
            } else if (evt.EventType == 4) {
                SerializeGame2EventBallThrowToPosition.parse(msg, (BallThrowToPosition) evt);
            } else if (evt.EventType == 3) {
                SerializeGame2EventBallThrowToHuman.parse(msg, (BallThrowToHuman) evt);
            } else if (evt.EventType == 12) {
                SerializeGame2EventPickBallFromGameItem.parse(msg, (PickBallFromGameItem) evt);
            } else if (evt.EventType == 11) {
                SerializeGame2EventAddBallToMachine.parse(msg, (AddBallToMachine) evt);
            } else {
                throw new UnsupportedOperationException("Not yet implemented");
            }
            if (!isFull) {
                evt.apply();
            }
        }
    }

    /**
     * Executes parse new for this network message contract.
     *
     * @param ClientMessage Client message supplied by the caller.
     * @param arena Arena supplied by the caller.
     * @param isFull Is full supplied by the caller.
     */
    public static void parseNew(MessageWriter ClientMessage, SnowWarRoom arena, boolean isFull) {
        int i = 0;

        ComposerShit.add(arena.Turn, ClientMessage);
        ComposerShit.add(seed(arena.Turn) + arena.checksum, ClientMessage);

        ComposerShit.add(1, ClientMessage);

        ComposerShit.add(ClientMessage.setSaved(0), ClientMessage);
        for (Event evt : arena.gameEvents) {
            ComposerShit.add(evt.EventType, ClientMessage);
            if (evt.EventType == 1) {
                SerializeGame2EventPlayerLeft.parse(ClientMessage, (PlayerLeft) evt);
            } else if (evt.EventType == 2) {
                SerializeGame2EventMove.parse(ClientMessage, (UserMove) evt);
            } else if (evt.EventType == 7) {
                SerializeGame2EventPickSnowBall.parse(ClientMessage, (MakeSnowBall) evt);
            } else if (evt.EventType == 8) {
                SerializeGame2EventCreateSnowBall.parse(ClientMessage, (CreateSnowBall) evt);
            } else if (evt.EventType == 4) {
                SerializeGame2EventBallThrowToPosition.parse(ClientMessage, (BallThrowToPosition) evt);
            } else if (evt.EventType == 3) {
                SerializeGame2EventBallThrowToHuman.parse(ClientMessage, (BallThrowToHuman) evt);
            } else if (evt.EventType == 12) {
                SerializeGame2EventPickBallFromGameItem.parse(ClientMessage, (PickBallFromGameItem) evt);
            } else if (evt.EventType == 11) {
                SerializeGame2EventAddBallToMachine.parse(ClientMessage, (AddBallToMachine) evt);
            } else {
                throw new UnsupportedOperationException("Not yet implemented");
            }

            if (!isFull) {
                evt.apply();
            }
            i++;
        }
        ClientMessage.writeSaved(i);
    }

    /**
     * Executes seed for this network message contract.
     *
     * @param Turn Turn supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int seed(int Turn) {
        if (Turn == 0) {
            Turn = -1;
        }
        int k = Turn << 13;
        Turn ^= k;
        k = Turn >> 17;
        Turn ^= k;
        k = Turn << 5;
        Turn ^= k;
        return Turn;
    }
}