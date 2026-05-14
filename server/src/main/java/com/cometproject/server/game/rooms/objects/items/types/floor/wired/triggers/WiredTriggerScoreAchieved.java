package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;


/**
 * Describes wired trigger score achieved behavior for the room subsystem.
 */
public class WiredTriggerScoreAchieved extends WiredTriggerItem {
    private static final int PARAM_SCORE_TO_ACHIEVE = 0;

    /**
     * Creates a wired trigger score achieved instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerScoreAchieved(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param score Score supplied by the caller.
     * @param team Team supplied by the caller.
     * @param room Room participating in the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(int score, GameTeam team, Room room) {
        boolean wasExecuted = false;

        for (RoomItemFloor floorItem : getTriggers(room, WiredTriggerScoreAchieved.class)) {
            WiredTriggerScoreAchieved trigger = ((WiredTriggerScoreAchieved) floorItem);

            if (trigger.scoreToAchieve() == score) {
                wasExecuted = trigger.evaluate(null, team);
            }
        }

        return wasExecuted;
    }

    /**
     * Executes supplies player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean suppliesPlayer() {
        return false;
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 10;
    }

    /**
     * Executes score to achieve for this room contract.
     *
     * @return Result produced by the operation.
     */
    public int scoreToAchieve() {
        if (this.getWiredData().getParams().size() == 1) {
            return this.getWiredData().getParams().get(PARAM_SCORE_TO_ACHIEVE);
        }

        return 0;
    }
}
