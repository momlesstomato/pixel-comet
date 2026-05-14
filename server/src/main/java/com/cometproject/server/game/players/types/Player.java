package com.cometproject.server.game.players.types;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.components.PlayerInventory;
import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.game.quests.QuestType;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.guides.GuideManager;
import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.game.guides.types.HelperSession;
import com.cometproject.server.game.items.crafting.CraftingMachine;
import com.cometproject.server.game.landing.LandingManager;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.components.*;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.quests.QuestManager;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.survival.types.QueueData;
import com.cometproject.server.game.rooms.types.components.types.ChatMessageColour;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.quests.QuestStartedMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.engine.HotelViewMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.details.NuxStatusMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.purse.CurrenciesMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.purse.SendCreditsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.storage.queries.catalog.CatalogDao;
import com.cometproject.server.storage.queries.landing.LandingDao;
import com.cometproject.server.storage.queries.permissions.PermissionsDao;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.common.collect.Sets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Describes player behavior for the player subsystem.
 */
public class Player implements IPlayer {

    private final PermissionComponent permissions;
    private final InventoryComponent inventory;
    private final SubscriptionComponent subscription;
    private final MessengerComponent messenger;
    private RelationshipComponent relationships;
    private final InventoryBotComponent bots;
    private final PetComponent pets;
    private final QuestComponent quests;
    private final AchievementComponent achievements;
    private final NavigatorComponent navigator;
    private final WardrobeComponent wardrobe;
    private CraftingMachine lastCraftingMachine;

    private boolean online;

    public boolean cancelPageOpen = false;
    public boolean isDisposed = false;
    private boolean logsClient;
    public int lastBannedListRequest = 0;
    private final int id;
    private PlayerSettings settings;
    private PlayerData data;
    private final PlayerStatistics stats;
    private final MisteryComponent mistery;
    private final RentableComponent rentable;
    private PlayerEntity entity;
    private Session session;
    private HelperSession helperSession;
    private List<Integer> rooms = new ArrayList<>();
    private List<Integer> roomsWithRights = new ArrayList<>();
    private List<Integer> enteredRooms = new ArrayList<>();
    private Set<Integer> groups = Sets.newConcurrentHashSet();
    private List<Integer> ignoredPlayers = new ArrayList<>();
    private List<String> groupWhispers = new ArrayList<>();
    private long roomLastMessageTime = 0;
    private double roomFloodTime = 0;
    private int lastForumPost = 0;
    private long lastRoomRequest = 0;
    private long lastBadgeUpdate = 0;
    private int lastFigureUpdate = 0;
    private int lastHealIncome = 0;
    private int lastCFH = 0;
    private int roomFloodFlag = 0;
    private long messengerLastMessageTime = 0;
    private double messengerFloodTime = 0;
    private boolean hasJob = false;
    private int messengerFloodFlag = 0;
    private boolean usernameConfirmed = false;
    private long teleportId = 0;
    private int teleportRoomId = 0;
    private String lastMessage = "";
    private int lastVoucherRedeemAttempt = 0;
    private int voucherRedeemAttempts = 0;
    private int notifCooldown = 0;
    private int lastRoomId;
    private int lastGift = 0;
    private int lastRoomCreated = 0;
    private boolean isDeletingGroup = false;
    private long deletingGroupAttempt = 0;
    private boolean bypassRoomAuth;
    private long lastDiamondReward = 0;
    private long lastSalaryReward = 0;
    private long lastReward = 0;
    private boolean invisible = false;
    private int lastTradePlayer = 0;
    private long lastTradeTime = 0;
    private int lastTradeFlag = 0;
    private long lastTradeFlood = 0;
    private long lastPhotoTaken = 0;
    private long lastPostItPlaced = 0;
    private long lastDuelSuggestion = 0;
    private long lastDuel = 0;
    private long lastProductAdded = 0;
    private double itemPlacementHeight = -1;
    private int itemPlacementRotation = -1;
    private int itemPlacementState = -1;
    private int groupCreationType = 0;
    private int bubbleId = 0;
    private boolean isBuilding = false;
    private boolean isSearchFurni = false;
    private boolean isDeveloping = false;
    private String RPSRival = "";
    private String RPSrequest = "";
    private int RPSamount;
    private int RPSselection;
    private Set<Integer> recentPurchases;
    private boolean[] calendarGifts;
    private QueueData queueData;

    private Set<Integer> listeningPlayers = Sets.newConcurrentHashSet();
    private Set<String> eventLogCategories = Sets.newConcurrentHashSet();

    private ChatMessageColour chatMessageColour = null;
    private HelpRequest helpRequest = null;

    private boolean petsMuted;
    private boolean botsMuted;
    private int shadowStatus;

    private String lastPhoto = null;
    private String lastPurchasedPhoto = null;
    private int roomQueueId = 0;
    private int survivalRoomId = 0;
    private int spectatorRoomId = 0;
    private Map<String, Long> antiSpam = new ConcurrentHashMap<>();

    private final List<PlayerMention> mentions = new ArrayList<PlayerMention>();

