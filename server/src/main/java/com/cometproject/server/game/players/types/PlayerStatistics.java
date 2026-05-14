package com.cometproject.server.game.players.types;

import com.cometproject.api.game.players.data.IPlayerStatistics;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.server.storage.queries.player.messenger.MessengerDao;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes player statistics behavior for the player subsystem.
 */
public class PlayerStatistics implements IPlayerStatistics {
    private int playerId;

    private int experiencePoints;
    private int level;

    private final Player player;

    private int dailyRespects;
    private int respectPoints;
    private int dailyRolls;

    private int scratches;
    private int fireworks;

    private int helpTickets;
    private int abusiveHelpTickets;
    private int cautions;
    private int bans;
    private long tradeLock;

    /**
     * Creates a player statistics instance for the player subsystem.
     *
     * @param data Data supplied by the caller.
     * @param isLogin Is login supplied by the caller.
     * @param player Player participating in the operation.
     * @throws SQLException When the operation cannot complete.
     */
    public PlayerStatistics(ResultSet data, boolean isLogin, Player player) throws SQLException {
        if (isLogin) {
            this.playerId = data.getInt("playerId");
            this.experiencePoints = data.getInt("playerStats_experiencePoints");
            this.level = data.getInt("playerStats_level");
            this.dailyRespects = data.getInt("playerStats_dailyRespects");
            this.respectPoints = data.getInt("playerStats_totalRespectPoints");
            this.helpTickets = data.getInt("playerStats_helpTickets");
            this.abusiveHelpTickets = data.getInt("playerStats_helpTicketsAbusive");
            this.cautions = data.getInt("playerStats_cautions");
            this.bans = data.getInt("playerStats_bans");
            this.scratches = data.getInt("playerStats_scratches");
            this.tradeLock = data.getLong("playerStats_tradeLock");
            this.fireworks = data.getInt("playerStats_fireworks");
            this.dailyRolls = data.getInt("playerStats_dailyRolls");
        } else {
            this.playerId = data.getInt("player_id");
            this.experiencePoints = data.getInt("experience_points");
            this.level = data.getInt("level");
            this.dailyRespects = data.getInt("daily_respects");
            this.respectPoints = data.getInt("total_respect_points");
            this.helpTickets = data.getInt("help_tickets");
            this.abusiveHelpTickets = data.getInt("help_tickets_abusive");
            this.cautions = data.getInt("cautions");
            this.bans = data.getInt("bans");
            this.scratches = data.getInt("daily_scratches");
            this.tradeLock = data.getInt("trade_lock");
            this.fireworks = data.getInt("fireworks");
            this.dailyRolls = data.getInt("daily_rolls");
        }

        this.player = player;
    }

    /**
     * Creates a player statistics instance for the player subsystem.
     *
     * @param userId User id supplied by the caller.
     */
    public PlayerStatistics(int userId) {
        this.playerId = userId;
        this.experiencePoints = 0;
        this.level = 1;
        this.respectPoints = 0;
        this.dailyRespects = 3;
        this.scratches = 3;
        this.helpTickets = 0;
        this.abusiveHelpTickets = 0;
        this.cautions = 0;
        this.bans = 0;
        this.tradeLock = 0;
        this.fireworks = 0;
        this.dailyRolls = 5;
        this.player = null;
    }

    /**
     * Executes save for this player contract.
     */
    public void save() {
        PlayerDao.updatePlayerStatistics(this);
    }

    /**
     * Persists fireworks for this player contract.
     */
    public void saveFireworks(){
        PlayerDao.updateFireworks(this.fireworks, this.playerId);
    }

    /**
     * Executes increment experience points for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void incrementExperiencePoints(int amount) {
        this.experiencePoints += amount;
        this.save();
    }

    /**
     * Returns the experience points for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getExperiencePoints() {
        return this.experiencePoints;
    }


    /**
     * Returns the level for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the fireworks for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getFireworks(){
        return this.fireworks;
    }

    /**
     * Executes increment fireworks for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void incrementFireworks(int amount) {
        this.fireworks += amount;
    }

    /**
     * Executes decrement fireworks for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void decrementFireworks(int amount) {
        this.fireworks -= amount;
    }

    /**
     * Executes increment level for this player contract.
     */
    public void incrementLevel() {
        this.level++;
        this.save();
    }

