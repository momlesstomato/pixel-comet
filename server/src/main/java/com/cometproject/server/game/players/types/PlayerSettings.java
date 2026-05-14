package com.cometproject.server.game.players.types;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.players.data.IPlayerSettings;
import com.cometproject.api.game.players.data.types.IPlaylistItem;
import com.cometproject.api.game.players.data.types.IVolumeData;
import com.cometproject.api.game.players.data.types.IWardrobeItem;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.components.types.settings.PlaylistItem;
import com.cometproject.server.game.players.components.types.settings.VolumeData;
import com.cometproject.server.game.players.components.types.settings.WardrobeItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Describes player settings behavior for the player subsystem.
 */
public class PlayerSettings implements IPlayerSettings {
    private VolumeData volumes;

    private List<IWardrobeItem> wardrobe;
    private List<IPlaylistItem> playlist;

    private boolean hideOnline;
    private boolean hideInRoom;
    private boolean allowFriendRequests;
    private boolean allowTrade;
    private boolean allowFollow;
    private boolean allowMimic;
    private boolean personalstaff;
    private boolean pinSuccess = Comet.isDebugging;
    private int pinTries;
    private String personalPin;
    private int nuxStatus;

    private int homeRoom;
    private boolean useOldChat;
    private boolean ignoreInvites;

    private int navigatorX;
    private int navigatorY;
    private int navigatorHeight;
    private int navigatorWidth;
    private boolean navigatorShowSearches;
    private int royaleXP;
    private int bubbleId;
    private int roomToolState;

    private boolean disableWhisper;
    private boolean ignoreEvents;
    private boolean roomCameraFollow;
    private boolean claimedGoal;
    private String eventType;

    private Player player;

    private boolean sendLoginNotification;