    /**
     * Creates a player instance for the player subsystem.
     *
     * @param data Data supplied by the caller.
     * @param isFallback Is fallback supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public Player(ResultSet data, boolean isFallback) throws SQLException {
        this.id = data.getInt("playerId");

        this.data = new PlayerData(data, this);
        this.refreshCurrencyInventorySnapshot();

        if (isFallback) {
            this.settings = PlayerDao.getSettingsById(this.id);
            this.stats = PlayerDao.getStatisticsById(this.id);
        } else {
            this.settings = new PlayerSettings(data, true, this);
            this.stats = new PlayerStatistics(data, true, this);
        }

        this.mistery = PlayerDao.getMisteryById(this.id);
        this.rentable = new RentableComponent(this);
        this.permissions = new PermissionComponent(this);
        this.inventory = new InventoryComponent(this);
        this.messenger = new MessengerComponent(this);
        this.subscription = new SubscriptionComponent(this);
        this.relationships = new RelationshipComponent(this);
        this.bots = new InventoryBotComponent(this);
        this.pets = new PetComponent(this);
        this.quests = new QuestComponent(this);
        this.achievements = new AchievementComponent(this);
        this.navigator = new NavigatorComponent(this);
        this.wardrobe = new WardrobeComponent(this);

        StorageContext.getCurrentContext().getGroupRepository().getGroupIdsByPlayerId(this.id,
                groups -> this.groups.addAll(groups));

        this.entity = null;
        this.lastReward = Comet.getTime();
        this.lastDiamondReward = Comet.getTime();
        this.lastSalaryReward = Comet.getTime();

        //this.addObserver(new PlayerObserver());
        this.getCalendarGifts();
    }

    private void refreshCurrencyInventorySnapshot() {
        final ICurrencyService currencyService = this.currencyService();
        if (currencyService == null || !currencyService.readFromInventory()) {
            return;
        }

        currencyService.balances(this.id).forEach((currencyCode, balance) ->
                this.data.applyCurrencyBalance(currencyCode, balance));
    }

    /**
     * Releases resources owned by this player component.
     */
    @Override
    public void dispose() {
        this.setOnline(false);
        flush();

        if (this.getEntity() != null) {
            try {
                this.getEntity().leaveRoom(true, false, false);
            } catch (Exception e) {
                // Player failed to leave room
                this.getSession().getLogger().error("Error while disposing entity when player disconnects", e);
            }
        }

        if(this.getSettings() != null) {
            PlayerDao.saveBubbleId(this.getSettings().getBubbleId(), this.getId());
        }

        if (this.helperSession != null) {
            GuideManager.getInstance().finishPlayerDuty(this.helperSession);
            this.helperSession = null;
        }

        if (this.data != null){
            this.getData().save();
        }

        this.getPets().dispose();
        this.getSettings().dispose();
        this.getBots().dispose();
        this.getInventory().dispose();
        this.getMessenger().dispose();
        this.getRelationships().dispose();
        this.getQuests().dispose();
        this.getNavigator().dispose();
        this.getWardrobe().dispose();

        this.session.getLogger().debug(this.getData().getUsername() + " logged out");

        PlayerDao.updatePlayerStatus(this, this.isOnline(), false);

        this.rooms.clear();
        this.rooms = null;

        this.roomsWithRights.clear();
        this.roomsWithRights = null;

        this.groups.clear();
        this.groups = null;

        this.ignoredPlayers.clear();
        this.ignoredPlayers = null;

        this.groupWhispers.clear();
        this.groupWhispers = null;

        this.enteredRooms.clear();
        this.enteredRooms = null;

        this.antiSpam.clear();
        this.antiSpam = null;

        this.eventLogCategories.clear();
        this.eventLogCategories = null;

        if (this.recentPurchases != null) {
            this.recentPurchases.clear();
            this.recentPurchases = null;
        }

        this.listeningPlayers.clear();

        this.settings = null;
        this.data = null;
        this.isDisposed = true;
    }

    /**
     * Executes send balance for this player contract.
     */
    @Override
    public void sendBalance() {
        session.send(composeCurrenciesBalance());
        session.send(composeCreditBalance());
    }

    /**
     * Indicates whether disposed applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isDisposed() {
        return isDisposed;
    }

    /**
     * Executes flush pets for this player contract.
     */
    public void flushPets() {
        this.pets.clearPets();
    }

    /**
     * Executes send notif for this player contract.
     *
     * @param title Title supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void sendNotif(String title, String message) {
        session.send(new AdvancedAlertMessageComposer(title, message));
    }

    /**
     * Executes send bubble for this player contract.
     *
     * @param image Image supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void sendBubble(String image, String message) {
        session.send(new NotificationMessageComposer(image, message));
    }

    /**
     * Executes send motd for this player contract.
     *
     * @param message Message supplied by the caller.
     */
    @Override
    public void sendMotd(String message) {
        session.send(new MotdNotificationMessageComposer(message));
    }

