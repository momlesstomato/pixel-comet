package com.cometproject.server.game.gamecenter;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.players.data.GamePlayer;
import com.cometproject.server.storage.queries.catalog.BetDao;
import com.cometproject.server.tasks.CometThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Manages game center runtime state for the Comet subsystem.
 */
public class GameCenterManager implements Startable {
    private static int gameId;
    private List<GamePlayer> currentWeek;
    private List<GamePlayer> lastWeek;
    private Logger LOGGER = LoggerFactory.getLogger(GameCenterManager.class.getName());

    private List<GameCenterInfo> gamesList;

    /**
     * Creates a game center manager instance for the Comet subsystem.
     */
    public GameCenterManager() {
    }

    /**
     * Starts this Comet component.
     */
    @Override
    public void start() {
        this.gamesList = new ArrayList<>();

        this.loadLeaderboards();
        this.loadGameCenterList();

        LOGGER.info("GameCenter initialized.");
    }

    private void loadLeaderboards() {
        if (this.currentWeek != null) {
            this.currentWeek.clear();
        }

        if (this.lastWeek != null) {
            this.lastWeek.clear();
        }

        this.currentWeek = BetDao.getLeaderBoard(1, 0, false, false);
        this.lastWeek = BetDao.getLeaderBoard(1, 0, true, false);

        // Queue it to be refreshed again in 5 minutes.
        CometThreadManager.getInstance().executeSchedule(this::loadLeaderboards,1, TimeUnit.MINUTES);
    }

    /**
     * Returns the leaderboard by week for this Comet contract.
     *
     * @param isCurrent Is current supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<GamePlayer> getLeaderboardByWeek(boolean isCurrent){
        if(isCurrent)
        return this.currentWeek;
        else return lastWeek;
    }

    /**
     * Returns the instance for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public static GameCenterManager getInstance() {
        return CometBootstrap.resolve(GameCenterManager.class);
    }

    /**
     * Loads game center list for this Comet contract.
     */
    public void loadGameCenterList() {
        if(!this.gamesList.isEmpty()) {
            this.gamesList.clear();
        }

        this.gamesList = BetDao.getGames();
    }

    /**
     * Returns the game by id for this Comet contract.
     *
     * @param gameId Game id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public GameCenterInfo getGameById(int gameId){

        GameCenterInfo gameInfo = null;
        for(GameCenterInfo infoGame : this.gamesList){
            if(infoGame.getGameId() == gameId){
                gameInfo = infoGame;
                break;
            }
        }
        return gameInfo;
    }

    /**
     * Returns the games list for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public List<GameCenterInfo> getGamesList() {
        return this.gamesList;
    }
}
