package com.cometproject.api.game.players;

import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.game.players.data.IPlayerSettings;
import com.cometproject.api.game.players.data.IPlayerStatistics;
import com.cometproject.api.game.players.data.components.*;
import com.cometproject.api.game.rooms.entities.PlayerRoomEntity;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.ISession;

import java.util.List;
import java.util.Set;

/**
 * Defines the i player contract for the player subsystem.
 */
public interface IPlayer {
    String INFINITE_BALANCE = "999999999";

    /**
     * Executes the dispose operation for this player contract.
     */
    void dispose();

    /**
     * Executes the send balance operation for this player contract.
     */
    void sendBalance();

    /**
     * Executes the compose credit balance operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    IMessageComposer composeCreditBalance();

    /**
     * Executes the compose currencies balance operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    IMessageComposer composeCurrenciesBalance();

    /**
     * Loads room data for this player contract.
     *
     * @param id Id value supplied by the caller.
     * @param password Password value supplied by the caller.
     */
    void loadRoom(int id, String password);

    /**
     * Executes the poof operation for this player contract.
     */
    void poof();

    /**
     * Executes the ignore player operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     */
    void ignorePlayer(int playerId);

    /**
     * Executes the unignore player operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     */
    void unignorePlayer(int playerId);

    /**
     * Executes the ignores operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean ignores(int playerId);

    /**
     * Returns the rooms associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getRooms();

    /**
     * Returns the rooms with rights associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getRoomsWithRights();

    /**
     * Updates the rooms value for this player contract.
     *
     * @param rooms Rooms value supplied by the caller.
     */
    void setRooms(List<Integer> rooms);

    /**
     * Updates the session value for this player contract.
     *
     * @param client Client value supplied by the caller.
     */
    void setSession(ISession client);

    /**
     * Executes the anti spam operation for this player contract.
     *
     * @param name Name value supplied by the caller.
     * @param expire Expire value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean antiSpam(String name, double expire);

    /**
     * Returns the entity associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerRoomEntity getEntity();

    /**
     * Returns the session associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISession getSession();

    /**
     * Returns the data associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayerData getData();

    /**
     * Returns the settings associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayerSettings getSettings();

    /**
     * Returns the stats associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayerStatistics getStats();

    /**
     * Returns the permissions associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerPermissions getPermissions();

    /**
     * Returns the achievements associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerAchievements getAchievements();

    /**
     * Returns the messenger associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerMessenger getMessenger();

    /**
     * Returns the inventory associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerInventory getInventory();

    /**
     * Returns the subscription associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    SubsComponent getSubscription();

    /**
     * Returns the relationships associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerRelationships getRelationships();

    /**
     * Returns the bots associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerBots getBots();

    /**
     * Returns the pets associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerPets getPets();

    /**
     * Returns the quests associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerQuests getQuests();

    /**
     * Returns the id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Executes the send notif operation for this player contract.
     *
     * @param title Title value supplied by the caller.
     * @param message Message value supplied by the caller.
     */
    void sendNotif(String title, String message);

    /**
     * Executes the send bubble operation for this player contract.
     *
     * @param title Title value supplied by the caller.
     * @param message Message value supplied by the caller.
     */
    void sendBubble(String title, String message);

    /**
     * Executes the send motd operation for this player contract.
     *
     * @param message Message value supplied by the caller.
     */
    void sendMotd(String message);

    /**
     * Indicates whether teleporting is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isTeleporting();

    /**
     * Returns the teleport id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getTeleportId();

    /**
     * Updates the teleport id value for this player contract.
     *
     * @param teleportId Teleport id value supplied by the caller.
     */
    void setTeleportId(long teleportId);

    /**
     * Returns the room last message time associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getRoomLastMessageTime();

    /**
     * Updates the room last message time value for this player contract.
     *
     * @param roomLastMessageTime Room last message time value supplied by the caller.
     */
    void setRoomLastMessageTime(long roomLastMessageTime);

    /**
     * Returns the room flood time associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    double getRoomFloodTime();

    /**
     * Updates the room flood time value for this player contract.
     *
     * @param roomFloodTime Room flood time value supplied by the caller.
     */
    void setRoomFloodTime(double roomFloodTime);

    /**
     * Returns the room flood flag associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRoomFloodFlag();

    /**
     * Updates the room flood flag value for this player contract.
     *
     * @param roomFloodFlag Room flood flag value supplied by the caller.
     */
    void setRoomFloodFlag(int roomFloodFlag);