    /**
     * Executes compose credit balance for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public MessageComposer composeCreditBalance() {
        if (CometSettings.playerInfiniteBalance) {
            return new SendCreditsMessageComposer(INFINITE_BALANCE);
        }

        final ICurrencyService currencyService = this.currencyService();
        if (currencyService != null && currencyService.readFromInventory()) {
            return new SendCreditsMessageComposer(Long.toString(currencyService.balance(this.id, "credits")));
        }

        return new SendCreditsMessageComposer(Integer.toString(session.getPlayer().getData().getCredits()));
    }

    /**
     * Executes compose currencies balance for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public MessageComposer composeCurrenciesBalance() {
        final ICurrencyService currencyService = this.currencyService();
        if (currencyService != null && currencyService.readFromInventory()) {
            return new CurrenciesMessageComposer(currencyService.protocolVisibleBalances(this.id));
        }

        return new CurrenciesMessageComposer(Map.of());
    }

    private ICurrencyService currencyService() {
        try {
            return CometBootstrap.resolve(ICurrencyService.class);
        } catch (IllegalStateException exception) {
            return null;
        }
    }

    /**
     * Loads room for this player contract.
     *
     * @param id Id supplied by the caller.
     * @param password Password supplied by the caller.
     */
    @Override
    public void loadRoom(int id, String password) {
        if (!this.usernameConfirmed) {
            session.send(new HotelViewMessageComposer());
            return;
        }

        if (entity != null && entity.getRoom() != null) {
            entity.leaveRoom(true, false, false);
            setEntity(null);
        }

        Room room = RoomManager.getInstance().get(id);

        if (room == null) {
            session.send(new HotelViewMessageComposer());
            return;
        }

        if (room.getEntities() == null) {
            return;
        }

        if (room.getEntities().getEntityByPlayerId(this.id) != null) {
            room.getEntities().getEntityByPlayerId(this.id).leaveRoom(true, false, false);
        }

        PlayerEntity playerEntity = room.getEntities().createEntity(this);
        setEntity(playerEntity);

        if (!playerEntity.joinRoom(room, password)) {
            setEntity(null);
        }

        if (this.getData().getQuestId() != 0) {
            IQuest quest = QuestManager.getInstance().getById(this.getData().getQuestId());

            if (quest != null && this.getQuests().hasStartedQuest(quest.getId()) && !this.getQuests().hasCompletedQuest(quest.getId())) {
                this.getSession().send(new QuestStartedMessageComposer(quest, this));

                if (quest.getType() == QuestType.SOCIAL_VISIT) {
                    this.getQuests().progressQuest(QuestType.SOCIAL_VISIT);
                }
            }
        }

        if(this.getSettings().getNuxStatus() == 0){
            this.getSession().send(new NuxStatusMessageComposer(2));
            this.getSession().send(new MassEventMessageComposer("helpBubble/add/CHAT_INPUT/" + Locale.getOrDefault("CHAT_INPUT", "¡Haz click aquí para escribir!")));
        }

        if (!this.enteredRooms.contains(id) && !this.rooms.contains(id)) {
            this.enteredRooms.add(id);
        }

        if (getSettings().hasPersonalStaff()) {
            List<Map.Entry<Integer, Integer>> rankPermList = new ArrayList<>(PermissionsDao.getEffects().entrySet());
            rankPermList.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

            for (Map.Entry<Integer, Integer> entry : rankPermList) {

                if (this.getPermissions().getRank().getId() < entry.getValue())
                    continue;

                if (this.getSettings().hasPersonalStaff()) {
                    this.getEntity().applyEffect(new PlayerEffect(entry.getKey()));
                } else
                    this.getEntity().applyEffect(new PlayerEffect(0));

                break;
            }
        }

    }

