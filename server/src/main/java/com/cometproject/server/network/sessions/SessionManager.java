package com.cometproject.server.network.sessions;

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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public final class SessionManager implements ISessionManager, ISessionService {
    public static boolean isLocked = false;
    private final AtomicInteger idGenerator = new AtomicInteger();
    private final Map<Integer, ISession> sessions = new ConcurrentHashMap<>();
    private final Map<String, Integer> connectionIdToSessionId = new ConcurrentHashMap<>();
    private final Map<String, SessionAccessLog> accessLog = new ConcurrentHashMap<>();
    private final Set<Integer> lotteryEntries = Sets.newConcurrentHashSet();

    public SessionManager() {
        SessionManagerAccessor.getInstance().setSessionManager(this);
    }

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

    public Session getByPlayerId(int id) {
        if (PlayerManager.getInstance().getSessionIdByPlayerId(id) != -1) {
            int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(id);

            return (Session) sessions.get(sessionId);
        }

        return null;
    }

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

    public void addLottery(final int playerId) {
        this.lotteryEntries.add(playerId);
    }

    public Set<Integer> getLotteryEntries () {
        return this.lotteryEntries;
    }

    public void clearLottery(){
        this.lotteryEntries.clear();
    }

    public int getUsersOnlineCount() {
        return PlayerManager.getInstance().size();
    }

    public Map<Integer, ISession> getSessions() {
        return this.sessions;
    }

    public void broadcast(IMessageComposer msg) {
        for (ISession session : this.sessions.values()) {
            session.send(msg);
        }
    }

    public void broadcastToModerators(IMessageComposer messageComposer) {
        for (ISession session : this.sessions.values()) {
            if (session.getPlayer() != null && session.getPlayer().getPermissions() != null && session.getPlayer().getPermissions().getRank().modTool()) {
                session.send(messageComposer);
            }
        }
    }

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

    public Map<String, SessionAccessLog> getAccessLog() {
        return accessLog;
    }
}