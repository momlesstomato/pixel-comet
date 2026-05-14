package com.cometproject.server.game.rooms.types.components;

import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.football.FootballScoreFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.AbstractGameGateFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerScoreAchieved;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.GameType;
import com.cometproject.server.game.rooms.types.components.games.RoomGame;
import com.cometproject.server.game.rooms.types.components.games.banzai.BanzaiGame;
import com.cometproject.server.game.rooms.types.components.games.casino.CasinoGame;
import com.cometproject.server.game.rooms.types.components.games.freeze.FreezeGame;
import com.cometproject.server.game.rooms.types.components.games.survival.SurvivalGame;
import com.cometproject.server.network.messages.outgoing.room.permissions.YouArePlayingGameMessageComposer;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Owns game behavior inside the room processing subsystem.
 */
public class GameComponent {
    private final AtomicInteger blobCounter = new AtomicInteger(0);
    private Room room;
    private RoomGame instance;
    private Map<GameTeam, List<Integer>> teams;
    private Map<GameTeam, Integer> scores;
    private Map<GameTeam, Set<AbstractGameGateFloorItem>> gates;
    private Set<PlayerEntity> players;

    /**
     * Creates a game component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public GameComponent(Room room) {
        this.teams = new HashMap<GameTeam, List<Integer>>() {{
            put(GameTeam.BLUE, Lists.newArrayList());
            put(GameTeam.YELLOW, Lists.newArrayList());
            put(GameTeam.RED, Lists.newArrayList());
            put(GameTeam.GREEN, Lists.newArrayList());
        }};

        this.gates = new HashMap<GameTeam, Set<AbstractGameGateFloorItem>>() {{
            put(GameTeam.BLUE, Sets.newHashSet());
            put(GameTeam.YELLOW, Sets.newHashSet());
            put(GameTeam.RED, Sets.newHashSet());
            put(GameTeam.GREEN, Sets.newHashSet());
        }};

        this.players = new ConcurrentHashSet<>();

        this.resetScores();
        this.room = room;
    }

    /**
     * Releases resources owned by this room processing component.
     */
    public void dispose() {
        for (Map.Entry<GameTeam, List<Integer>> entry : this.teams.entrySet()) {
            entry.getValue().clear();
        }

        for (Map.Entry<GameTeam, Set<AbstractGameGateFloorItem>> team : this.gates.entrySet()) {
            team.getValue().clear();
        }

        this.gates.clear();
        this.teams.clear();
        this.scores.clear();
    }

    /**
     * Stops this room processing component.
     */
    public void stop() {
        if (this.instance != null) {
            this.instance.stop();
        }

        this.instance = null;
    }

    /**
     * Creates new for this room processing contract.
     *
     * @param game Game supplied by the caller.
     */
    public void createNew(GameType game) {
        if (game == GameType.BANZAI) {
            this.instance = new BanzaiGame(this.room);
        } else if (game == GameType.FREEZE) {
            this.instance = new FreezeGame(this.room);
        } else if (game == GameType.CASINO) {
            this.instance = new CasinoGame(this.room);
        } else if (game == GameType.SURVIVAL) {
            this.instance = new SurvivalGame(this.room);
        }
    }

    /**
     * Indicates whether teamed applies to this room processing contract.
     *
     * @param id Id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isTeamed(int id) {
        return this.getTeam(id) != GameTeam.NONE;
    }

    /**
     * Executes join team for this room processing contract.
     *
     * @param team Team supplied by the caller.
     * @param entity Entity supplied by the caller.
     */
    public void joinTeam(GameTeam team, PlayerEntity entity) {
        this.teams.get(team).add(entity.getPlayerId());
        this.players.add(entity);

        entity.getPlayer().getSession().send(new YouArePlayingGameMessageComposer(true));
    }

