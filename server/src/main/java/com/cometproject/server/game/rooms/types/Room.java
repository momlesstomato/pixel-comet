package com.cometproject.server.game.rooms.types;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.pets.IPetData;
import com.cometproject.api.game.rooms.IRoom;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.RoomCategory;
import com.cometproject.api.game.rooms.RoomType;
import com.cometproject.api.game.rooms.models.CustomFloorMapData;
import com.cometproject.api.game.rooms.models.IRoomModel;
import com.cometproject.api.game.rooms.models.RoomModelData;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.navigator.NavigatorManager;
import com.cometproject.server.game.polls.PollManager;
import com.cometproject.server.game.polls.types.Poll;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.RoomQueue;
import com.cometproject.server.game.rooms.objects.entities.types.BotEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.data.PlayerBotData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerAtGivenTime;
import com.cometproject.server.game.rooms.types.components.*;
import com.cometproject.server.game.rooms.types.mapping.RoomMapping;
import com.cometproject.server.network.messages.outgoing.room.polls.GetInfobusPollResultsMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.polls.QuickPollMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.polls.QuickPollResultsMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.polls.StartInfobusPollMessageComposer;
import com.cometproject.server.storage.cache.objects.RoomDataObject;
import com.cometproject.server.storage.cache.objects.items.FloorItemDataObject;
import com.cometproject.server.storage.cache.objects.items.WallItemDataObject;
import com.cometproject.server.utilities.attributes.Attributable;
import com.cometproject.storage.mysql.models.factories.rooms.RoomModelDataFactory;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Describes room behavior for the room subsystem.
 */
public class Room implements Attributable, IRoom {
    public static final boolean useCycleForItems = false;
    public static final boolean useCycleForEntities = false;

    public final Logger LOGGER;

    private final IRoomData data;

    private final RoomDataObject cachedData;
    private final AtomicInteger wiredTimer = new AtomicInteger(0);
    private IRoomModel model;
    private RoomMapping mapping;
    private ProcessComponent process;
    private RightsComponent rights;
    private ItemsComponent items;
    private ItemProcessComponent itemProcess;
    private TradeComponent trade;
    private RoomBotComponent bots;
    private PetComponent pets;
    private GameComponent game;
    private EntityComponent entities;
    private FilterComponent filter;
    //private RPSComponent rps;
    private IGroup group;
    private Map<String, Object> attributes;
    private Set<Integer> ratings;
    private String question;
    private Set<Integer> yesVotes;
    private Set<Integer> noVotes;
    private Set<Integer> infobusChoice1;
    private Set<Integer> infobusChoice2;
    private Set<Integer> infobusChoice3;
    private int executedEvent = 0;
    private boolean isDisposed = false;
    private int idleTicks = 0;
    private boolean isReloading = false;
    private boolean forcedUnload = false;

    /**
     * Creates a room instance for the room subsystem.
     *
     * @param data Data supplied by the caller.
     */
    public Room(IRoomData data) {
        this.data = data;
        this.LOGGER = LoggerFactory.getLogger("Room \"" + this.getData().getName() + "\"");
        this.cachedData = null;
    }

    /**
     * Creates a room instance for the room subsystem.
     *
     * @param cachedRoomObject Cached room object supplied by the caller.
     */
    public Room(RoomDataObject cachedRoomObject) {
        this(cachedRoomObject.getData());
    }

