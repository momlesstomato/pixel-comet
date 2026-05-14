package com.cometproject.server.network.sessions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.networking.sessions.ISessionManager;
import com.cometproject.api.networking.sessions.ISessionService;
import com.cometproject.api.networking.sessions.SessionManagerAccessor;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.PlayerManager;
import com.google.common.collect.Sets;


/**
 * Manages session runtime state for the network session subsystem.
 */
public final class SessionManager implements ISessionManager, ISessionService {
    public static boolean isLocked = false;
    private final AtomicInteger idGenerator = new AtomicInteger();
    private final Map<Integer, ISession> sessions = new ConcurrentHashMap<>();
    private final Map<String, Integer> connectionIdToSessionId = new ConcurrentHashMap<>();
    private final Map<String, SessionAccessLog> accessLog = new ConcurrentHashMap<>();
    private final Set<Integer> lotteryEntries = Sets.newConcurrentHashSet();

    /**
     * Creates a session manager instance for the network session subsystem.
     */
    public SessionManager() {
        SessionManagerAccessor.getInstance().setSessionManager(this);
    }

    /**
     * Executes add for this network session contract.
     *
     * @param connection Connection supplied by the caller.
     * @return Result produced by the mutation.
     */
    public Session add(final Connection connection) {
        final int sessionId = this.idGenerator.incrementAndGet();
        Session session = new Session(connection, sessionId);
        session.initialise();

        if (this.sessions.putIfAbsent(sessionId, session) != null) {
            return null;
        }

        this.connectionIdToSessionId.put(connection.getId(), sessionId);
        return session;
    }

    /**
     * Executes remove for this network session contract.
     *
     * @param connection Connection supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean remove(final Connection connection) {
        final Integer sessionId = this.connectionIdToSessionId.remove(connection.getId());

        if (sessionId == null) {
            return false;
        }

        if (this.sessions.containsKey(sessionId)) {
            this.sessions.remove(sessionId);
            return true;
        }

        return false;
    }

    /**
     * Executes disconnect by player id for this network session contract.
     *
     * @param id Id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean disconnectByPlayerId(int id) {
        if (PlayerManager.getInstance().getSessionIdByPlayerId(id) == -1) {
            return false;
        }

        int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(id);
        Session session = (Session) sessions.get(sessionId);

        if (session != null) {
            session.disconnect();
            return true;
        }

        return false;
    }

    /**
     * Returns the by player id for this network session contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Session getByPlayerId(int id) {
        if (PlayerManager.getInstance().getSessionIdByPlayerId(id) != -1) {
            int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(id);

            return (Session) sessions.get(sessionId);
        }

        return null;
    }

    /**
     * Returns the by player permission for this network session contract.
     *
     * @param permission Permission supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Set<ISession> getByPlayerPermission(String permission) {
        // TODO: Optimize this
        Set<ISession> sessions = new HashSet<>();

//        int rank = PermissionsManager.getInstance().getPermissions().get(permission).getRank();
//
//        for (Map.Entry<Integer, ISession> session : this.sessions.entrySet()) {
//            if (session.getValue().getPlayer() != null) {
//                if (((Session) session.getValue()).getPlayer().getData().getRank() >= rank) {
//                    sessions.add(session.getValue());
//                }
//            }
//        }

        return sessions;
    }

    /**
     * Returns the by player username for this network session contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Session getByPlayerUsername(String username) {
        int playerId = PlayerManager.getInstance().getPlayerIdByUsername(username);

        if (playerId == -1)
            return null;

        int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(playerId);

        if (sessionId == -1)
            return null;

        if (this.sessions.containsKey(sessionId))
            return (Session) this.sessions.get(sessionId);

        return null;
    }

    /**
     * Adds lottery to this network session contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void addLottery(final int playerId) {
        this.lotteryEntries.add(playerId);
    }

    /**
     * Returns the lottery entries for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getLotteryEntries () {
        return this.lotteryEntries;
    }

    /**
     * Executes clear lottery for this network session contract.
     */
    public void clearLottery(){
        this.lotteryEntries.clear();
    }

    /**
     * Returns the users online count for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public int getUsersOnlineCount() {
        return PlayerManager.getInstance().size();
    }

    /**
     * Returns the sessions for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, ISession> getSessions() {
        return this.sessions;
    }

    /**
     * Executes broadcast for this network session contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void broadcast(IMessageComposer msg) {
        for (ISession session : this.sessions.values()) {
            session.send(msg);
        }
    }

    /**
     * Executes broadcast to moderators for this network session contract.
     *
     * @param messageComposer Message composer supplied by the caller.
     */
    public void broadcastToModerators(IMessageComposer messageComposer) {
        for (ISession session : this.sessions.values()) {
            if (session.getPlayer() != null && session.getPlayer().getPermissions() != null && session.getPlayer().getPermissions().getRank().modTool()) {
                session.send(messageComposer);
            }
        }
    }

    /**
     * Executes parse command for this network session contract.
     *
     * @param message Message supplied by the caller.
     * @param connection Connection supplied by the caller.
     */
    @Override
    public void parseCommand(String[] message, Connection connection) {
        String password = message[0];

        if (password.equals("cometServer")) {
            String command = message[1];

            if ("stats".equals(command)) {
                connection.sendRaw("response||" + JsonUtil.getInstance().toJson(Comet.getStats()));
            } else {
                connection.sendRaw("response||You're connected!");
            }
        } else {
            connection.close(ConnectionCloseCode.AUTHENTICATION_FAILED);
        }
    }

    /**
     * Returns the access log for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, SessionAccessLog> getAccessLog() {
        return accessLog;
    }
}