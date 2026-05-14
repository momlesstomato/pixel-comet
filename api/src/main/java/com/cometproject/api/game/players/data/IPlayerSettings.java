package com.cometproject.api.game.players.data;

import com.cometproject.api.game.players.data.types.IPlaylistItem;
import com.cometproject.api.game.players.data.types.IVolumeData;
import com.cometproject.api.game.players.data.types.IWardrobeItem;

import java.util.List;

/**
 * Defines the i player settings contract for the player subsystem.
 */
public interface IPlayerSettings {

    /**
     * Returns the volumes associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IVolumeData getVolumes();

    /**
     * Returns the hide online associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getHideOnline();

    /**
     * Returns the room tool state associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRoomToolState();

    /**
     * Returns the bubble id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBubbleId();

    /**
     * Updates the room tool state value for this player contract.
     *
     * @param roomToolState Room tool state value supplied by the caller.
     */
    void setRoomToolState(int roomToolState);

    /**
     * Updates the bubble id value for this player contract.
     *
     * @param bubbleId Bubble id value supplied by the caller.
     */
    void setBubbleId(int bubbleId);

    /**
     * Returns the hide in room associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getHideInRoom();

    /**
     * Returns the allow friend requests associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getAllowFriendRequests();

    /**
     * Updates the allow friend requests value for this player contract.
     *
     * @param allowFriendRequests Allow friend requests value supplied by the caller.
     */
    void setAllowFriendRequests(boolean allowFriendRequests);

    /**
     * Updates the personal staff value for this player contract.
     *
     * @param b B value supplied by the caller.
     */
    void setPersonalStaff(boolean b);

    /**
     * Indicates whether this player contract has personal staff.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasPersonalStaff();

    /**
     * Returns the allow trade associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getAllowTrade();
    
    /**
     * Returns the allow follow associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getAllowFollow();
    
    /**
     * Returns the allow mimic associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getAllowMimic();

    /**
     * Returns the home room associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHomeRoom();

    /**
     * Updates the home room value for this player contract.
     *
     * @param homeRoom Home room value supplied by the caller.
     */
    void setHomeRoom(int homeRoom);

    /**
     * Returns the wardrobe associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IWardrobeItem> getWardrobe();

    /**
     * Updates the wardrobe value for this player contract.
     *
     * @param wardrobe Wardrobe value supplied by the caller.
     */
    void setWardrobe(List<IWardrobeItem> wardrobe);

    /**
     * Returns the playlist associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IPlaylistItem> getPlaylist();

    /**
     * Indicates whether use old chat is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isUseOldChat();

    /**
     * Updates the use old chat value for this player contract.
     *
     * @param useOldChat Use old chat value supplied by the caller.
     */
    void setUseOldChat(boolean useOldChat);

    /**
     * Executes the ignore events operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean ignoreEvents();

    /**
     * Executes the room camera follow operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomCameraFollow();

    /**
     * Returns the event type associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getEventType();

    /**
     * Updates the ignore invites value for this player contract.
     *
     * @param ignoreInvites Ignore invites value supplied by the caller.
     */
    void setIgnoreInvites(boolean ignoreInvites);

    /**
     * Executes the dispose operation for this player contract.
     */
    void dispose();
}
