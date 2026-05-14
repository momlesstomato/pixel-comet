package com.cometproject.server.game.rooms.objects.entities;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.Square;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.utilities.attributes.Attributable;

import java.util.List;
import java.util.Map;


/**
 * Defines the avatar entity contract for the room subsystem.
 */
public interface AvatarEntity extends Attributable {
    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    int getId();

    /**
     * Returns the walking goal for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    Position getWalkingGoal();

    /**
     * Updates the walking goal for this Comet contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    void setWalkingGoal(int x, int y);

    /**
     * Returns the position to set for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    Position getPositionToSet();

    /**
     * Executes update and set position for this Comet contract.
     *
     * @param position Position supplied by the caller.
     */
    void updateAndSetPosition(Position position);

    /**
     * Executes mark position is set for this Comet contract.
     */
    void markPositionIsSet();

    /**
     * Returns the body rotation for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getBodyRotation();

    /**
     * Updates the body rotation for this Comet contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    void setBodyRotation(int rotation);

    /**
     * Returns the head rotation for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getHeadRotation();

    /**
     * Updates the head rotation for this Comet contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    void setHeadRotation(int rotation);

    /**
     * Returns the walking path for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    List<Square> getWalkingPath();

    /**
     * Updates the walking path for this Comet contract.
     *
     * @param path Path supplied by the caller.
     */
    void setWalkingPath(List<Square> path);

    /**
     * Returns the processing path for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    List<Square> getProcessingPath();

    /**
     * Updates the processing path for this Comet contract.
     *
     * @param path Path supplied by the caller.
     */
    void setProcessingPath(List<Square> path);

    /**
     * Indicates whether walking applies to this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean isWalking();

    /**
     * Returns the future square for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    Square getFutureSquare();

    /**
     * Updates the future square for this Comet contract.
     *
     * @param square Square supplied by the caller.
     */
    void setFutureSquare(Square square);

    /**
     * Executes move to for this Comet contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    void moveTo(int x, int y);

    /**
     * Returns the statuses for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    Map<RoomEntityStatus, String> getStatuses();

    /**
     * Executes add status for this Comet contract.
     *
     * @param key Key supplied by the caller.
     * @param value Value supplied by the caller.
     */
    void addStatus(RoomEntityStatus key, String value);

    /**
     * Executes remove status for this Comet contract.
     *
     * @param key Key supplied by the caller.
     */
    void removeStatus(RoomEntityStatus key);

    /**
     * Executes has status for this Comet contract.
     *
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean hasStatus(RoomEntityStatus key);

    /**
     * Executes mark needs update for this Comet contract.
     */
    void markNeedsUpdate();

    /**
     * Executes needs update for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean needsUpdate();

    /**
     * Updates the idle for this Comet contract.
     */
    void setIdle();

    /**
     * Returns the idle time for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getIdleTime();

    /**
     * Indicates whether idle and increment applies to this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean isIdleAndIncrement();

    /**
     * Executes reset idle time for this Comet contract.
     */
    void resetIdleTime();
    /**
     * Updates the idle status for this Comet contract.
     *
     * @param value Value supplied by the caller.
     */
    void setIdleStatus(boolean value);

    /**
     * Returns the dance id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getDanceId();

    /**
     * Updates the dance id for this Comet contract.
     *
     * @param danceId Dance id supplied by the caller.
     */
    void setDanceId(int danceId);

    /**
     * Returns the sign time for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getSignTime();

    /**
     * Executes mark displaying sign for this Comet contract.
     */
    void markDisplayingSign();

    /**
     * Indicates whether displaying sign applies to this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean isDisplayingSign();

    /**
     * Indicates whether overriden applies to this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean isOverriden();

    /**
     * Updates the overriden for this Comet contract.
     *
     * @param overriden Overriden supplied by the caller.
     */
    void setOverriden(boolean overriden);

    /**
     * Returns the current effect for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    PlayerEffect getCurrentEffect();

    /**
     * Executes apply effect for this Comet contract.
     *
     * @param effect Effect supplied by the caller.
     */
    void applyEffect(PlayerEffect effect);

    /**
     * Executes carry item for this Comet contract.
     *
     * @param id Id supplied by the caller.
     */
    void carryItem(int id);

    /**
     * Executes carry item for this Comet contract.
     *
     * @param id Id supplied by the caller.
     * @param timer Timer supplied by the caller.
     */
    void carryItem(int id, boolean timer);

    /**
     * Returns the hand item for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getHandItem();

    /**
     * Executes hand item needs remove for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean handItemNeedsRemove();

    /**
     * Handles the reached tile callback for this Comet contract.
     *
     * @param tile Tile supplied by the caller.
     */
    void onReachedTile(RoomTile tile);

    /**
     * Returns the hand item timer for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getHandItemTimer();

    /**
     * Updates the hand item timer for this Comet contract.
     *
     * @param time Time supplied by the caller.
     */
    void setHandItemTimer(int time);

    /**
     * Returns the username for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getUsername();

    /**
     * Returns the motto for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getMotto();

    /**
     * Returns the figure for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getFigure();

    /**
     * Returns the gender for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getGender();

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    void compose(IComposer msg);

    /**
     * Executes warp for this Comet contract.
     *
     * @param position Position supplied by the caller.
     */
    void warp(Position position);

    /**
     * Executes warp immediately for this Comet contract.
     *
     * @param position Position supplied by the caller.
     */
    void warpImmediately(Position position);

    /**
     * Executes kick for this Comet contract.
     */
    void kick();

    /**
     * Returns the join time for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    long getJoinTime();
}
