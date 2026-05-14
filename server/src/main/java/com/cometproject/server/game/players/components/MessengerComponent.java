package com.cometproject.server.game.players.components;

import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.players.data.components.PlayerMessenger;
import com.cometproject.api.game.players.data.components.messenger.IMessengerFriend;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.components.types.messenger.MessengerSearchResult;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.logging.entries.OfflineChatLogEntry;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.messenger.BuddyListMessageComposer;
import com.cometproject.server.network.messages.outgoing.messenger.FriendRequestsMessageComposer;
import com.cometproject.server.network.messages.outgoing.messenger.MessengerSearchResultsMessageComposer;
import com.cometproject.server.network.messages.outgoing.messenger.UpdateFriendStateMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.messenger.MessengerDao;
import com.cometproject.server.storage.queries.player.messenger.MessengerSearchDao;
import com.google.common.collect.Lists;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Owns messenger behavior inside the player subsystem.
 */
public class MessengerComponent extends PlayerComponent implements PlayerMessenger {
    private Map<Integer, IMessengerFriend> friends;
    private List<OfflineChatLogEntry> offlineMessages;

    private List<Integer> requests;
    private boolean initialised;

    /**
     * Creates a messenger component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public MessengerComponent(IPlayer player) {
        super(player);

        try {
            this.friends = MessengerDao.getFriendsByPlayerId(player.getId());
            this.offlineMessages = MessengerDao.getOfflineMessages(player.getId());
        } catch (Exception e) {
            LoggerFactory.getLogger(MessengerComponent.class.getName()).error("Error while loading messenger friends", e);
        }
    }

    /**
     * Releases resources owned by this player component.
     */
    public void dispose() {
        this.sendStatus(false, false);

        if (this.getRequests() != null) {
            this.requests.clear();
        }

        this.friends.clear();
        this.requests = null;
        this.friends = null;
    }