    /**
     * Executes load for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Room load() {
        this.model = GameContext.getCurrent().getRoomModelService().getModel(this.getData().getModel());

        if (this.getData().getHeightmap() != null) {
            RoomModelData roomModelData;

            try {
                if (this.getData().getHeightmap().startsWith("{")) {
                    CustomFloorMapData mapData = JsonUtil.getInstance().fromJson(this.getData().getHeightmap(), CustomFloorMapData.class);
                    roomModelData = RoomModelDataFactory.instance.createData(mapData);
                } else {
                    roomModelData = RoomModelDataFactory.instance.createData("dynamic_heightmap", this.getData().getHeightmap(),
                            this.getModel().getRoomModelData().getDoorX(), this.getModel().getRoomModelData().getDoorY(),
                            this.getModel().getRoomModelData().getDoorRotation());
                }

                if (roomModelData != null) {
                    this.model = GameContext.getCurrent().getRoomModelService().getRoomModelFactory().createModel(roomModelData);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load dynamic room model", e);
            }
        }

        // Cache the group.
        this.group = GameContext.getCurrent().getGroupService() != null
                ? GameContext.getCurrent().getGroupService().getGroup(this.getData().getGroupId())
                : null;

        this.attributes = new HashMap<>();
        this.ratings = new HashSet<>();

        this.mapping = new RoomMapping(this);
        this.itemProcess = new ItemProcessComponent(this);
        this.process = new ProcessComponent(this);
        this.rights = new RightsComponent(this);
        this.items = new ItemsComponent(this);

        this.mapping.init();

        this.trade = new TradeComponent(this);
        this.game = new GameComponent(this);
        this.entities = new EntityComponent(this);
        this.bots = new RoomBotComponent(this);
        this.pets = new PetComponent(this);
        this.filter = new FilterComponent(this);
        //this.rps = new RPSComponent(this);

        this.setAttribute("loadTime", System.currentTimeMillis());

        if (this.data.getType() == RoomType.PUBLIC) {
            RoomQueue.getInstance().addQueue(this.getId(), 0);
        }

        this.LOGGER.debug("Room loaded");
        return this;
    }

    /**
     * Returns the category for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomCategory getCategory() {
        return NavigatorManager.getInstance().getCategory(this.data.getCategoryId());
    }

    /**
     * Returns the cache object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomDataObject getCacheObject() {
        final List<FloorItemDataObject> floorItems = new ArrayList<>();
        final List<WallItemDataObject> wallItems = new ArrayList<>();

        final List<IPetData> petData = new ArrayList<>();
        final List<IBotData> botData = new ArrayList<>();

        for (RoomItemFloor floorItem : this.getItems().getFloorItems().values()) {
            if (floorItem != null) {
                floorItems.add(new FloorItemDataObject(floorItem.getId(), floorItem.getItemData().getItemId(),
                        this.getId(), floorItem.getItemData().getOwnerId(), floorItem.getItemData().getOwnerName(), floorItem.getDataObject(),
                        floorItem.getPosition(), floorItem.getRotation(), floorItem.getLimitedEditionItemData()));
            }
        }

        for (RoomItemWall wallItem : this.getItems().getWallItems().values()) {
            if (wallItem != null) {
                wallItems.add(new WallItemDataObject(wallItem.getId(), wallItem.getItemData().getItemId(),
                        this.getId(), wallItem.getItemData().getOwnerId(), wallItem.getItemData().getOwnerName(), wallItem.getItemData().getData(),
                        wallItem.getWallPosition(), wallItem.getLimitedEditionItemData()));
            }
        }

        final List<Integer> rights = new ArrayList<>(this.rights.getAll());

        for (PetEntity petEntity : this.getEntities().getPetEntities()) {
            if (petEntity.getData() != null) {
                petData.add(petEntity.getData());
            }
        }

        for (BotEntity botEntity : this.getEntities().getBotEntities()) {
            if (botEntity.getData() instanceof PlayerBotData) {
                botData.add(botEntity.getData());
            }
        }

        return new RoomDataObject(this.getId(), this.getData(), rights, floorItems, wallItems, petData, botData);
    }

    /**
     * Indicates whether idle applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isIdle() {
        if (this.idleTicks < 600 && this.getEntities().realPlayerCount() > 0) {
            this.idleTicks = 0;
        } else {
            if (this.idleTicks >= 600) {
                return true;
            } else {
                this.idleTicks += 10;
            }
        }

        return false;
    }

    /**
     * Updates the idle now for this room contract.
     */
    public void setIdleNow() {
        this.idleTicks = 600;
        this.forcedUnload = true;
    }

    /**
     * Executes reload for this room contract.
     */
    public void reload() {
        this.setIdleNow();
        this.isReloading = true;
    }