    /**
     * Returns the last message associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getLastMessage();

    /**
     * Updates the last message value for this player contract.
     *
     * @param lastMessage Last message value supplied by the caller.
     */
    void setLastMessage(String lastMessage);

    /**
     * Returns the groups associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<Integer> getGroups();

    /**
     * Returns the notif cooldown associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getNotifCooldown();

    /**
     * Updates the notif cooldown value for this player contract.
     *
     * @param notifCooldown Notif cooldown value supplied by the caller.
     */
    void setNotifCooldown(int notifCooldown);

    /**
     * Returns the last room id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLastRoomId();

    /**
     * Updates the last room id value for this player contract.
     *
     * @param lastRoomId Last room id value supplied by the caller.
     */
    void setLastRoomId(int lastRoomId);

    /**
     * Returns the last gift associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLastGift();

    /**
     * Updates the last gift value for this player contract.
     *
     * @param lastGift Last gift value supplied by the caller.
     */
    void setLastGift(int lastGift);

    /**
     * Returns the messenger last message time associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getMessengerLastMessageTime();

    /**
     * Updates the messenger last message time value for this player contract.
     *
     * @param messengerLastMessageTime Messenger last message time value supplied by the caller.
     */
    void setMessengerLastMessageTime(long messengerLastMessageTime);

    /**
     * Returns the messenger flood time associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    double getMessengerFloodTime();

    /**
     * Updates the messenger flood time value for this player contract.
     *
     * @param messengerFloodTime Messenger flood time value supplied by the caller.
     */
    void setMessengerFloodTime(double messengerFloodTime);

    /**
     * Returns the messenger flood flag associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getMessengerFloodFlag();

    /**
     * Updates the messenger flood flag value for this player contract.
     *
     * @param messengerFloodFlag Messenger flood flag value supplied by the caller.
     */
    void setMessengerFloodFlag(int messengerFloodFlag);

    /**
     * Indicates whether deleting group is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isDeletingGroup();

    /**
     * Updates the deleting group value for this player contract.
     *
     * @param isDeletingGroup Is deleting group value supplied by the caller.
     */
    void setDeletingGroup(boolean isDeletingGroup);

    /**
     * Returns the deleting group attempt associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getDeletingGroupAttempt();

    /**
     * Updates the deleting group attempt value for this player contract.
     *
     * @param deletingGroupAttempt Deleting group attempt value supplied by the caller.
     */
    void setDeletingGroupAttempt(long deletingGroupAttempt);

    /**
     * Executes the bypass room auth operation for this player contract.
     *
     * @param bypassRoomAuth Bypass room auth value supplied by the caller.
     */
    void bypassRoomAuth(boolean bypassRoomAuth);

    /**
     * Indicates whether bypassing room auth is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isBypassingRoomAuth();

    /**
     * Returns the last figure update associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLastFigureUpdate();

    /**
     * Returns the last cfh associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLastCFH();

    /**
     * Updates the last cfh value for this player contract.
     *
     * @param lastCFH Last cfh value supplied by the caller.
     */
    void setLastCFH(int lastCFH);

    /**
     * Updates the last figure update value for this player contract.
     *
     * @param lastFigureUpdate Last figure update value supplied by the caller.
     */
    void setLastFigureUpdate(int lastFigureUpdate);

    /**
     * Returns the last reward associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getLastReward();

    /**
     * Updates the last reward value for this player contract.
     *
     * @param lastReward Last reward value supplied by the caller.
     */
    void setLastReward(long lastReward);

    /**
     * Returns the last diamond reward associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getLastDiamondReward();

    /**
     * Updates the last diamond reward value for this player contract.
     *
     * @param lastDiamondReward Last diamond reward value supplied by the caller.
     */
    void setLastDiamondReward(long lastDiamondReward);

    /**
     * Returns the last salary reward associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getLastSalaryReward();

    /**
     * Updates the last salary reward value for this player contract.
     *
     * @param lastSalaryReward Last salary reward value supplied by the caller.
     */
    void setLastSalaryReward(long lastSalaryReward);

    /**
     * Returns the recent purchases associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<Integer> getRecentPurchases();

    /**
     * Returns the last room created associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLastRoomCreated();

    /**
     * Updates the last room created value for this player contract.
     *
     * @param lastRoomCreated Last room created value supplied by the caller.
     */
    void setLastRoomCreated(int lastRoomCreated);


    /**
     * Executes the flush operation for this player contract.
     */
    void flush();

    /**
     * Returns the logs client staff associated with this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getLogsClientStaff();

    /**
     * Updates the logs client staff value for this player contract.
     *
     * @param logsClient Logs client value supplied by the caller.
     */
    void setLogsClientStaff(boolean logsClient);
}