    /**
     * Executes search for this player contract.
     *
     * @param query Query supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public IMessageComposer search(String query) {
        List<MessengerSearchResult> currentFriends = Lists.newArrayList();
        List<MessengerSearchResult> otherPeople = Lists.newArrayList();

        try {
            for (MessengerSearchResult searchResult : MessengerSearchDao.performSearch(query)) {
                if (this.getFriendById(searchResult.getId()) != null) {
                    currentFriends.add(searchResult);
                } else {
                    otherPeople.add(searchResult);
                }
            }
        } catch (Exception e) {
            this.getPlayer().getSession().getLogger().error("Error while searching for players", e);
        }

        return new MessengerSearchResultsMessageComposer(currentFriends, otherPeople);
    }

    /**
     * Adds request to this player contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void addRequest(int playerId) {
        this.getRequests().add(playerId);

        this.getPlayer().flush();
    }

    /**
     * Adds friend to this player contract.
     *
     * @param friend Friend supplied by the caller.
     */
    @Override
    public void addFriend(IMessengerFriend friend) {
        if(this.getFriends().containsKey(friend.getUserId())) {
            return;
        }

        this.getFriends().put(friend.getUserId(), friend);
        this.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_23, 1);
    }

    /**
     * Removes friend from this player contract.
     *
     * @param userId User id supplied by the caller.
     */
    @Override
    public void removeFriend(int userId) {
        if (!this.friends.containsKey(userId)) {
            return;
        }

        this.friends.remove(userId);

        MessengerDao.deleteFriendship(this.getPlayer().getId(), userId);
        this.getPlayer().getSession().send(new UpdateFriendStateMessageComposer(-1, userId));

        this.getPlayer().flush();
    }

    /**
     * Returns the request by sender for this player contract.
     *
     * @param sender Sender supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Integer getRequestBySender(int sender) {
        for (Integer request : requests) {
            if (request == sender) {
                return request;
            }
        }

        return null;
    }

    /**
     * Executes broadcast for this player contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void broadcast(IMessageComposer msg) {
        for (IMessengerFriend friend : this.getFriends().values()) {
            if (!friend.isOnline() || friend.getUserId() == this.getPlayer().getId()) {
                continue;
            }

            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(friend.getUserId());

            if (session != null && session.getPlayer().getMessenger().isInitialised())
                session.send(msg);
        }
    }

    /**
     * Executes broadcast for this player contract.
     *
     * @param friends Friends supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void broadcast(List<Integer> friends, IMessageComposer msg) {
        for (int friendId : friends) {
            if (friendId == this.getPlayer().getId() || !this.friends.containsKey(friendId) || !this.friends.get(friendId).isOnline()) {
                continue;
            }

            IMessengerFriend friend = this.friends.get(friendId);

            if (!friend.isOnline() || friend.getUserId() == this.getPlayer().getId()) {
                continue;
            }

            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(friend.getUserId());

            if (session != null && session.getPlayer() != null)
                session.send(msg);
        }
    }

    /**
     * Indicates whether this player contract has request from.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasRequestFrom(int playerId) {
        if (this.requests == null) return false;

        for (Integer messengerRequest : this.requests) {
            if (messengerRequest == playerId)
                return true;
        }

        return false;
    }

    /**
     * Returns the request avatars for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<PlayerAvatar> getRequestAvatars() {
        List<PlayerAvatar> avatars = Lists.newArrayList();

        for (int playerId : this.getRequests()) {
            PlayerAvatar playerAvatar = PlayerManager.getInstance().getAvatarByPlayerId(playerId, PlayerAvatar.USERNAME_FIGURE);

            if (playerAvatar != null) {
                avatars.add(playerAvatar);
            }
        }

        return avatars;
    }

    /**
     * Executes clear requests for this player contract.
     */
    @Override
    public void clearRequests() {
        this.requests.clear();

        this.getPlayer().flush();
    }

    /**
     * Executes send offline for this player contract.
     *
     * @param friend Friend supplied by the caller.
     * @param online Online supplied by the caller.
     * @param inRoom In room supplied by the caller.
     */
    @Override
    public void sendOffline(int friend, boolean online, boolean inRoom) {
        this.getPlayer().getSession().send(new UpdateFriendStateMessageComposer(PlayerManager.getInstance().getAvatarByPlayerId(friend, PlayerAvatar.USERNAME_FIGURE_MOTTO), online, inRoom, this.getPlayer().getRelationships().get(friend)));

        this.getPlayer().flush();
    }

    /**
     * Executes send status for this player contract.
     *
     * @param online Online supplied by the caller.
     * @param inRoom In room supplied by the caller.
     */
    @Override
    public void sendStatus(boolean online, boolean inRoom) {
        if (this.getPlayer() == null || this.getPlayer().getSettings() == null) {
            return;
        }

        if (this.getPlayer().getSettings().getHideOnline()) {
            return;
        }

        for (IMessengerFriend friend : this.getFriends().values()) {
            if (!friend.isOnline() || friend.getUserId() == this.getPlayer().getId()) {
                continue;
            }

            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(friend.getUserId());

            if (session != null && session.getPlayer().getMessenger().isInitialised())
                session.send(new UpdateFriendStateMessageComposer(this.getPlayer().getData(), online, inRoom, session.getPlayer().getRelationships().get(this.getPlayer().getId())));
        }

        this.getPlayer().flush();
    }

    /**
     * Returns the friend by id for this player contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IMessengerFriend getFriendById(int id) {
        return this.getFriends().get(id);
    }

    /**
     * Returns the friends for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, IMessengerFriend> getFriends() {
        return this.friends;
    }

    /**
     * Returns the requests for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<Integer> getRequests() {
        if (this.requests == null) {
            this.requests = MessengerDao.getRequestsByPlayerId(this.getPlayer().getId());
        }

        return this.requests;
    }

    /**
     * Removes request from this player contract.
     *
     * @param request Request supplied by the caller.
     */
    @Override
    public void removeRequest(Integer request) {
        this.getRequests().remove(request);
    }

    /**
     * Indicates whether initialised applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isInitialised() {
        return initialised;
    }

    /**
     * Updates the initialised for this player contract.
     *
     * @param initialised Initialised supplied by the caller.
     */
    @Override
    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    /**
     * Executes initialise for this player contract.
     */
    public void initialise() {
        this.getPlayer().getSession().sendQueue(new BuddyListMessageComposer((Player) this.getPlayer(),
                this.getFriends(),
                this.getPlayer().getPermissions().getRank().messengerStaffChat(),
                this.getPlayer().getPermissions().getRank().messengerModChat(),
                this.getPlayer().getPermissions().getRank().messengerLogChat(),
                this.getPlayer().getPermissions().getRank().messengerAlfaChat(),
                this.getPlayer().getGroups()));

        this.getPlayer().getSession().sendQueue(new FriendRequestsMessageComposer(this.getRequestAvatars()));
        this.getPlayer().getSession().flush();

        if (!this.getPlayer().getAchievements().hasStartedAchievement(AchievementType.ACH_23)) {
            this.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_23, this.getFriends().size());
        }

        this.setInitialised(true);
    }
}