    /**
     * Creates a player settings instance for the player subsystem.
     *
     * @param data Data supplied by the caller.
     * @param isLogin Is login supplied by the caller.
     * @param player Player participating in the operation.
     * @throws SQLException When the operation cannot complete.
     */
    public PlayerSettings(ResultSet data, boolean isLogin, Player player) throws SQLException {
        if (isLogin) {
            String volumeData = data.getString("playerSettings_volume");

            if (volumeData != null && volumeData.startsWith("{")) {
                volumes = JsonUtil.getInstance().fromJson(volumeData, VolumeData.class);
            } else {
                volumes = new VolumeData(100, 100, 100);
            }

            this.hideOnline = data.getString("playerSettings_hideOnline").equals("1");
            this.hideInRoom = data.getString("playerSettings_hideInRoom").equals("1");
            this.allowFriendRequests = data.getString("playerSettings_allowFriendRequests").equals("1");
            this.allowTrade = data.getString("playerSettings_allowTrade").equals("1");
            this.allowFollow = data.getString("playerSettings_allowFollow").equals("1");
            this.allowMimic = data.getString("playerSettings_allowMimic").equals("1");
            this.personalstaff = data.getString("playerSettings_personalstaff").equals("1");
            this.nuxStatus = data.getInt("playerSettings_nux");
            this.royaleXP = data.getInt("playerSettings_royaleXP");

            this.bubbleId = data.getInt("playerSettings_bubbleId");
            this.roomToolState = data.getInt("playerSettings_roomToolState");

            this.homeRoom = data.getInt("playerSettings_homeRoom");
            this.eventType = data.getString("playerSettings_eventType");

            String wardrobeText = data.getString("playerSettings_wardrobe");

            if (wardrobeText == null || wardrobeText.isEmpty()) {
                wardrobe = new ArrayList<>();
            } else {
                wardrobe = JsonUtil.getInstance().fromJson(wardrobeText, new TypeToken<ArrayList<WardrobeItem>>() {
                }.getType());
            }

            String playlistText = data.getString("playerSettings_playlist");

            if (playlistText == null || playlistText.isEmpty()) {
                playlist = new ArrayList<>();
            } else {
                playlist = JsonUtil.getInstance().fromJson(playlistText, new TypeToken<ArrayList<PlaylistItem>>() {
                }.getType());
            }

            this.useOldChat = data.getString("playerSettings_useOldChat").equals("1");
            this.ignoreInvites = data.getString("playerSettings_ignoreInvites").equals("1");

            this.navigatorX = data.getInt("playerSettings_navigatorX");
            this.navigatorY = data.getInt("playerSettings_navigatorY");
            this.navigatorHeight = data.getInt("playerSettings_navigatorHeight");
            this.navigatorWidth = data.getInt("playerSettings_navigatorWidth");
            this.personalPin = data.getString("playerSettings_personalPin");

            this.navigatorShowSearches = data.getString("playerSettings_navigatorShowSearches").equals("1");

            this.ignoreEvents = data.getString("playerSettings_ignoreEvents").equalsIgnoreCase("1");
            this.disableWhisper = data.getString("playerSettings_disableWhisper").equalsIgnoreCase("1");
            this.sendLoginNotification = data.getString("playerSettings_sendLoginNotif").equalsIgnoreCase("1");
            this.roomCameraFollow = data.getString("playerSettings_roomCameraFollow").equalsIgnoreCase("1");
            this.claimedGoal = data.getString("playerSettings_claimedGoal").equalsIgnoreCase("1");
        } else {
            String volumeData = data.getString("volume");

            if (volumeData != null && volumeData.startsWith("{")) {
                volumes = JsonUtil.getInstance().fromJson(volumeData, VolumeData.class);
            } else {
                volumes = new VolumeData(100, 100, 100);
            }

            this.hideOnline = data.getString("hide_online").equals("1");
            this.hideInRoom = data.getString("hide_inroom").equals("1");
            this.allowFriendRequests = data.getString("allow_friend_requests").equals("1");
            this.allowTrade = data.getString("allow_trade").equals("1");
            this.allowFollow = data.getString("allow_follow").equals("1");
            this.allowFollow = data.getString("allow_mimic").equals("1");
            this.personalstaff = data.getString("personalstaff").equals("1");
            this.roomCameraFollow = data.getString("camera_follow").equals("1");
            this.claimedGoal = data.getString("claimed_goal").equals("1");
            this.personalPin = data.getString("personal_pin");
            this.nuxStatus = data.getInt("nux");
            this.royaleXP = data.getInt("royale_xp");
            this.bubbleId = data.getInt("bubble_id");
            this.roomToolState = data.getInt("room_tool_state");
            this.eventType = data.getString("event_type");


            this.homeRoom = data.getInt("home_room");

            String wardrobeText = data.getString("wardrobe");

            if (wardrobeText == null || wardrobeText.isEmpty()) {
                wardrobe = new ArrayList<>();
            } else {
                wardrobe = JsonUtil.getInstance().fromJson(wardrobeText, new TypeToken<ArrayList<WardrobeItem>>() {
                }.getType());
            }

            String playlistText = data.getString("playlist");

            if (playlistText == null || playlistText.isEmpty()) {
                playlist = new ArrayList<>();
            } else {
                playlist = JsonUtil.getInstance().fromJson(playlistText, new TypeToken<ArrayList<PlaylistItem>>() {
                }.getType());
            }

            this.useOldChat = data.getString("chat_oldstyle").equals("1");
            this.ignoreInvites = data.getString("ignore_invites").equals("1");

            this.navigatorX = data.getInt("navigator_x");
            this.navigatorY = data.getInt("navigator_y");
            this.navigatorHeight = data.getInt("navigator_height");
            this.navigatorWidth = data.getInt("navigator_width");

            this.navigatorShowSearches = data.getString("navigator_show_searches").equals("1");

            this.ignoreEvents = data.getString("ignore_events").equals("1");
            this.disableWhisper = data.getString("disable_whisper").equals("1");
            this.sendLoginNotification = data.getString("send_login_notif").equals("1");
        }

        flush();
    }

    /**
     * Creates a player settings instance for the player subsystem.
     */
    public PlayerSettings() {
        this.volumes = new VolumeData(75, 75, 65);
        this.hideInRoom = false;
        this.homeRoom = CometSettings.baseWelcomeRoomId;
        this.hideOnline = false;
        this.allowFriendRequests = true;
        this.allowTrade = true;
        this.allowFollow = true;
        this.allowMimic = true;
        this.wardrobe = new ArrayList<>();
        this.playlist = new ArrayList<>();
        this.useOldChat = false;
        this.roomCameraFollow = false;
        this.claimedGoal = false;
        this.nuxStatus = 0;
        this.navigatorX = 68;
        this.navigatorY = 42;
        this.navigatorWidth = 425;
        this.navigatorHeight = 592;
        this.bubbleId = 0;
        this.roomToolState = 3;
        this.navigatorShowSearches = true;
        this.disableWhisper = false;
    }

    /**
     * Returns the volumes for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public IVolumeData getVolumes() {
        return this.volumes;
    }

    /**
     * Returns the hide online for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getHideOnline() {
        return this.hideOnline;
    }

    /**
     * Returns the hide in room for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getHideInRoom() {
        return this.hideInRoom;
    }

    /**
     * Returns the room tool state for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomToolState() {
        return this.roomToolState;
    }

    /**
     * Updates the room tool state for this player contract.
     *
     * @param roomToolState Room tool state supplied by the caller.
     */
    public void setRoomToolState(int roomToolState) {
        this.roomToolState = roomToolState;
    }