    /**
     * Executes increment cautions for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void incrementCautions(int amount) {
        this.cautions += amount;
        this.save();
    }

    /**
     * Executes increment respect points for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void incrementRespectPoints(int amount) {
        this.respectPoints += amount;
        this.save();
    }

    /**
     * Executes decrement daily respects for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void decrementDailyRespects(int amount) {
        this.dailyRespects -= amount;
        this.save();
    }

    /**
     * Executes decrement daily rolls for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void decrementDailyRolls(int amount) {
        this.dailyRolls -= amount;
        PlayerDao.updateDailyRolls(this.dailyRolls, this.getPlayerId());
    }

    /**
     * Executes increment bans for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void incrementBans(int amount) {
        this.bans += amount;
    }

    /**
     * Executes increment abusive help tickets for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void incrementAbusiveHelpTickets(int amount) {
        this.abusiveHelpTickets += amount;
    }

    /**
     * Returns the player id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Returns the daily respects for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDailyRespects() {
        return this.dailyRespects;
    }

    /**
     * Returns the daily rolls for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDailyRolls() {
        return this.dailyRolls;
    }

    /**
     * Updates the daily respects for this player contract.
     *
     * @param points Points supplied by the caller.
     */
    @Override
    public void setDailyRespects(int points) {
        this.dailyRespects = points;
    }

    /**
     * Updates the daily rolls for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    @Override
    public void setDailyRolls(int amount) {
        this.dailyRolls = amount;
    }


    /**
     * Returns the respect points for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRespectPoints() {
        return this.respectPoints;
    }

    /**
     * Returns the friend count for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getFriendCount() {
        return MessengerDao.getFriendCount(this.playerId);
    }

    /**
     * Returns the help tickets for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getHelpTickets() {
        return helpTickets;
    }

    /**
     * Updates the help tickets for this player contract.
     *
     * @param helpTickets Help tickets supplied by the caller.
     */
    public void setHelpTickets(int helpTickets) {
        this.helpTickets = helpTickets;
    }

   /**
    * Executes level pass for this player contract.
    *
    * @return True when the condition is satisfied; otherwise false.
    */
   @Override
   public boolean levelPass(){
        return this.level >= 2;
   }

    /**
     * Returns the abusive help tickets for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAbusiveHelpTickets() {
        return abusiveHelpTickets;
    }

    /**
     * Updates the abusive help tickets for this player contract.
     *
     * @param abusiveHelpTickets Abusive help tickets supplied by the caller.
     */
    public void setAbusiveHelpTickets(int abusiveHelpTickets) {
        this.abusiveHelpTickets = abusiveHelpTickets;
    }

    /**
     * Returns the cautions for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCautions() {
        return cautions;
    }

    /**
     * Updates the cautions for this player contract.
     *
     * @param cautions Cautions supplied by the caller.
     */
    public void setCautions(int cautions) {
        this.cautions = cautions;
    }

    /**
     * Returns the bans for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBans() {
        return bans;
    }

    /**
     * Updates the bans for this player contract.
     *
     * @param bans Bans supplied by the caller.
     */
    public void setBans(int bans) {
        this.bans = bans;
    }

    /**
     * Adds ban to this player contract.
     */
    public void addBan() {
        this.bans = this.bans++;
    }

    /**
     * Returns the scratches for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getScratches() {
        return scratches;
    }

    /**
     * Updates the scratches for this player contract.
     *
     * @param scratches Scratches supplied by the caller.
     */
    @Override
    public void setScratches(int scratches) {
        this.scratches = scratches;
    }

    /**
     * Executes to JSON for this player contract.
     *
     * @return Result produced by the operation.
     */
    public JsonElement toJson() {
        final JsonObject coreObject = new JsonObject();
        coreObject.addProperty("achievementPoints", experiencePoints);
        coreObject.addProperty("level", level);
        coreObject.addProperty("dailyRespects", dailyRespects);
        coreObject.addProperty("respectPoints", respectPoints);
        coreObject.addProperty("scratches", scratches);
        coreObject.addProperty("helpTickets", helpTickets);
        coreObject.addProperty("abusiveHelpTickets", abusiveHelpTickets);
        coreObject.addProperty("cautions", cautions);
        coreObject.addProperty("bans", bans);
        coreObject.addProperty("tradeLock", tradeLock);
        coreObject.addProperty("fireworks", fireworks);
        coreObject.addProperty("dailyRolls", dailyRolls);
        return coreObject;
    }

    /**
     * Returns the player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the trade lock for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getTradeLock() {
        return tradeLock;
    }

    /**
     * Updates the trade lock for this player contract.
     *
     * @param tradeLock Trade lock supplied by the caller.
     */
    public void setTradeLock(long tradeLock) {
        this.tradeLock = tradeLock;
    }
}
