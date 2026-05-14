package com.cometproject.server.game.rooms;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.IRoomService;
import com.cometproject.api.game.rooms.models.IRoomModel;
import com.cometproject.api.game.rooms.models.IRoomModelFactory;
import com.cometproject.api.game.rooms.models.IRoomModelService;
import com.cometproject.api.game.rooms.models.CustomFloorMapData;
import com.cometproject.api.game.rooms.settings.RoomAccessType;
import com.cometproject.api.game.rooms.settings.RoomTradeState;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.filter.WordFilter;
import com.cometproject.server.game.rooms.models.ServerRoomModelFactory;
import com.cometproject.server.game.rooms.models.types.StaticRoomModel;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredUtil;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.RoomPromotion;
import com.cometproject.server.game.rooms.types.RoomReloadListener;
import com.cometproject.server.game.rooms.types.misc.ChatEmotionsManager;
import com.cometproject.server.network.messages.outgoing.room.events.RoomPromotionMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.cache.CacheManager;
import com.cometproject.server.storage.cache.objects.RoomDataObject;
import com.cometproject.server.storage.queries.rooms.RoomDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Manages room runtime state for the room subsystem.
 */
public class RoomManager implements Startable, IRoomService, IRoomModelService {

    /**
     * Manages room runtime state for the room subsystem.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);
    public static final int LRU_MAX_ENTRIES = Integer.parseInt(Configuration.currentConfig().getProperty("comet.game.rooms.data.max"));
    public static final int LRU_MAX_LOWER_WATERMARK = Integer.parseInt(Configuration.currentConfig().getProperty("comet.game.rooms.data.lowerWatermark"));
    private Map<Integer, IRoomData> roomDataInstances;

    private Map<Integer, Room> loadedRoomInstances;
    private Map<Integer, Room> unloadingRoomInstances;

    private Map<Integer, RoomPromotion> roomPromotions;

    private Map<String, StaticRoomModel> models;
    private WordFilter filterManager;

    private RoomCycle globalCycle;
    private ChatEmotionsManager emotions;

    private ExecutorService executorService;

    private Map<Integer, RoomReloadListener> reloadListeners;

    /**
     * Creates a room manager instance for the room subsystem.
     */
    public RoomManager() {

    }

    /**
     * Returns the instance for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public static RoomManager getInstance() {
        return CometBootstrap.resolve(RoomManager.class);
    }

    /**
     * Starts this room component.
     */
    @Override
    public void start() {
        this.roomDataInstances = new ConcurrentHashMap<>();

        this.loadedRoomInstances = new ConcurrentHashMap<>();
        this.unloadingRoomInstances = new ConcurrentHashMap<>();
        this.roomPromotions = new ConcurrentHashMap<>();
        this.reloadListeners = new ConcurrentHashMap<>();

        this.emotions = new ChatEmotionsManager();
        this.filterManager = new WordFilter();

        this.globalCycle = new RoomCycle();

        this.loadPromotedRooms();
        this.loadModels();

        this.globalCycle.start();

        this.executorService = Executors.newFixedThreadPool(2, r -> {
            final Thread roomThread = new Thread(r, "Room-Load-Worker-" + UUID.randomUUID());

            roomThread.setUncaughtExceptionHandler((t, e) -> e.printStackTrace());

            return roomThread;
        });

        LOGGER.info("RoomManager initialized");
    }

    /**
     * Stops this room component.
     */
    @Override
    public void stop() {
        if (this.globalCycle != null) {
            this.globalCycle.stop();
        }

        if (this.executorService != null) {
            this.executorService.shutdownNow();
        }
    }

    /**
     * Loads promoted rooms for this room contract.
     */
    public void loadPromotedRooms() {
        RoomDao.deleteExpiredRoomPromotions();
        RoomDao.getActivePromotions(this.roomPromotions);

        LOGGER.info("Loaded " + this.getRoomPromotions().size() + " room promotions");
    }

    /**
     * Executes initialize room for this room contract.
     *
     * @param initializer Initializer supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param password Password supplied by the caller.
     */
    public void initializeRoom(Session initializer, int roomId, String password) {
        this.executorService.submit(() -> {
            if (initializer != null && initializer.getPlayer() != null) {
                initializer.getPlayer().loadRoom(roomId, password);
            }
        });
    }