    /**
     * Returns the bubble id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBubbleId() {
        return this.bubbleId;
    }

    /**
     * Updates the bubble id for this player contract.
     *
     * @param bubbleId Bubble id supplied by the caller.
     */
    public void setBubbleId(int bubbleId) {
        this.bubbleId = bubbleId;
    }

    /**
     * Executes verify goal for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean verifyGoal() { return this.claimedGoal; }

    /**
     * Updates the claimed goal for this player contract.
     *
     * @param claimedGoal Claimed goal supplied by the caller.
     */
    public void setClaimedGoal(boolean claimedGoal) {
        this.claimedGoal = claimedGoal;
    }

    /**
     * Returns the NUX status for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNuxStatus() { return this.nuxStatus; }

    /**
     * Executes increment NUX status for this player contract.
     */
    public void incrementNuxStatus() { this.nuxStatus++; }

    /**
     * Returns the allow friend requests for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getAllowFriendRequests() {
        return this.allowFriendRequests;
    }

    /**
     * Updates the allow friend requests for this player contract.
     *
     * @param allowFriendRequests Allow friend requests supplied by the caller.
     */
    public void setAllowFriendRequests(boolean allowFriendRequests) {
        this.allowFriendRequests = allowFriendRequests;

        flush();
    }

    /**
     * Returns the event type for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Updates the event type for this player contract.
     *
     * @param e E supplied by the caller.
     */
    public void setEventType(String e) {
        this.eventType = e;
    }

    /**
     * Updates the personal staff for this player contract.
     *
     * @param b B supplied by the caller.
     */
    public void setPersonalStaff(boolean b) {
        this.personalstaff = b;
    }

    /**
     * Indicates whether this player contract has personal staff.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPersonalStaff() {
        return this.personalstaff;
    }

    /**
     * Returns the allow trade for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getAllowTrade() {
        return this.allowTrade;
    }

    /**
     * Returns the allow follow for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getAllowFollow() {
        return this.allowFollow;
    }

    /**
     * Returns the allow mimic for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getAllowMimic() {
        return this.allowMimic;
    }

    /**
     * Returns the home room for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getHomeRoom() {
        return this.homeRoom;
    }

    /**
     * Returns the personal pin for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPersonalPin() {
        return personalPin;
    }

    /**
     * Indicates whether pin success applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isPinSuccess() {
        return pinSuccess;
    }

    /**
     * Returns the pin tries for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPinTries() {
        return pinTries;
    }

    /**
     * Executes increment pin tries for this player contract.
     */
    public void incrementPinTries() {
        this.pinTries++;
    }

    /**
     * Updates the pin succes for this player contract.
     */
    public void setPinSucces() {
        this.pinSuccess = true;
    }

    /**
     * Updates the home room for this player contract.
     *
     * @param homeRoom Home room supplied by the caller.
     */
    public void setHomeRoom(int homeRoom) {
        this.homeRoom = homeRoom;

        flush();
    }

    /**
     * Returns the wardrobe for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IWardrobeItem> getWardrobe() {
        return wardrobe;
    }

    /**
     * Updates the wardrobe for this player contract.
     *
     * @param wardrobe Wardrobe supplied by the caller.
     */
    public void setWardrobe(List<IWardrobeItem> wardrobe) {
        this.wardrobe = wardrobe;

        flush();
    }

    /**
     * Returns the playlist for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IPlaylistItem> getPlaylist() {
        return playlist;
    }

    /**
     * Indicates whether use old chat applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isUseOldChat() {
        return this.useOldChat;
    }

    /**
     * Updates the use old chat for this player contract.
     *
     * @param useOldChat Use old chat supplied by the caller.
     */
    public void setUseOldChat(boolean useOldChat) {
        this.useOldChat = useOldChat;

        flush();
    }

    /**
     * Executes room camera follow for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean roomCameraFollow(){ return this.roomCameraFollow; }

    /**
     * Updates the room camera follow for this player contract.
     *
     * @param roomCameraFollow Room camera follow supplied by the caller.
     */
    public void setRoomCameraFollow(boolean roomCameraFollow) {
        this.roomCameraFollow = roomCameraFollow;
    }

    /**
     * Executes ignore events for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean ignoreEvents() {
        return ignoreInvites;
    }

    /**
     * Updates the ignore invites for this player contract.
     *
     * @param ignoreInvites Ignore invites supplied by the caller.
     */
    public void setIgnoreInvites(boolean ignoreInvites) {
        this.ignoreInvites = ignoreInvites;

        flush();
    }

    /**
     * Returns the navigator x for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNavigatorX() {
        return navigatorX;
    }

    /**
     * Updates the navigator x for this player contract.
     *
     * @param navigatorX Navigator x supplied by the caller.
     */
    public void setNavigatorX(int navigatorX) {
        this.navigatorX = navigatorX;
    }