    /**
     * Removes from team from this room processing contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void removeFromTeam(PlayerEntity entity) {
        if (entity.getGameTeam() == null || entity.getGameTeam() == GameTeam.NONE) {
            return;
        }

        if (this.teams.get(entity.getGameTeam()).contains(entity.getPlayerId())) {
            this.teams.get(entity.getGameTeam()).remove((Integer) entity.getPlayerId());
        }

        this.players.remove(entity);

        entity.getPlayer().getSession().send(new YouArePlayingGameMessageComposer(false));
    }

    /**
     * Returns the team for this room processing contract.
     *
     * @param userId User id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public GameTeam getTeam(int userId) {
        for (Map.Entry<GameTeam, List<Integer>> entry : this.getTeams().entrySet()) {
            if (entry.getValue().contains(userId)) {
                return entry.getKey();
            }
        }

        return GameTeam.NONE;
    }

    /**
     * Executes decrease score for this room processing contract.
     *
     * @param team Team supplied by the caller.
     * @param amount Amount supplied by the caller.
     */
    public void decreaseScore(GameTeam team, int amount) {
        if (!this.scores.containsKey(team)) {
            return;
        }

        this.scores.replace(team, this.scores.get(team) - amount);
        this.scoreUpdated(team);
    }

    /**
     * Executes increase score for this room processing contract.
     *
     * @param team Team supplied by the caller.
     * @param amount Amount supplied by the caller.
     */
    public void increaseScore(GameTeam team, int amount) {
        if (!this.scores.containsKey(team)) {
            return;
        }

        this.scores.replace(team, this.scores.get(team) + amount);
        this.scoreUpdated(team);
    }

    /**
     * Executes reset scores for this room processing contract.
     */
    public void resetScores() {
        this.resetScores(false);
    }

    /**
     * Executes reset scores for this room processing contract.
     *
     * @param update Update supplied by the caller.
     */
    public void resetScores(boolean update) {
        if (this.scores != null)
            this.scores.clear();

        this.scores = new ConcurrentHashMap<GameTeam, Integer>() {{
            put(GameTeam.BLUE, 0);
            put(GameTeam.YELLOW, 0);
            put(GameTeam.GREEN, 0);
            put(GameTeam.RED, 0);
        }};

        if (update) {
            this.scoreUpdated(GameTeam.BLUE);
            this.scoreUpdated(GameTeam.RED);
            this.scoreUpdated(GameTeam.GREEN);
            this.scoreUpdated(GameTeam.YELLOW);
        }
    }

    private void scoreUpdated(GameTeam team) {
        for (RoomItemFloor scoreItem : this.getRoom().getItems().getByClass(FootballScoreFloorItem.class)) {
            scoreItem.sendUpdate();
        }

        for (RoomItemFloor scoreboard : this.getRoom().getItems().getByInteraction("%_score%")) {
            if (team == null || scoreboard.getDefinition().getInteraction().toUpperCase().startsWith(team.name()) || scoreboard.getDefinition().getItemName().endsWith("score_" + team.getTeamLetter())) {
                scoreboard.getItemData().setData(team == null ? "0" : this.getScore(team) + "");
                scoreboard.sendUpdate();
            }
        }

        WiredTriggerScoreAchieved.executeTriggers(this.getRoom().getGame().getScore(team), team, this.getRoom());
    }

    /**
     * Returns the gates for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<GameTeam, Set<AbstractGameGateFloorItem>> getGates() {
        return this.gates;
    }

    /**
     * Returns the score for this room processing contract.
     *
     * @param team Team supplied by the caller.
     * @return Value exposed by the contract.
     */
    public int getScore(GameTeam team) {
        return this.scores.get(team);
    }


    /**
     * Returns the teams for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<GameTeam, List<Integer>> getTeams() {
        return teams;
    }

    /**
     * Returns the instance for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomGame getInstance() {
        return this.instance;
    }

    /**
     * Returns the room for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Returns the scores for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<GameTeam, Integer> getScores() {
        return scores;
    }

    /**
     * Returns the players for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<PlayerEntity> getPlayers() {
        return this.players;
    }

    /**
     * Returns the blob counter for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public AtomicInteger getBlobCounter() {
        return blobCounter;
    }
}