    /**
     * Loads models for this room contract.
     */
    @Override
    public void loadModels() {
        if (this.models != null && !this.models.isEmpty()) {
            this.models.clear();
        }

        this.models = RoomDao.getModels();

        LOGGER.info("Loaded " + this.models.size() + " room models");
    }

    /**
     * Returns the model for this room contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IRoomModel getModel(String id) {
        if (this.models != null && this.models.containsKey(id)) {
            return this.models.get(id);
        }

        LOGGER.debug("Couldn't find model: {}", id);
        return null;
    }

    /**
     * Returns the room model factory for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IRoomModelFactory getRoomModelFactory() {
        return ServerRoomModelFactory.INSTANCE;
    }

    /**
     * Executes get for this room contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Room get(int id) {
        if (id < 1) return null;

        if (this.getRoomInstances().containsKey(id)) {
            return this.getRoomInstances().get(id);
        }

        if (this.getRoomInstances().containsKey(id)) {
            return this.getRoomInstances().get(id);
        }

        Room room = null;

        if (CacheManager.getInstance().exists("rooms." + id)) {
            final RoomDataObject roomDataObject = CacheManager.getInstance().get(RoomDataObject.class, "rooms." + id);

            if (roomDataObject != null) {
                room = new Room(roomDataObject);
            }
        }

        if (room == null) {
            IRoomData data = this.getRoomData(id);

            if (data == null) {
                return null;
            }

            room = new Room(data);
        }

        room.load();

        this.loadedRoomInstances.put(id, room);

        this.finalizeRoomLoad(room);

        return room;
    }

    private void finalizeRoomLoad(Room room) {
        if (room == null) {
            return;
        }
        room.getItems().onLoaded();
    }
//
/**
 * Returns the room data for this room contract.
 *
 * @param id Id supplied by the caller.
 * @return Value exposed by the contract.
 */
@Override
    public IRoomData getRoomData(int id) {
        if (this.roomDataInstances.containsKey(id)) {
            return this.roomDataInstances.get(id);
        }

        IRoomData roomData = RoomDao.getRoomDataById(id);

        if (roomData != null) {
            this.roomDataInstances.put(id, roomData);
        }

        return roomData;
    }

    /**
     * Persists room data for this room contract.
     *
     * @param roomData Room data supplied by the caller.
     */
    @Override
    public void saveRoomData(IRoomData roomData) {
        StorageContext.getCurrentContext().getRoomRepository().updateRoom(roomData);
    }

    /**
     * Executes unload idle rooms for this room contract.
     */
    public void unloadIdleRooms() {
        for (Room room : this.unloadingRoomInstances.values()) {
            this.executorService.submit(() -> {
                room.dispose();

                if (room.isReloading()) {
                    Room newRoom = this.get(room.getId());

                    if (newRoom != null) {
                        if (this.reloadListeners.containsKey(room.getId())) {
                            final RoomReloadListener reloadListener = this.reloadListeners.get(newRoom.getId());

                            reloadListener.onReloaded(newRoom);
                            this.reloadListeners.remove(room.getId());
                        }
                    }
                }
            });
        }

        this.unloadingRoomInstances.clear();

        List<Room> idleRooms = new ArrayList<>();

        for (Room room : this.loadedRoomInstances.values()) {
            if (room.isIdle()) {
                idleRooms.add(room);
            }
        }

        for (Room room : idleRooms) {
            this.loadedRoomInstances.remove(room.getId());
            this.unloadingRoomInstances.put(room.getId(), room);
        }
    }

    /**
     * Executes force unload for this room contract.
     *
     * @param id Id supplied by the caller.
     */
    public void forceUnload(int id) {
        if (this.loadedRoomInstances.containsKey(id)) {
            this.loadedRoomInstances.remove(id).dispose();
        }
    }

    /**
     * Removes data from this room contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void removeData(int roomId) {
        if (this.getRoomDataInstances().get(roomId) == null) {
            return;
        }

        this.getRoomDataInstances().remove(roomId);
    }

    /**
     * Adds reload listener to this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param listener Listener supplied by the caller.
     */
    public void addReloadListener(int roomId, RoomReloadListener listener) {
        this.reloadListeners.put(roomId, listener);
    }

