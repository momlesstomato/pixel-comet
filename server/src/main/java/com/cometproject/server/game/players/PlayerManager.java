package com.cometproject.server.game.players;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.game.players.IPlayerService;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.config.Configuration;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.login.PlayerLoginRequest;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;


/**
 * Manages player runtime state for the player subsystem.
 */
public class PlayerManager implements IPlayerService {
    private static final String PLAYER_AVATAR_CACHE_SIZE_PROPERTY = "comet.cache.playerAvatar.maxSize";
    private static final String PLAYER_DATA_CACHE_SIZE_PROPERTY = "comet.cache.playerData.maxSize";
    private static final String PLAYER_AVATAR_CACHE_MINUTES_PROPERTY = "comet.cache.playerAvatar.expireMinutes";
    private static final String PLAYER_DATA_CACHE_MINUTES_PROPERTY = "comet.cache.playerData.expireMinutes";
    private static final long DEFAULT_PLAYER_AVATAR_CACHE_SIZE = 5_000L;
    private static final long DEFAULT_PLAYER_DATA_CACHE_SIZE = 5_000L;
    private static final long DEFAULT_PLAYER_AVATAR_CACHE_MINUTES = 30L;
    private static final long DEFAULT_PLAYER_DATA_CACHE_MINUTES = 30L;
    private static Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class.getName());
    private final ISsoTicketService ssoTicketService;
    private final IPlayerRepository playerRepository;

    private Map<Integer, Integer> playerIdToSessionId;
    private Map<String, Integer> playerUsernameToPlayerId;

    private Map<String, List<Integer>> ipAddressToPlayerIds;

    private Map<Integer, String> playerIdToUsername;

    private Cache<Integer, PlayerAvatar> playerAvatarCache;
    private Cache<Integer, PlayerData> playerDataCache;
    private ExecutorService playerLoginService;

    @Inject
    PlayerManager(
            final ISsoTicketService ssoTicketService,
            final IPlayerRepository playerRepository) {
        this.ssoTicketService = ssoTicketService;
        this.playerRepository = playerRepository;
    }

    /**
     * Returns the instance for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public static PlayerManager getInstance() {
        return CometBootstrap.resolve(PlayerManager.class);
    }

    /**
     * Starts this player component.
     */
    @Override
    public void start() {
        this.playerIdToSessionId = new ConcurrentHashMap<>();
        this.playerUsernameToPlayerId = new ConcurrentHashMap<>();
        this.ipAddressToPlayerIds = new ConcurrentHashMap<>();
        this.playerIdToUsername = new ConcurrentHashMap<>();

        this.playerAvatarCache = Caffeine.newBuilder()
            .maximumSize(this.getConfiguredLong(PLAYER_AVATAR_CACHE_SIZE_PROPERTY, DEFAULT_PLAYER_AVATAR_CACHE_SIZE))
            .expireAfterAccess(Duration.ofMinutes(this.getConfiguredLong(PLAYER_AVATAR_CACHE_MINUTES_PROPERTY, DEFAULT_PLAYER_AVATAR_CACHE_MINUTES)))
            .build();
        this.playerDataCache = Caffeine.newBuilder()
            .maximumSize(this.getConfiguredLong(PLAYER_DATA_CACHE_SIZE_PROPERTY, DEFAULT_PLAYER_DATA_CACHE_SIZE))
            .expireAfterAccess(Duration.ofMinutes(this.getConfiguredLong(PLAYER_DATA_CACHE_MINUTES_PROPERTY, DEFAULT_PLAYER_DATA_CACHE_MINUTES)))
            .build();

        this.playerLoginService = Executors.newFixedThreadPool(4);// TODO: configure this.

        LOGGER.info("Resetting player online status");
        this.playerRepository.resetOnlineStatus();

        LOGGER.info("PlayerManager initialized");
    }

    /**
     * Stops this player component.
     */
    @Override
    public void stop() {
        if (this.playerLoginService != null) {
            this.playerLoginService.shutdownNow();
        }
    }

    /**
     * Executes submit login request for this player contract.
     *
     * @param client Client supplied by the caller.
     * @param ticket Ticket supplied by the caller.
     */
    public void submitLoginRequest(ISession client, String ticket) {
        this.playerLoginService.submit(new PlayerLoginRequest(
            (Session) client,
            ticket,
            this.ssoTicketService,
            this.playerRepository));
    }

    /**
     * Returns the avatar by player id for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param mode Mode supplied by the caller.
     * @return Value exposed by the contract.
     */
    public PlayerAvatar getAvatarByPlayerId(int playerId, byte mode) {
        if (this.isOnline(playerId)) {
            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

            if (session != null && session.getPlayer() != null && session.getPlayer().getData() != null) {
                return session.getPlayer().getData();
            }
        }

        if (this.playerDataCache != null) {
            final PlayerData cachedPlayerData = this.playerDataCache.getIfPresent(playerId);

            if (cachedPlayerData != null) {
                return cachedPlayerData;
            }
        }

        if (this.playerAvatarCache != null) {
            final PlayerAvatar playerAvatar = this.playerAvatarCache.getIfPresent(playerId);

            if (playerAvatar != null) {

                if (playerAvatar.getMotto() == null && mode == PlayerAvatar.USERNAME_FIGURE_MOTTO) {
                    playerAvatar.setMotto(PlayerDao.getMottoByPlayerId(playerId));
                }

                return playerAvatar;
            }
        }

        PlayerAvatar playerAvatar = PlayerDao.getAvatarById(playerId, mode);

        if (playerAvatar != null && this.playerAvatarCache != null) {
            this.playerAvatarCache.put(playerId, playerAvatar);
        }

        return playerAvatar;
    }

    /**
     * Returns the data by player id for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public PlayerData getDataByPlayerId(int playerId) {
        if (this.isOnline(playerId)) {
            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

            if (session != null && session.getPlayer() != null && session.getPlayer().getData() != null) {
                return session.getPlayer().getData();
            }
        }

        if (this.playerDataCache != null) {
            final PlayerData cachedPlayerData = this.playerDataCache.getIfPresent(playerId);

            if (cachedPlayerData != null) {
                return cachedPlayerData;
            }
        }

        PlayerData playerData = PlayerDao.getDataById(playerId);

        if (playerData != null && this.playerDataCache != null) {
            this.playerDataCache.put(playerId, playerData);
        }

        return playerData;
    }

    /**
     * Returns the player count by IP address for this player contract.
     *
     * @param ipAddress Ip address supplied by the caller.
     * @return Value exposed by the contract.
     */
    public int getPlayerCountByIpAddress(String ipAddress) {
        if (this.ipAddressToPlayerIds.containsKey(ipAddress)) {
            return this.ipAddressToPlayerIds.get(ipAddress).size();
        }

        return 0;
    }

    /**
     * Executes put for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param sessionId Session id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param ipAddress Ip address supplied by the caller.
     */
    public void put(int playerId, int sessionId, String username, String ipAddress) {
        this.playerIdToSessionId.remove(playerId);

        this.playerUsernameToPlayerId.remove(username.toLowerCase());

        if (!this.ipAddressToPlayerIds.containsKey(ipAddress)) {
            final List<Integer> list = new CopyOnWriteArrayList<Integer>() {{
                add(playerId);
            }};

            this.ipAddressToPlayerIds.put(ipAddress, list);
        } else {
            this.ipAddressToPlayerIds.get(ipAddress).add(playerId);
        }

        this.playerIdToSessionId.put(playerId, sessionId);
        this.playerUsernameToPlayerId.put(username.toLowerCase(), playerId);
    }

    /**
     * Executes remove for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param username Username supplied by the caller.
     * @param sessionId Session id supplied by the caller.
     * @param ipAddress Ip address supplied by the caller.
     */
    public void remove(int playerId, String username, int sessionId, String ipAddress) {
        if (this.getSessionIdByPlayerId(playerId) != sessionId) {
            return;
        }

        final List<Integer> playerIds = this.ipAddressToPlayerIds.get(ipAddress);

        if (playerIds != null && playerIds.contains(playerId)) {
            playerIds.remove((Integer) playerId);

            if (playerIds.size() == 0) {
                this.ipAddressToPlayerIds.remove(ipAddress);
            }
        }

        this.playerIdToSessionId.remove(playerId);
        this.playerUsernameToPlayerId.remove(username.toLowerCase());
    }

    /**
     * Returns the player id by username for this player contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public int getPlayerIdByUsername(String username) {
        if (this.playerUsernameToPlayerId.containsKey(username.toLowerCase())) {
            return this.playerUsernameToPlayerId.get(username.toLowerCase());
        }

        return -1;
    }

    /**
     * Returns the session id by player id for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public int getSessionIdByPlayerId(int playerId) {
        if (this.playerIdToSessionId.containsKey(playerId)) {
            return this.playerIdToSessionId.get(playerId);
        }

        return -1;
    }

    /**
     * Updates username cache for this player contract.
     *
     * @param oldName Old name supplied by the caller.
     * @param newName New name supplied by the caller.
     */
    public void updateUsernameCache(final String oldName, final String newName) {
        final int playerId = this.getPlayerIdByUsername(oldName.toLowerCase());

        this.playerUsernameToPlayerId.remove(oldName.toLowerCase());
        this.playerUsernameToPlayerId.put(newName.toLowerCase(), playerId);
    }

    /**
     * Returns the player ids by IP address for this player contract.
     *
     * @param ipAddress Ip address supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<Integer> getPlayerIdsByIpAddress(String ipAddress) {
        return new ArrayList<>(this.ipAddressToPlayerIds.get(ipAddress));
    }

    /**
     * Indicates whether online applies to this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOnline(int playerId) {
        return this.playerIdToSessionId.containsKey(playerId);
    }

    /**
     * Indicates whether online applies to this player contract.
     *
     * @param username Username supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOnline(String username) {
        return this.playerUsernameToPlayerId.containsKey(username.toLowerCase());
    }

    /**
     * Executes size for this player contract.
     *
     * @return Result produced by the operation.
     */
    public int size() {
        return this.playerIdToSessionId.size();
    }

    /**
     * Returns the player id by auth token for this player contract.
     *
     * @param authToken Auth token supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Integer getPlayerIdByAuthToken(String authToken) {
        return this.ssoTicketService.resolvePlayerId(authToken).orElse(null);
    }

    /**
     * Creates auth token for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param authToken Auth token supplied by the caller.
     */
    @Override
    public void createAuthToken(int playerId, String authToken) {
        this.ssoTicketService.createSessionToken(playerId, authToken);
    }

    /**
     * Returns the player load execution service for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public ExecutorService getPlayerLoadExecutionService() {
        return playerLoginService;
    }

    private long getConfiguredLong(final String property, final long defaultValue) {
        return Long.parseLong(String.valueOf(Configuration.currentConfig().getOrDefault(property, String.valueOf(defaultValue))));
    }
}
