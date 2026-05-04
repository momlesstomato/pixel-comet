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
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.config.Configuration;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.login.PlayerLoginRequest;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


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

    private Map<Integer, Integer> playerIdToSessionId;
    private Map<String, Integer> playerUsernameToPlayerId;

    private Map<String, List<Integer>> ipAddressToPlayerIds;

    private Map<String, Integer> ssoTicketToPlayerId;
    private Map<Integer, String> playerIdToUsername;
    private Map<String, Integer> authTokenToPlayerId;

    private Cache<Integer, PlayerAvatar> playerAvatarCache;
    private Cache<Integer, PlayerData> playerDataCache;
    private ExecutorService playerLoginService;

    public PlayerManager() {

    }

    public static PlayerManager getInstance() {
        return CometBootstrap.resolve(PlayerManager.class);
    }

    @Override
    public void start() {
        this.playerIdToSessionId = new ConcurrentHashMap<>();
        this.playerUsernameToPlayerId = new ConcurrentHashMap<>();
        this.ipAddressToPlayerIds = new ConcurrentHashMap<>();
        this.ssoTicketToPlayerId = new ConcurrentHashMap<>();
        this.playerIdToUsername = new ConcurrentHashMap<>();
        this.authTokenToPlayerId = new ConcurrentHashMap<>();

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
        PlayerDao.resetOnlineStatus();

        LOGGER.info("PlayerManager initialized");
    }

    @Override
    public void stop() {
        if (this.playerLoginService != null) {
            this.playerLoginService.shutdownNow();
        }
    }

    public void submitLoginRequest(ISession client, String ticket) {
        this.playerLoginService.submit(new PlayerLoginRequest((Session) client, ticket));
    }

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

    public int getPlayerCountByIpAddress(String ipAddress) {
        if (this.ipAddressToPlayerIds.containsKey(ipAddress)) {
            return this.ipAddressToPlayerIds.get(ipAddress).size();
        }

        return 0;
    }

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

    public int getPlayerIdByUsername(String username) {
        if (this.playerUsernameToPlayerId.containsKey(username.toLowerCase())) {
            return this.playerUsernameToPlayerId.get(username.toLowerCase());
        }

        return -1;
    }

    public int getSessionIdByPlayerId(int playerId) {
        if (this.playerIdToSessionId.containsKey(playerId)) {
            return this.playerIdToSessionId.get(playerId);
        }

        return -1;
    }

    public void updateUsernameCache(final String oldName, final String newName) {
        final int playerId = this.getPlayerIdByUsername(oldName.toLowerCase());

        this.playerUsernameToPlayerId.remove(oldName.toLowerCase());
        this.playerUsernameToPlayerId.put(newName.toLowerCase(), playerId);
    }

    public List<Integer> getPlayerIdsByIpAddress(String ipAddress) {
        return new ArrayList<>(this.ipAddressToPlayerIds.get(ipAddress));
    }

    public boolean isOnline(int playerId) {
        return this.playerIdToSessionId.containsKey(playerId);
    }

    public boolean isOnline(String username) {
        return this.playerUsernameToPlayerId.containsKey(username.toLowerCase());
    }

    public int size() {
        return this.playerIdToSessionId.size();
    }

    public Map<String, Integer> getSsoTicketToPlayerId() {
        return ssoTicketToPlayerId;
    }

    @Override
    public Integer getPlayerIdByAuthToken(String authToken) {
        return this.ssoTicketToPlayerId.get(authToken);
    }

    @Override
    public void createAuthToken(int playerId, String authToken) {

    }

    public ExecutorService getPlayerLoadExecutionService() {
        return playerLoginService;
    }

    private long getConfiguredLong(final String property, final long defaultValue) {
        return Long.parseLong(String.valueOf(Configuration.currentConfig().getOrDefault(property, String.valueOf(defaultValue))));
    }
}