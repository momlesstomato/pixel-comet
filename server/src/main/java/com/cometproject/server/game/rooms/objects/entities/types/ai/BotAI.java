package com.cometproject.server.game.rooms.objects.entities.types.ai;

import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;


/**
 * Defines the bot ai contract for the room subsystem.
 */
public interface BotAI {
    /**
     * Handles the talk callback for this Comet contract.
     *
     * @param entity Entity supplied by the caller.
     * @param playerEntity Player entity supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean onTalk(PlayerEntity entity, String message); // return value indicates whether the message should be broadcasted to room or not.

    /**
     * Handles the player enter callback for this Comet contract.
     *
     * @param playerEntity Player entity supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean onPlayerEnter(PlayerEntity playerEntity);

    /**
     * Handles the player leave callback for this Comet contract.
     *
     * @param playerEntity Player entity supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean onPlayerLeave(PlayerEntity playerEntity);

    /**
     * Handles the added to room callback for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean onAddedToRoom();

    /**
     * Handles the removed from room callback for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean onRemovedFromRoom();

    /**
     * Handles the tick callback for this Comet contract.
     */
    void onTick();

    /**
     * Handles the reached tile callback for this Comet contract.
     *
     * @param tile Tile supplied by the caller.
     */
    void onReachedTile(RoomTile tile);

    /**
     * Handles the tick complete callback for this Comet contract.
     */
    void onTickComplete();

    /**
     * Executes sit for this Comet contract.
     */
    void sit();

    /**
     * Executes lay for this Comet contract.
     */
    void lay();

    /**
     * Updates the ticks until complete in seconds for this Comet contract.
     *
     * @param seconds Seconds supplied by the caller.
     */
    void setTicksUntilCompleteInSeconds(double seconds);

    /**
     * Executes say for this Comet contract.
     *
     * @param message Message supplied by the caller.
     */
    void say(String message);

    /**
     * Executes say for this Comet contract.
     *
     * @param message Message supplied by the caller.
     * @param emotion Emotion supplied by the caller.
     */
    void say(String message, ChatEmotion emotion);

    /**
     * Executes can move for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean canMove();
}