    /**
     * Loads rooms for user for this room contract.
     *
     * @param player Player participating in the operation.
     */
    public void loadRoomsForUser(IPlayer player) {
        player.getRooms().clear();
        player.getRoomsWithRights().clear();

        Map<Integer, IRoomData> rooms = RoomDao.getRoomsByPlayerId(player.getId());
        Map<Integer, IRoomData> roomsWithRights = RoomDao.getRoomsWithRightsByPlayerId(player.getId());

        for (Map.Entry<Integer, IRoomData> roomEntry : rooms.entrySet()) {
            player.getRooms().add(roomEntry.getKey());

            if (!this.getRoomDataInstances().containsKey(roomEntry.getKey())) {
                this.getRoomDataInstances().put(roomEntry.getKey(), roomEntry.getValue());
            }
        }

        for (Map.Entry<Integer, IRoomData> roomEntry : roomsWithRights.entrySet()) {
            player.getRoomsWithRights().add(roomEntry.getKey());

            if (!this.getRoomDataInstances().containsKey(roomEntry.getKey())) {
                this.getRoomDataInstances().put(roomEntry.getKey(), roomEntry.getValue());
            }
        }
    }

    /**
     * Returns the rooms by query for this room contract.
     *
     * @param query Query supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<IRoomData> getRoomsByQuery(String query) {
        List<IRoomData> rooms = new ArrayList<>();

        if (query.equals("owner:")) return rooms;

        if (query.equals("tag:")) return rooms;

        if (query.equals("group:")) return rooms;

        if (query.startsWith("roomname:")) {
            query = query.substring(9);
        }

        List<IRoomData> roomSearchResults = RoomDao.getRoomsByQuery(query);

        for (IRoomData data : roomSearchResults) {
            if (!this.getRoomDataInstances().containsKey(data.getId())) {
                this.getRoomDataInstances().put(data.getId(), data);
            }

            rooms.add(data);
        }

        if (rooms.size() == 0 && !query.toLowerCase().startsWith("owner:")) {
            return this.getRoomsByQuery("owner:" + query);
        }

        return rooms;
    }

    /**
     * Indicates whether active applies to this room contract.
     *
     * @param id Id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isActive(int id) {
        return this.getRoomInstances().containsKey(id);
    }

    /**
     * Creates room for this room contract.
     *
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     * @param model Model supplied by the caller.
     * @param category Category supplied by the caller.
     * @param maxVisitors Max visitors supplied by the caller.
     * @param tradeState Trade state supplied by the caller.
     * @param client Client supplied by the caller.
     * @param wallTickness Wall tickness supplied by the caller.
     * @param floorThickness Floor thickness supplied by the caller.
     * @param decorations Decorations supplied by the caller.
     * @param hideWalls Hide walls supplied by the caller.
     * @return Value exposed by the contract.
     */
    public int createRoom(String name, String description, CustomFloorMapData model, int category, int maxVisitors, int tradeState, ISession client, int wallTickness, int floorThickness, String decorations, boolean hideWalls) {
        int roomId = RoomDao.createRoom(name, model, description, category, maxVisitors, RoomTradeState.valueOf(tradeState), client.getPlayer().getId(), client.getPlayer().getData().getUsername(), wallTickness, floorThickness, decorations, hideWalls);

        this.loadRoomsForUser(client.getPlayer());

        return roomId;
    }

    /**
     * Creates room for this room contract.
     *
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     * @param model Model supplied by the caller.
     * @param category Category supplied by the caller.
     * @param maxVisitors Max visitors supplied by the caller.
     * @param tradeState Trade state supplied by the caller.
     * @param client Client supplied by the caller.
     * @return Value exposed by the contract.
     */
    public int createRoom(String name, String description, String model, int category, int maxVisitors, int tradeState, Session client) {
        int roomId = RoomDao.createRoom(name, model, description, category, maxVisitors, RoomTradeState.valueOf(tradeState), client.getPlayer().getId(), client.getPlayer().getData().getUsername());

        this.loadRoomsForUser(client.getPlayer());

        return roomId;
    }

    /**
     * Executes rights rooms update for this room contract.
     *
     * @param client Client supplied by the caller.
     */
    public void rightsRoomsUpdate(Session client) {
        this.loadRoomsForUser(client.getPlayer());
    }

    private List<Integer> getActiveAvailableRooms() {
        final List<Integer> rooms = new ArrayList<>();

        for (Room activeRoom : this.loadedRoomInstances.values()) {
            if (!this.unloadingRoomInstances.containsKey(activeRoom.getId())) {
                final int playerCount = activeRoom.getEntities().playerCount();

                if (playerCount != 0 && playerCount < activeRoom.getData().getMaxUsers() &&
                        activeRoom.getData().getAccess() == RoomAccessType.OPEN) {
                    rooms.add(activeRoom.getId());
                }
            }
        }

        return rooms;
    }

