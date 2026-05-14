package com.cometproject.server.game.rooms.types.components.games.survival.types;

import com.cometproject.api.config.CometSettings;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketSessionManager;
import com.cometproject.server.network.websockets.packets.outgoing.battleroyale.BattleRoyaleQueueWebPacket;
import com.cometproject.server.network.websockets.packets.outgoing.battleroyale.BattleRoyaleResetQueueWebPacket;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Describes survival queue behavior for the room processing subsystem.
 */
public class SurvivalQueue {

    private static SurvivalQueue instance;

    private final Map<Integer, List<Integer>> roomQueues;
    private final Map<Integer, ArrayList<QueueData>> figures;
    private final List<SurvivalScenario> availableScenarios;

    /**
     * Creates a survival queue instance for the room processing subsystem.
     */
    public SurvivalQueue() {
        this.roomQueues = Maps.newConcurrentMap();
        this.figures = Maps.newConcurrentMap();
        this.availableScenarios = new ArrayList<>();
        this.roomQueues.put(1, new ArrayList<>());
        this.figures.put(1, new ArrayList<>());
        this.availableScenarios.add(new SurvivalScenario(7360, true));
        this.availableScenarios.add(new SurvivalScenario(7361, true));
        this.availableScenarios.add(new SurvivalScenario(1402, true));
        this.availableScenarios.add(new SurvivalScenario(1403, true));
        this.availableScenarios.add(new SurvivalScenario(1404, true));
        this.availableScenarios.add(new SurvivalScenario(1405, true));
        this.availableScenarios.add(new SurvivalScenario(1406, true));
        this.availableScenarios.add(new SurvivalScenario(1407, true));
        this.availableScenarios.add(new SurvivalScenario(1408, true));
        this.availableScenarios.add(new SurvivalScenario(1409, true));
        this.availableScenarios.add(new SurvivalScenario(1410, true));
    }

    /**
     * Returns the available scenario for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAvailableScenario() {
        for (SurvivalScenario scenario : this.availableScenarios) {
            if (scenario.availability) {

                if(this.getQueue(scenario.roomId) != null && this.getQueue(scenario.roomId).size() >= CometSettings.survivalMinQueue)
                    scenario.updateAvailability(false);

                return scenario.roomId;
            }
        }
        return 0;
    }

    private void checkScenarios(int roomId, boolean value){
        for (SurvivalScenario scenario : this.availableScenarios) {
            if (scenario.roomId == roomId) {
                //if(this.getQueue(scenario.roomId) != null && this.getQueue(scenario.roomId).size() >= 10)
                    scenario.updateAvailability(value);
            }
        }
    }

    /**
     * Returns the instance for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public static SurvivalQueue getInstance() {
        if (instance == null) {
            instance = new SurvivalQueue();
        }

        return instance;
    }

    /**
     * Indicates whether this room processing contract has queue.
     *
     * @param roomId Room identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasQueue(int roomId) {
        return this.roomQueues.containsKey(roomId);
    }

    /**
     * Returns the queue for this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public List<Integer> getQueue(int roomId) {
        return this.roomQueues.get(roomId);
    }

    private SurvivalScenario getScenarioStatus(int roomId){
        for (SurvivalScenario scenario : this.availableScenarios) {
            if (scenario.roomId == roomId) {
                return scenario;
            }
        }
        return null;
    }

    /**
     * Returns the figures for this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public ArrayList<QueueData> getFigures(int roomId) {
        return this.figures.get(roomId);
    }

    /**
     * Adds queue to this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param startingPlayer Starting player supplied by the caller.
     * @param figure Figure supplied by the caller.
     */
    public void addQueue(int roomId, int startingPlayer, ArrayList<String> figure) {
        this.roomQueues.put(roomId, startingPlayer == 0 ? new ArrayList<>() : new ArrayList<>(startingPlayer));
        this.figures.put(roomId, new ArrayList<>());
    }

    /**
     * Removes queue from this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void removeQueue(int roomId) {
        this.roomQueues.remove(roomId);
    }

    /**
     * Removes player from queue from this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @param data Data supplied by the caller.
     */
    public void removePlayerFromQueue(int roomId, Integer playerId, QueueData data) {
        if (this.hasQueue(roomId)) {
            this.roomQueues.get(roomId).remove(playerId);

            if(this.getScenarioStatus(roomId) != null)
                this.checkScenarios(roomId, true);

            System.out.print("\n{ REMOVING PLAVER: " + data.playerId + " WITH FIGURE: " + data.figure + " }\n");
            this.figures.get(roomId).remove(data);
            System.out.print("{ SIZE OF THE LIST: " + this.figures.get(roomId).size() +" }\n");


            Session leavingPlayer = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

            if (leavingPlayer != null) {
                WebSocketSessionManager.getInstance().sendMessage(leavingPlayer.getWsChannel(), new BattleRoyaleResetQueueWebPacket("br_queue_leave"));
            }

            for (int player : this.roomQueues.get(roomId)) {
                Session session = NetworkManager.getInstance().getSessions().getByPlayerId(player);

                if (session != null) {
                    WebSocketSessionManager.getInstance().sendMessage(session.getWsChannel(), new BattleRoyaleQueueWebPacket("br_queue", roomId, SurvivalQueue.getInstance().getFigures(roomId)));
                }
            }
        }
    }

    /**
     * Executes player has queue for this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean playerHasQueue(int roomId, int playerId) {
        if (!this.hasQueue(roomId))
            return false;


        if(this.getQueue(roomId).contains(playerId))
            return true;

        return false;
    }

    /**
     * Executes start game for this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void startGame(int roomId){
        for (int player : this.roomQueues.get(roomId)) {
            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(player);

            if (session != null) {
                session.getPlayer().sendBubble("newuser", "Started properly Survival game after handeling the queue.");
                session.send(new RoomForwardMessageComposer(roomId));
                // SEND SOCKET WITH QUEUE STATUS
                WebSocketSessionManager.getInstance().sendMessage(session.getWsChannel(), new BattleRoyaleResetQueueWebPacket("br_queue_leave"));
            }
        }

        if(this.getScenarioStatus(roomId) != null)
            this.checkScenarios(roomId, false);

        this.roomQueues.get(roomId).clear();
        this.figures.get(roomId).clear();
    }

    /**
     * Adds player to queue to this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @param data Data supplied by the caller.
     */
    public void addPlayerToQueue(int roomId, int playerId, QueueData data) {
        if (!this.hasQueue(roomId)) {
            return;
        }

        this.getQueue(roomId).add(playerId);
        this.getFigures(roomId).add(data);

        for (int player : this.roomQueues.get(roomId)) {
            Session session = NetworkManager.getInstance().getSessions().getByPlayerId(player);

            if (session != null) {
                WebSocketSessionManager.getInstance().sendMessage(session.getWsChannel(), new BattleRoyaleQueueWebPacket("br_queue", roomId, SurvivalQueue.getInstance().getFigures(roomId)));
            }
        }
    }

    /**
     * Returns the next player for this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public int getNextPlayer(int roomId) {
        if (!this.hasQueue(roomId)) {
            return 0;
        }

        return this.getQueue(roomId).get(0);
    }

    /**
     * Returns the queue count for this room processing contract.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public int getQueueCount(int roomId, int playerId) {
        if (!this.hasQueue(roomId)) {
            return 0;
        }

        int size = 0;

        for (int player : this.getQueue(roomId)) {
            if (player == playerId) {
                break;
            }

            size++;
        }

        return size;
    }
}