    /**
     * Returns the navigator y for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNavigatorY() {
        return navigatorY;
    }

    /**
     * Returns the royale XP for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoyaleXP() {
        return royaleXP;
    }

    /**
     * Executes increment royale XP for this player contract.
     *
     * @param add Add supplied by the caller.
     */
    public void incrementRoyaleXP(int add){
        this.royaleXP += add;
    }

    /**
     * Updates the navigator y for this player contract.
     *
     * @param navigatorY Navigator y supplied by the caller.
     */
    public void setNavigatorY(int navigatorY) {
        this.navigatorY = navigatorY;
    }

    /**
     * Returns the navigator height for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNavigatorHeight() {
        return navigatorHeight;
    }

    /**
     * Updates the navigator height for this player contract.
     *
     * @param navigatorHeight Navigator height supplied by the caller.
     */
    public void setNavigatorHeight(int navigatorHeight) {
        this.navigatorHeight = navigatorHeight;
    }

    /**
     * Returns the navigator width for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNavigatorWidth() {
        return navigatorWidth;
    }

    /**
     * Updates the navigator width for this player contract.
     *
     * @param navigatorWidth Navigator width supplied by the caller.
     */
    public void setNavigatorWidth(int navigatorWidth) {
        this.navigatorWidth = navigatorWidth;
    }

    /**
     * Returns the navigator show searches for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getNavigatorShowSearches() {
        return navigatorShowSearches;
    }

    /**
     * Updates the navigator show searches for this player contract.
     *
     * @param navigatorShowSearches Navigator show searches supplied by the caller.
     */
    public void setNavigatorShowSearches(boolean navigatorShowSearches) {
        this.navigatorShowSearches = navigatorShowSearches;
    }

    /**
     * Indicates whether ignore events applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isIgnoreEvents() {
        return ignoreEvents;
    }

    /**
     * Updates the ignore events for this player contract.
     *
     * @param ignoreEvents Ignore events supplied by the caller.
     */
    public void setIgnoreEvents(boolean ignoreEvents) {
        this.ignoreEvents = ignoreEvents;
    }

    /**
     * Updates the allow trade for this player contract.
     *
     * @param allowTrade Allow trade supplied by the caller.
     */
    public void setAllowTrade(boolean allowTrade) {
        this.allowTrade = allowTrade;
    }

    /**
     * Executes disable whisper for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean disableWhisper() {
        return disableWhisper;
    }

    /**
     * Updates the disable whisper for this player contract.
     *
     * @param disableWhisper Disable whisper supplied by the caller.
     */
    public void setDisableWhisper(boolean disableWhisper) {
        this.disableWhisper = disableWhisper;

        flush();
    }

    /**
     * Executes send login notif for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean sendLoginNotif() {
        return sendLoginNotification;
    }

    /**
     * Updates the send login notification for this player contract.
     *
     * @param sendLoginNotification Send login notification supplied by the caller.
     */
    public void setSendLoginNotification(boolean sendLoginNotification) {
        this.sendLoginNotification = sendLoginNotification;
    }

    /**
     * Executes to JSON for this player contract.
     *
     * @return Result produced by the operation.
     */
    public JsonObject toJson() {
        final JsonObject coreObject = new JsonObject();
        final JsonArray wardrobeArray = new JsonArray();

        coreObject.add("volumes", volumes.toJson());

        for(IWardrobeItem wardrobeItem : wardrobe) {
            wardrobeArray.add(wardrobeItem.toJson());
        }

        coreObject.add("wardrobe", wardrobeArray);

        coreObject.addProperty("hideOnline", hideOnline);
        coreObject.addProperty("hideInRoom", hideInRoom);
        coreObject.addProperty("allowFriendRequests", allowFriendRequests);
        coreObject.addProperty("allowTrade", allowTrade);
        coreObject.addProperty("allowFollow", allowFollow);
        coreObject.addProperty("allowMimic", allowMimic);
        coreObject.addProperty("homeRoom", homeRoom);
        coreObject.addProperty("useOldChat", useOldChat);
        coreObject.addProperty("ignoreInvites", ignoreInvites);
        coreObject.addProperty("disableWhisper", disableWhisper);
        coreObject.addProperty("ignoreEvents", ignoreEvents);
        coreObject.addProperty("bubbleId", bubbleId);
        coreObject.addProperty("roomToolState", roomToolState);

        return coreObject;
    }

    /**
     * Returns the player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Releases resources owned by this player component.
     */
    @Override
    public void dispose(){
        this.wardrobe.clear();
        this.playlist.clear();
        this.volumes = null;
        this.wardrobe = null;
    }
    /**
     * Executes flush for this player contract.
     */
    public void flush() {
        if (player != null) {
            this.getPlayer().flush();
        }
    }
}