    /**
     * Releases resources owned by this room component.
     */
    public void dispose() {
        if (this.isDisposed) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        /*if (true) {
            CacheManager.getInstance().put("rooms." + this.getId(), this.getCacheObject());
        }*/

        this.getItems().commit();

        this.isDisposed = true;

        this.process.stop();
        this.itemProcess.stop();
        this.game.stop();

        this.game.dispose();
        this.entities.dispose();
        this.items.dispose();
        this.bots.dispose();
        this.mapping.dispose();

        if (this.data.getType() == RoomType.PUBLIC) {
            RoomQueue.getInstance().removeQueue(this.getId());
        }

        if (this.forcedUnload) {
            RoomManager.getInstance().removeData(this.getId());
        }

        if (this.yesVotes != null) {
            this.yesVotes.clear();
        }

        if (this.noVotes != null) {
            this.noVotes.clear();
        }

        if (this.infobusChoice1 != null) {
            this.infobusChoice1.clear();
        }

        if (this.infobusChoice2 != null) {
            this.infobusChoice2.clear();
        }

        if (this.infobusChoice3 != null) {
            this.infobusChoice3.clear();
        }

        long timeTaken = System.currentTimeMillis() - currentTime;

        if (timeTaken >= 250) {
            this.LOGGER.warn("Room [" + this.getData().getId() + "][" + this.getData().getName() + "] took " + timeTaken + "ms to dispose");
        }

        this.LOGGER.debug("[ROOM " + this.getData().getId() + "] > DISPOSED.");
    }

    /**
     * Executes tick for this room contract.
     */
    public void tick() {
        WiredTriggerAtGivenTime.executeTriggers(this, this.wiredTimer.incrementAndGet());

        if (this.rights != null) {
            this.rights.tick();
        }

        if (this.mapping != null) {
            this.mapping.tick();
        }
    }

    /**
     * Executes start question for this room contract.
     *
     * @param question Question supplied by the caller.
     */
    public void startQuestion(String question) {
        this.question = question;
        this.yesVotes = Sets.newConcurrentHashSet();
        this.noVotes = Sets.newConcurrentHashSet();

        this.getEntities().broadcastMessage(new QuickPollMessageComposer(question));
    }

    /**
     * Executes start infobus for this room contract.
     *
     * @param id Id supplied by the caller.
     */
    public void startInfobus(String id) {
        Poll poll = PollManager.getInstance().getPollbyId(Integer.parseInt(id));

        this.infobusChoice1 = Sets.newConcurrentHashSet();
        this.infobusChoice2 = Sets.newConcurrentHashSet();
        this.infobusChoice3 = Sets.newConcurrentHashSet();

        if(poll != null)
        this.getEntities().broadcastMessage(new StartInfobusPollMessageComposer(poll));
    }

    /**
     * Indicates whether this room contract has answered infobus.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasAnsweredInfobus(int playerId){
        return this.infobusChoice1.contains(playerId) || this.infobusChoice2.contains(playerId) || this.infobusChoice3.contains(playerId);
    }

    /**
     * Executes end question for this room contract.
     */
    public void endQuestion() {
        this.question = null;

        this.getEntities().broadcastMessage(new QuickPollResultsMessageComposer(this.yesVotes.size(), this.noVotes.size()));

        if (this.yesVotes != null) {
            this.yesVotes.clear();
        }

        if (this.noVotes != null) {
            this.noVotes.clear();
        }
    }

    /**
     * Executes end infobus for this room contract.
     */
    public void endInfobus() {
        this.getEntities().broadcastMessage(new GetInfobusPollResultsMessageComposer(this));

        if (this.infobusChoice1 != null) {
            this.infobusChoice1.clear();
        }

        if (this.infobusChoice2 != null) {
            this.infobusChoice2.clear();
        }

        if (this.infobusChoice3 != null) {
            this.infobusChoice3.clear();
        }
    }

    /**
     * Returns the wired timer for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getWiredTimer() {
        return this.wiredTimer.get();
    }

    /**
     * Executes reset wired timer for this room contract.
     */
    public void resetWiredTimer() {
        this.wiredTimer.set(0);
    }