    /**
     * Returns the random active room for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRandomActiveRoom() {
        final List<Integer> rooms = this.getActiveAvailableRooms();
        final Integer roomId = WiredUtil.getRandomElement(rooms);

        rooms.clear();

        if (roomId != null) {
            return roomId;
        }

        return -1;
    }

    /**
     * Returns the rooms by category for this room contract.
     *
     * @param category Category supplied by the caller.
     * @param player Player participating in the operation.
     * @return Value exposed by the contract.
     */
    public List<IRoomData> getRoomsByCategory(int category, Player player) {
        return this.getRoomsByCategory(category, 0, player);
    }

    /**
     * Returns the rooms by category for this room contract.
     *
     * @param category Category supplied by the caller.
     * @param minimumPlayers Minimum players supplied by the caller.
     * @param player Player participating in the operation.
     * @return Value exposed by the contract.
     */
    public List<IRoomData> getRoomsByCategory(int category, int minimumPlayers, Player player) {
        List<IRoomData> rooms = new ArrayList<>();

        for (Room room : this.getRoomInstances().values()) {
            if (category != -1 && (room.getCategory() == null || room.getCategory().getId() != category)) {
                continue;
            }

            if (room.getEntities() != null && room.getEntities().playerCount() < minimumPlayers) continue;

            if (room.getData().getAccess() == RoomAccessType.INVISIBLE && player.getData().getRank() < 3) {
                if (room.getGroup() != null) {
                    if (!player.getGroups().contains(room.getGroup().getId())) {
                        continue;
                    }
                } else {
                    if (!room.getRights().hasRights(player.getId())) {
                        continue;
                    }
                }
            }

            rooms.add(room.getData());
        }

        return rooms;
    }

    /**
     * Executes promote room for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     */
    public void promoteRoom(int roomId, String name, String description) {
        if (this.roomPromotions.containsKey(roomId)) {
            RoomPromotion promo = this.roomPromotions.get(roomId);
            promo.setTimestampFinish(promo.getTimestampFinish() + (RoomPromotion.DEFAULT_PROMO_LENGTH * 60));

            RoomDao.updatePromotedRoom(promo);
        } else {
            RoomPromotion roomPromotion = new RoomPromotion(roomId, name, description);
            RoomDao.createPromotedRoom(roomPromotion);

            this.roomPromotions.put(roomId, roomPromotion);
        }

        final Room room = this.get(roomId);

        if (room != null) {
            if (room.getEntities() != null && room.getEntities().realPlayerCount() >= 1) {
                room.getEntities().broadcastMessage(new RoomPromotionMessageComposer(room.getData(), this.roomPromotions.get(roomId)));
            }
        }
    }

    /**
     * Creates r pinstance for this room contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     */
    public void createRPinstance(int roomId, String name, String description) {
        /*RoomPromotion roomPromotion = new RoomPromotion(roomId, name, description, Long.MIN_VALUE, Long.MAX_VALUE);
        RoomDao.createPromotedRoom(roomPromotion);

        this.roomPromotions.put(roomId, roomPromotion);


        final Room room = this.get(roomId);

        if (room != null) {
            if (room.getEntities() != null && room.getEntities().realPlayerCount() >= 1) {
                room.getEntities().broadcastMessage(new RoomPromotionMessageComposer(room.getData(), this.roomPromotions.get(roomId)));
            }
        }*/
    }

    /**
     * Indicates whether this room contract has promotion.
     *
     * @param roomId Room identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPromotion(int roomId) {
        return this.roomPromotions.containsKey(roomId) && !this.roomPromotions.get(roomId).isExpired();
    }

    /**
     * Returns the emotions for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public final ChatEmotionsManager getEmotions() {
        return this.emotions;
    }

    /**
     * Returns the room instances for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public final Map<Integer, Room> getRoomInstances() {
        return this.loadedRoomInstances;
    }

    private Map<Integer, IRoomData> getRoomDataInstances() {
        return this.roomDataInstances;
    }

    /**
     * Returns the global cycle for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public final RoomCycle getGlobalCycle() {
        return this.globalCycle;
    }

    /**
     * Returns the filter for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public final WordFilter getFilter() {
        return filterManager;
    }

    /**
     * Returns the room promotions for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, RoomPromotion> getRoomPromotions() {
        return roomPromotions;
    }
}