    /**
     * Executes poof for this player contract.
     */
    @Override
    public void poof() {
        if (this.getEntity() != null && this.getEntity().getRoom() != null && this.getEntity().getRoom().getEntities() != null) {
            this.getSession().send(new UpdateInfoMessageComposer(this.getEntity().getId(), this.getData().getFigure(), this.getData().getGender(), this.getData().getMotto(), this.getData().getAchievementPoints()));
            this.getEntity().unIdle();
            this.getEntity().getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(this.getEntity()));
        }
    }

    /**
     * Executes ignore player for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void ignorePlayer(int playerId) {
        if (this.ignoredPlayers == null) {
            this.ignoredPlayers = new ArrayList<>();
        }

        this.ignoredPlayers.add(playerId);
    }

    /**
     * Executes unignore player for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void unignorePlayer(int playerId) {
        this.ignoredPlayers.remove((Integer) playerId);
    }

    /**
     * Executes ignores for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean ignores(int playerId) {
        return this.ignoredPlayers != null && this.ignoredPlayers.contains(playerId);
    }

    /**
     * Returns the group whispers for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public List<String> getGroupWhispers(){
        return this.groupWhispers;
    }

    /**
     * Handles group whisper for this player contract.
     *
     * @param username Username supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean handleGroupWhisper(String username) {
        if (this.groupWhispers == null) {
            this.groupWhispers = new ArrayList<>();
        }

        if(!this.isGroupWhisperValid(username)) {
            this.groupWhispers.add(username);
            return true;
        } else {
            this.groupWhispers.remove(username);
            return false;
        }
    }

    /**
     * Returns the player by group chat for this player contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Player getPlayerByGroupChat(String username){
        if(this.groupWhispers.contains(username)){
            Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);
            if(session == null){
                this.handleGroupWhisper(username);
                return null;
            }

            return session.getPlayer();
        }

        return null;
    }


    /**
     * Indicates whether group whisper valid applies to this player contract.
     *
     * @param username Username supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isGroupWhisperValid(String username) {
        return this.groupWhispers != null && this.groupWhispers.contains(username);
    }

    /**
     * Returns the shadow status for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getShadowStatus() {
        return shadowStatus;
    }

    /**
     * Updates the shadow for this player contract.
     *
     * @param status Status supplied by the caller.
     */
    public void setShadow(int status) {
        this.shadowStatus = status;
    }

    /**
     * Returns the rooms for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<Integer> getRooms() {
        return rooms;
    }

    /**
     * Updates the rooms for this player contract.
     *
     * @param rooms Rooms supplied by the caller.
     */
    @Override
    public void setRooms(List<Integer> rooms) {
        this.rooms = rooms;

        flush();
    }

    /**
     * Executes anti spam for this player contract.
     *
     * @param name Name supplied by the caller.
     * @param expire Expire supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean antiSpam(String name, double expire) {

        if (this.antiSpam.containsKey(name)) {

            if (System.currentTimeMillis() - this.antiSpam.get(name) < (expire * 1000)) {
                return true;
            }

            this.antiSpam.replace(name, System.currentTimeMillis());
        } else {
            this.antiSpam.put(name, System.currentTimeMillis());
        }

        return false;
    }

    /**
     * Returns the rooms with rights for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<Integer> getRoomsWithRights() {
        return roomsWithRights;
    }

    //    @Override
    /**
     * Returns the entity for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerEntity getEntity() {
        return this.entity;
    }

    //    @Override
    /**
     * Updates the entity for this player contract.
     *
     * @param avatar Avatar supplied by the caller.
     */
    public void setEntity(PlayerEntity avatar) {
        this.entity = avatar;

        flush();
    }

    /**
     * Returns the session for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Session getSession() {
        return this.session;
    }

    /**
     * Updates the session for this player contract.
     *
     * @param client Client supplied by the caller.
     */
    @Override
    public void setSession(ISession client) {
        this.session = ((Session) client);
    }

    /**
     * Returns the data for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PlayerData getData() {
        return this.data;
    }

    /**
     * Updates the data for this player contract.
     *
     * @param playerData Player data supplied by the caller.
     */
    public void setData(PlayerData playerData) {
        this.data = playerData;
    }

    /**
     * Returns the stats for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PlayerStatistics getStats() {
        return this.stats;
    }

    /**
     * Returns the permissions for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PermissionComponent getPermissions() {
        return this.permissions;
    }

    //    @Override
    /**
     * Returns the messenger for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public MessengerComponent getMessenger() {
        return this.messenger;
    }

    /**
     * Returns the rentable for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public RentableComponent getRentable() { return this.rentable; }

    //    @Override
    /**
     * Returns the inventory for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerInventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns the subscription for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public SubscriptionComponent getSubscription() {
        return this.subscription;
    }

    /**
     * Returns the relationships for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RelationshipComponent getRelationships() {
        return this.relationships;
    }

    //    @Override
    /**
     * Returns the bots for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public InventoryBotComponent getBots() {
        return this.bots;
    }

    //    @Override
    /**
     * Returns the pets for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public PetComponent getPets() {
        return this.pets;
    }

    //    @Override
    /**
     * Returns the quests for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public QuestComponent getQuests() {
        return quests;
    }

    /**
     * Returns the achievements for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public AchievementComponent getAchievements() {
        return achievements;
    }

    /**
     * Returns the settings for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PlayerSettings getSettings() {
        return this.settings;
    }

    /**
     * Returns the mistery for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public MisteryComponent getMistery() {
        return this.mistery;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Indicates whether teleporting applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isTeleporting() {
        return this.teleportId != 0;
    }

    /**
     * Returns the teleport id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getTeleportId() {
        return this.teleportId;
    }

    /**
     * Updates the teleport id for this player contract.
     *
     * @param teleportId Teleport id supplied by the caller.
     */
    @Override
    public void setTeleportId(long teleportId) {
        this.teleportId = teleportId;
    }

    /**
     * Returns the room last message time for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getRoomLastMessageTime() {
        return roomLastMessageTime;
    }

    /**
     * Updates the room last message time for this player contract.
     *
     * @param roomLastMessageTime Room last message time supplied by the caller.
     */
    @Override
    public void setRoomLastMessageTime(long roomLastMessageTime) {
        this.roomLastMessageTime = roomLastMessageTime;
    }

    /**
     * Returns the room flood time for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public double getRoomFloodTime() {
        return roomFloodTime;
    }

    /**
     * Updates the room flood time for this player contract.
     *
     * @param roomFloodTime Room flood time supplied by the caller.
     */
    @Override
    public void setRoomFloodTime(double roomFloodTime) {
        this.roomFloodTime = roomFloodTime;
    }

    /**
     * Returns the room flood flag for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRoomFloodFlag() {
        return roomFloodFlag;
    }

    /**
     * Updates the room flood flag for this player contract.
     *
     * @param roomFloodFlag Room flood flag supplied by the caller.
     */
    @Override
    public void setRoomFloodFlag(int roomFloodFlag) {
        this.roomFloodFlag = roomFloodFlag;
    }

    /**
     * Returns the last message for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Updates the last message for this player contract.
     *
     * @param lastMessage Last message supplied by the caller.
     */
    @Override
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * Returns the groups for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Set<Integer> getGroups() {
        return groups == null ? Sets.newHashSet() : groups;
    }

    /**
     * Returns the notif cooldown for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getNotifCooldown() {
        return this.notifCooldown;
    }

    /**
     * Updates the notif cooldown for this player contract.
     *
     * @param notifCooldown Notif cooldown supplied by the caller.
     */
    @Override
    public void setNotifCooldown(int notifCooldown) {
        this.notifCooldown = notifCooldown;
    }

    /**
     * Returns the last room id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLastRoomId() {
        return lastRoomId;
    }

    /**
     * Updates the last room id for this player contract.
     *
     * @param lastRoomId Last room id supplied by the caller.
     */
    @Override
    public void setLastRoomId(int lastRoomId) {
        this.lastRoomId = lastRoomId;

        flush();
    }

    /**
     * Returns the last gift for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLastGift() {
        return lastGift;
    }

    /**
     * Updates the last gift for this player contract.
     *
     * @param lastGift Last gift supplied by the caller.
     */
    @Override
    public void setLastGift(int lastGift) {
        this.lastGift = lastGift;
    }

    /**
     * Returns the messenger last message time for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getMessengerLastMessageTime() {
        return messengerLastMessageTime;
    }

    /**
     * Updates the messenger last message time for this player contract.
     *
     * @param messengerLastMessageTime Messenger last message time supplied by the caller.
     */
    @Override
    public void setMessengerLastMessageTime(long messengerLastMessageTime) {
        this.messengerLastMessageTime = messengerLastMessageTime;
    }

    /**
     * Returns the messenger flood time for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public double getMessengerFloodTime() {
        return messengerFloodTime;
    }

    /**
     * Updates the messenger flood time for this player contract.
     *
     * @param messengerFloodTime Messenger flood time supplied by the caller.
     */
    @Override
    public void setMessengerFloodTime(double messengerFloodTime) {
        this.messengerFloodTime = messengerFloodTime;
    }

    /**
     * Returns the messenger flood flag for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getMessengerFloodFlag() {
        return messengerFloodFlag;
    }

    /**
     * Updates the messenger flood flag for this player contract.
     *
     * @param messengerFloodFlag Messenger flood flag supplied by the caller.
     */
    @Override
    public void setMessengerFloodFlag(int messengerFloodFlag) {
        this.messengerFloodFlag = messengerFloodFlag;
    }

    /**
     * Indicates whether deleting group applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isDeletingGroup() {
        return isDeletingGroup;
    }

    /**
     * Updates the deleting group for this player contract.
     *
     * @param isDeletingGroup Is deleting group supplied by the caller.
     */
    @Override
    public void setDeletingGroup(boolean isDeletingGroup) {
        this.isDeletingGroup = isDeletingGroup;
    }

    /**
     * Returns the deleting group attempt for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getDeletingGroupAttempt() {
        return deletingGroupAttempt;
    }

    /**
     * Updates the deleting group attempt for this player contract.
     *
     * @param deletingGroupAttempt Deleting group attempt supplied by the caller.
     */
    @Override
    public void setDeletingGroupAttempt(long deletingGroupAttempt) {
        this.deletingGroupAttempt = deletingGroupAttempt;
    }

    /**
     * Executes bypass room auth for this player contract.
     *
     * @param bypassRoomAuth Bypass room auth supplied by the caller.
     */
    @Override
    public void bypassRoomAuth(final boolean bypassRoomAuth) {
        this.bypassRoomAuth = bypassRoomAuth;
    }

    /**
     * Indicates whether bypassing room auth applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isBypassingRoomAuth() {
        return bypassRoomAuth;
    }

    /**
     * Returns the last figure update for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLastFigureUpdate() {
        return lastFigureUpdate;
    }

    /**
     * Returns the last cfh for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLastCFH() {
        return lastCFH;
    }

    /**
     * Updates the last cfh for this player contract.
     *
     * @param lastCFH Last cfh supplied by the caller.
     */
    @Override
    public void setLastCFH(int lastCFH) {
        this.lastCFH = lastCFH;
    }

    /**
     * Updates the last figure update for this player contract.
     *
     * @param lastFigureUpdate Last figure update supplied by the caller.
     */
    @Override
    public void setLastFigureUpdate(int lastFigureUpdate) {
        this.lastFigureUpdate = lastFigureUpdate;
    }

    /**
     * Updates the last heal income for this player contract.
     *
     * @param lastHealIncome Last heal income supplied by the caller.
     */
    public void setLastHealIncome(int lastHealIncome) {
        this.lastHealIncome = lastHealIncome;
    }

    /**
     * Returns the last heal income for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastHealIncome() {
        return lastHealIncome;
    }

    /**
     * Returns the teleport room id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTeleportRoomId() {
        return teleportRoomId;
    }

    /**
     * Updates the teleport room id for this player contract.
     *
     * @param teleportRoomId Teleport room id supplied by the caller.
     */
    public void setTeleportRoomId(int teleportRoomId) {
        this.teleportRoomId = teleportRoomId;
    }

    /**
     * Returns the last reward for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getLastReward() {
        return lastReward;
    }

    /**
     * Updates the last reward for this player contract.
     *
     * @param lastReward Last reward supplied by the caller.
     */
    @Override
    public void setLastReward(long lastReward) {
        this.lastReward = lastReward;
    }

    /**
     * Returns the last diamond reward for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getLastDiamondReward() {
        return lastDiamondReward;
    }

    /**
     * Updates the last diamond reward for this player contract.
     *
     * @param lastDiamondReward Last diamond reward supplied by the caller.
     */
    @Override
    public void setLastDiamondReward(long lastDiamondReward) {
        this.lastDiamondReward = lastDiamondReward;
    }

    /**
     * Returns the last salary reward for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getLastSalaryReward() {
        return lastSalaryReward;
    }

    /**
     * Updates the last salary reward for this player contract.
     *
     * @param lastSalaryReward Last salary reward supplied by the caller.
     */
    @Override
    public void setLastSalaryReward(long lastSalaryReward) {
        this.lastSalaryReward = lastSalaryReward;
    }

    /**
     * Returns the last forum post for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastForumPost() {
        return lastForumPost;
    }

    /**
     * Updates the last forum post for this player contract.
     *
     * @param lastForumPost Last forum post supplied by the caller.
     */
    public void setLastForumPost(int lastForumPost) {
        this.lastForumPost = lastForumPost;
    }

    /**
     * Indicates whether this player contract has queued.
     *
     * @param id Id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasQueued(int id) {
        return roomQueueId == id;

    }

    /**
     * Updates the room queue id for this player contract.
     *
     * @param id Id supplied by the caller.
     */
    public void setRoomQueueId(int id) {
        this.roomQueueId = id;
    }

    /**
     * Updates the survival room id for this player contract.
     *
     * @param survivalRoomId Survival room id supplied by the caller.
     */
    public void setSurvivalRoomId(int survivalRoomId) {
        this.survivalRoomId = survivalRoomId;
    }

    /**
     * Returns the survival room id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSurvivalRoomId() {
        return survivalRoomId;
    }

    /**
     * Updates the queue data for this player contract.
     *
     * @param q Q supplied by the caller.
     */
    public void setQueueData(QueueData q) {
        this.queueData = q;
    }

    /**
     * Returns the queue data for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public QueueData getQueueData(){
        return this.queueData;
    }

    /**
     * Indicates whether spectating applies to this player contract.
     *
     * @param id Id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSpectating(int id) {
        return this.spectatorRoomId == id;

    }

    /**
     * Updates the spectator room id for this player contract.
     *
     * @param id Id supplied by the caller.
     */
    public void setSpectatorRoomId(int id) {
        this.spectatorRoomId = id;
    }

    /**
     * Returns the last room created for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastRoomCreated() {
        return lastRoomCreated;
    }

    /**
     * Updates the last room created for this player contract.
     *
     * @param lastRoomCreated Last room created supplied by the caller.
     */
    public void setLastRoomCreated(int lastRoomCreated) {
        this.lastRoomCreated = lastRoomCreated;
    }

    /**
     * Returns the last room request for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastRoomRequest() {
        return lastRoomRequest;
    }

    /**
     * Updates the last room request for this player contract.
     *
     * @param lastRoomRequest Last room request supplied by the caller.
     */
    public void setLastRoomRequest(long lastRoomRequest) {
        this.lastRoomRequest = lastRoomRequest;
    }

    /**
     * Returns the last badge update for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastBadgeUpdate() {
        return lastBadgeUpdate;
    }

    /**
     * Updates the last badge update for this player contract.
     *
     * @param lastBadgeUpdate Last badge update supplied by the caller.
     */
    public void setLastBadgeUpdate(long lastBadgeUpdate) {
        this.lastBadgeUpdate = lastBadgeUpdate;
    }

    /**
     * Indicates whether this player contract has job.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasJob() { return this.hasJob; }

    /**
     * Updates the has job for this player contract.
     *
     * @param value Value supplied by the caller.
     */
    public void setHasJob(boolean value) {
        this.hasJob = value;
    }

    /**
     * Indicates whether invisible applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isInvisible() {
        return invisible;
    }

    /**
     * Updates the invisible for this player contract.
     *
     * @param invisible Invisible supplied by the caller.
     */
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;

        flush();
    }

    /**
     * Returns the last trade player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastTradePlayer() {
        return lastTradePlayer;
    }

    /**
     * Updates the last trade player for this player contract.
     *
     * @param lastTradePlayer Last trade player supplied by the caller.
     */
    public void setLastTradePlayer(int lastTradePlayer) {
        this.lastTradePlayer = lastTradePlayer;
    }

    /**
     * Returns the last trade time for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastTradeTime() {
        return lastTradeTime;
    }

    /**
     * Updates the last trade time for this player contract.
     *
     * @param lastTradeTime Last trade time supplied by the caller.
     */
    public void setLastTradeTime(long lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    /**
     * Returns the last trade flag for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastTradeFlag() {
        return lastTradeFlag;
    }

    /**
     * Updates the last trade flag for this player contract.
     *
     * @param lastTradeFlag Last trade flag supplied by the caller.
     */
    public void setLastTradeFlag(int lastTradeFlag) {
        this.lastTradeFlag = lastTradeFlag;
    }

    /**
     * Returns the last trade flood for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastTradeFlood() {
        return lastTradeFlood;
    }

    /**
     * Updates the last trade flood for this player contract.
     *
     * @param lastTradeFlood Last trade flood supplied by the caller.
     */
    public void setLastTradeFlood(long lastTradeFlood) {
        this.lastTradeFlood = lastTradeFlood;
    }

    /**
     * Returns the last photo taken for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastPhotoTaken() {
        return lastPhotoTaken;
    }

    /**
     * Returns the last post it placed for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastPostItPlaced() {
        return lastPostItPlaced;
    }

    /**
     * Updates the last post it placed for this player contract.
     *
     * @param lastPostItPlaced Last post it placed supplied by the caller.
     */
    public void setLastPostItPlaced(long lastPostItPlaced) {
        this.lastPostItPlaced = lastPostItPlaced;
    }

    /**
     * Returns the last duel suggestion for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastDuelSuggestion(){ return lastDuelSuggestion; }
    /**
     * Returns the last duel for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastDuel(){ return lastDuel; }

    /**
     * Returns the last product added for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getLastProductAdded() {
        return this.lastProductAdded;
    }

    /**
     * Updates the last product added for this player contract.
     *
     * @param lastProductAdded Last product added supplied by the caller.
     */
    public void setLastProductAdded(long lastProductAdded) {
        this.lastProductAdded = lastProductAdded;
    }

    /**
     * Updates the last duel for this player contract.
     *
     * @param lastDuel Last duel supplied by the caller.
     */
    public void setLastDuel(long lastDuel) {
        this.lastDuel = lastDuel;
    }

    /**
     * Updates the last duel suggestion for this player contract.
     *
     * @param lastDuelSuggestion Last duel suggestion supplied by the caller.
     */
    public void setLastDuelSuggestion(long lastDuelSuggestion) {
        this.lastDuelSuggestion = lastDuelSuggestion;
    }

    /**
     * Updates the last photo taken for this player contract.
     *
     * @param lastPhotoTaken Last photo taken supplied by the caller.
     */
    public void setLastPhotoTaken(long lastPhotoTaken) {
        this.lastPhotoTaken = lastPhotoTaken;
    }

    /**
     * Returns the last voucher redeem attempt for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastVoucherRedeemAttempt() {
        return lastVoucherRedeemAttempt;
    }

    /**
     * Updates the last voucher redeem attempt for this player contract.
     *
     * @param lastVoucherRedeem Last voucher redeem supplied by the caller.
     */
    public void setLastVoucherRedeemAttempt(int lastVoucherRedeem) {
        this.lastVoucherRedeemAttempt = lastVoucherRedeem;
    }

    /**
     * Returns the voucher redeem attempts for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getVoucherRedeemAttempts() {
        return voucherRedeemAttempts;
    }

    /**
     * Updates the voucher redeem attempts for this player contract.
     *
     * @param voucherRedeemAttempts Voucher redeem attempts supplied by the caller.
     */
    public void setVoucherRedeemAttempts(int voucherRedeemAttempts) {
        this.voucherRedeemAttempts = voucherRedeemAttempts;
    }

    /**
     * Returns the group creation type for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGroupCreationType() {
        return this.groupCreationType;
    }

    /**
     * Updates the group creation type for this player contract.
     *
     * @param groupCreationType Group creation type supplied by the caller.
     */
    public void setGroupCreationType(int groupCreationType) {
        this.groupCreationType = groupCreationType;
    }

    /**
     * Indicates whether username confirmed applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isUsernameConfirmed() {
        return usernameConfirmed;
    }

    /**
     * Updates the username confirmed for this player contract.
     *
     * @param usernameConfirmed Username confirmed supplied by the caller.
     */
    public void setUsernameConfirmed(boolean usernameConfirmed) {
        this.usernameConfirmed = usernameConfirmed;
    }

    /**
     * Returns the event log categories for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<String> getEventLogCategories() {
        return eventLogCategories;
    }

    /**
     * Returns the chat message colour for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public ChatMessageColour getChatMessageColour() {
        return chatMessageColour;
    }

    /**
     * Updates the chat message colour for this player contract.
     *
     * @param chatMessageColour Chat message colour supplied by the caller.
     */
    public void setChatMessageColour(ChatMessageColour chatMessageColour) {
        this.chatMessageColour = chatMessageColour;
    }

    /**
     * Returns the helper session for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public HelperSession getHelperSession() {
        return helperSession;
    }

    /**
     * Updates the helper session for this player contract.
     *
     * @param helperSession Helper session supplied by the caller.
     */
    public void setHelperSession(HelperSession helperSession) {
        this.helperSession = helperSession;
    }

    /**
     * Returns the help request for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public HelpRequest getHelpRequest() {
        return helpRequest;
    }

    /**
     * Updates the help request for this player contract.
     *
     * @param helpRequest Help request supplied by the caller.
     */
    public void setHelpRequest(HelpRequest helpRequest) {
        this.helpRequest = helpRequest;
    }

    /**
     * Returns the recent purchases for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getRecentPurchases() {
        if (this.recentPurchases == null) {
            this.recentPurchases = new ConcurrentHashSet<>();

            this.recentPurchases.addAll(CatalogDao.findRecentPurchases(30, this.id));
        }

        return this.recentPurchases;
    }


    /**
     * Returns the logs client staff for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean getLogsClientStaff() { return this.logsClient;}

    /**
     * Updates the logs client staff for this player contract.
     *
     * @param logsClient Logs client supplied by the caller.
     */
    @Override
    public void setLogsClientStaff(boolean logsClient) { this.logsClient = logsClient; }

    /**
     * Returns the navigator for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public NavigatorComponent getNavigator() {
        return navigator;
    }

    /**
     * Executes pets muted for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean petsMuted() {
        return petsMuted;
    }

    /**
     * Updates the pets muted for this player contract.
     *
     * @param petsMuted Pets muted supplied by the caller.
     */
    public void setPetsMuted(boolean petsMuted) {
        this.petsMuted = petsMuted;
    }

    /**
     * Executes bots muted for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean botsMuted() {
        return botsMuted;
    }

    /**
     * Updates the bots muted for this player contract.
     *
     * @param botsMuted Bots muted supplied by the caller.
     */
    public void setBotsMuted(boolean botsMuted) {
        this.botsMuted = botsMuted;
    }

    /**
     * Returns the wardrobe for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public WardrobeComponent getWardrobe() {
        return wardrobe;
    }

    /**
     * Returns the last photo for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getLastPhoto() {
        return lastPhoto;
    }

    /**
     * Returns the last purchased photo for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getLastPurchasedPhoto() {
        return lastPurchasedPhoto;
    }

    /**
     * Updates the last purchased photo for this player contract.
     *
     * @param photo Photo supplied by the caller.
     */
    public void setLastPurchasedPhoto(String photo){
        this.lastPurchasedPhoto = photo;
    }

    /**
     * Updates the last photo for this player contract.
     *
     * @param lastPhoto Last photo supplied by the caller.
     */
    public void setLastPhoto(String lastPhoto) {
        this.lastPhoto = lastPhoto;
    }

    /**
     * Returns the item placement height for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public double getItemPlacementHeight() {
        return itemPlacementHeight;
    }

    /**
     * Returns the item placement rotation for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemPlacementRotation() {
        return itemPlacementRotation;
    }

    /**
     * Returns the item placement state for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemPlacementState() {
        return itemPlacementState;
    }

    /**
     * Updates the item placement height for this player contract.
     *
     * @param itemPlacementHeight Item placement height supplied by the caller.
     */
    public void setItemPlacementHeight(double itemPlacementHeight) {
        this.itemPlacementHeight = itemPlacementHeight;
    }

    /**
     * Updates the item placement rotation for this player contract.
     *
     * @param itemPlacementRotation Item placement rotation supplied by the caller.
     */
    public void setItemPlacementRotation(int itemPlacementRotation) {
        this.itemPlacementRotation = itemPlacementRotation;
    }

    /**
     * Updates the item placement state for this player contract.
     *
     * @param itemPlacementState Item placement state supplied by the caller.
     */
    public void setItemPlacementState(int itemPlacementState) {
        this.itemPlacementState = itemPlacementState;
    }

    /**
     * Updates the is developing for this player contract.
     *
     * @param status Status supplied by the caller.
     */
    public void setIsDeveloping(boolean status) {
        this.isDeveloping = status;
    }

    /**
     * Indicates whether developing applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isDeveloping() {
        return isDeveloping;
    }

    /**
     * Updates the is building for this player contract.
     *
     * @param status Status supplied by the caller.
     */
    public void setIsBuilding(boolean status) {
        this.isBuilding = status;
    }

    /**
     * Executes reset building for this player contract.
     */
    public void resetBuilding(){
        this.itemPlacementHeight = -1;
        this.itemPlacementRotation = -1;
        this.itemPlacementState = -1;
    }



    /**
     * Returns the is building for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getIsBuilding(){ return isBuilding;}

    /**
     * Returns the last crafting machine for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public CraftingMachine getLastCraftingMachine() {
        return this.lastCraftingMachine;
    }

    /**
     * Updates the last crafting machine for this player contract.
     *
     * @param machine Machine supplied by the caller.
     */
    public void setLastCraftingMachine(CraftingMachine machine) {
        this.lastCraftingMachine = machine;
    }

    /**
     * Returns the listening players for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getListeningPlayers() {
        return listeningPlayers;
    }

    /**
     * Updates the listening players for this player contract.
     *
     * @param listeningPlayers Listening players supplied by the caller.
     */
    public void setListeningPlayers(Set<Integer> listeningPlayers) {
        this.listeningPlayers = listeningPlayers;
    }

    /**
     * Executes flush for this player contract.
     */
    public void flush() {
        /*setChanged();
        notifyObservers();*/
    }

    /**
     * Indicates whether online applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOnline() {
        return this.online;
    }

    /**
     * Updates the online for this player contract.
     *
     * @param online Online supplied by the caller.
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Returns the rps rival for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRPSRival() { return RPSRival; }

    /**
     * Updates the rp samount for this player contract.
     *
     * @param RPSamount Rp samount supplied by the caller.
     */
    public void setRPSamount(int RPSamount) { this.RPSamount = RPSamount; }

    /**
     * Returns the rp samount for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRPSamount() { return RPSamount; }

    /**
     * Updates the rp sselection for this player contract.
     *
     * @param RPSselection Rp sselection supplied by the caller.
     */
    public void setRPSselection(int RPSselection) { this.RPSselection = RPSselection; }

    /**
     * Returns the rp sselection for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRPSselection() { return RPSselection; }

    /**
     * Executes reset rps for this player contract.
     */
    public void resetRPS(){
        this.RPSamount = 0;
        this.RPSselection = 0;
        this.RPSRival = "";
    }

    /**
     * Returns the calendar gifts for this player contract.
     */
    public void getCalendarGifts(){
        int total = LandingManager.getInstance().getTotalDays();
        this.calendarGifts = new boolean[total];

        Arrays.fill(this.calendarGifts, false);


        this.calendarGifts = LandingDao.calendarDays(this.id,total);
    }

    /**
     * Returns the gifts for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public boolean[] getGifts(){
        return this.calendarGifts;
    }

    /**
     * Updates the rp srequest for this player contract.
     *
     * @param RPSrequest Rp srequest supplied by the caller.
     */
    public void setRPSrequest(String RPSrequest) {
        this.RPSrequest = RPSrequest;
    }

    /**
     * Returns the rp srequest for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRPSrequest() {
        return RPSrequest;
    }

    /**
     * Updates the rps rival for this player contract.
     *
     * @param RPSRival Rps rival supplied by the caller.
     */
    public void setRPSRival(String RPSRival) { this.RPSRival = RPSRival; }

    /**
     * Returns the mentions for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public List<PlayerMention> getMentions() {
        return this.mentions;
    }

    /**
     * Adds mention to this player contract.
     *
     * @param mention Mention supplied by the caller.
     */
    public void addMention(PlayerMention mention) {
        this.mentions.add(mention);
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

}