    /**
     * Returns the promotion for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomPromotion getPromotion() {
        return RoomManager.getInstance().getRoomPromotions().get(this.getId());
    }

    /**
     * Updates the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @param attributeValue Attribute value supplied by the caller.
     */
    @Override
    public void setAttribute(String attributeKey, Object attributeValue) {
        if (this.attributes.containsKey(attributeKey)) {
            this.removeAttribute(attributeKey);
        }

        this.attributes.put(attributeKey, attributeValue);
    }

    /**
     * Returns the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Object getAttribute(String attributeKey) {
        return this.attributes.get(attributeKey);
    }

    /**
     * Indicates whether this room contract has attribute.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasAttribute(String attributeKey) {
        return this.attributes.containsKey(attributeKey);
    }

    /**
     * Removes attribute from this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     */
    @Override
    public void removeAttribute(String attributeKey) {
        this.attributes.remove(attributeKey);
    }

    /**
     * Returns the id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.data.getId();
    }

    /**
     * Returns the data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomData getData() {
        return this.data;
    }

    /**
     * Returns the model for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomModel getModel() {
        return this.model;
    }

    /**
     * Returns the process for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public ProcessComponent getProcess() {
        return this.process;
    }

    /**
     * Returns the item process for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public ItemProcessComponent getItemProcess() {
        return this.itemProcess;
    }

    /**
     * Returns the items for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public ItemsComponent getItems() {
        return this.items;
    }

    /**
     * Returns the trade for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public TradeComponent getTrade() {
        return this.trade;
    }

    /**
     * Returns the RPS for this Snow War game contract.
     *
     * @return Value exposed by the contract.
     */
    //public RPSComponent getRPS() { return this.rps; }

    /**
     * Returns the rights for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RightsComponent getRights() {
        return this.rights;
    }

    /**
     * Returns the bots for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomBotComponent getBots() {
        return this.bots;
    }

    /**
     * Returns the pets for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public PetComponent getPets() {
        return this.pets;
    }

    /**
     * Returns the game for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public GameComponent getGame() {
        return this.game;
    }

    /**
     * Returns the entities for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public EntityComponent getEntities() {
        return this.entities;
    }

    /**
     * Returns the mapping for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomMapping getMapping() {
        return this.mapping;
    }

    /**
     * Returns the group for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public IGroup getGroup() {
        if (this.group == null || this.group.getData() == null) return null;

        return this.group;
    }

    /**
     * Updates the group for this room contract.
     *
     * @param group Group supplied by the caller.
     */
    public void setGroup(final IGroup group) {
        this.group = group;
    }

    /**
     * Indicates whether this room contract has room mute.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasRoomMute() {
        return this.attributes.containsKey("room_muted") && (boolean) this.attributes.get("room_muted");
    }

    /**
     * Updates the room mute for this room contract.
     *
     * @param mute Mute supplied by the caller.
     */
    public void setRoomMute(boolean mute) {
        if (this.attributes.containsKey("room_muted")) {
            this.attributes.replace("room_muted", mute);
        } else {
            this.attributes.put("room_muted", mute);
        }
    }

    /**
     * Returns the ratings for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getRatings() {
        return ratings;
    }

    /**
     * Returns the no votes for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getNoVotes() {
        return noVotes;
    }

    /**
     * Returns the yes votes for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getYesVotes() {
        return yesVotes;
    }

    /**
     * Returns the infobus choice1 for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getInfobusChoice1() {
        return infobusChoice1;
    }

    /**
     * Returns the infobus choice2 for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getInfobusChoice2() {
        return infobusChoice2;
    }

    /**
     * Returns the infobus choice3 for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getInfobusChoice3() {
        return infobusChoice3;
    }

    /**
     * Returns the question for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the cached data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomDataObject getCachedData() {
        return cachedData;
    }

    /**
     * Indicates whether reloading applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isReloading() {
        return this.isReloading;
    }

    /**
     * Returns the filter for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public FilterComponent getFilter() {
        return filter;
    }

    /**
     * Returns the executed event for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getExecutedEvent() {
        return this.executedEvent;
    }

    /**
     * Updates the executed event for this room contract.
     *
     * @param executedEvent Executed event supplied by the caller.
     */
    public void setExecutedEvent(int executedEvent) {
        this.executedEvent = executedEvent;
    }
}
