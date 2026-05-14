package com.cometproject.server.game.players.data;

import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.achievements.BattlePassGlobals;
import com.cometproject.server.game.achievements.types.BattlePassMission;
import com.cometproject.server.game.achievements.types.BattlePassMissionStats;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.objects.items.RoomItem;
import com.cometproject.server.game.utilities.validator.PlayerFigureValidator;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.achievements.AchievementPointsMessageComposer;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Carries player data data for the player subsystem.
 */
public class PlayerData implements IPlayerData {
    public static final String DEFAULT_FIGURE = "hr-100-61.hd-180-2.sh-290-91.ch-210-66.lg-270-82";
    private static final String CREDITS_CURRENCY_CODE = "credits";
    private static final int PROTOCOL_CURRENCY_0 = 0;
    private static final int PROTOCOL_CURRENCY_5 = 5;
    private static final int PROTOCOL_CURRENCY_103 = 103;
    private static final int PROTOCOL_CURRENCY_105 = 105;

    private int id;
    private int rank;

    private Player player;

    private String username;
    private String motto;
    private String legacyMotto;
    private String figure;
    private String gender;
    private String email;

    private String ipAddress;

    private int credits;
    private final Map<String, Integer> currencyBalances = new ConcurrentHashMap<>();

    private String regDate;
    private int lastVisit;
    private int regTimestamp;
    private int achievementPoints;

    private int favouriteGroup;

    private String temporaryFigure;

    private boolean vip;
    private int questId;

    private boolean ask = false;
    private int timeMuted;
    private int karma;
    private int prestige;
    private String nameColour;
    private String tag;
    private String job;

    private boolean changingName = false;

    private boolean flaggingUser = false;

    private Object tempData = null;
    private static final Gson gson = new Gson();

    public RoomItem itemOnBet = null;
    public boolean hasPaidBet = false;
    public int betMoney = 0;
    public int betNumber = 0;
    public int coinOnBet = 0;
    public String kiss_player = null;
    public String sex_player = null;
    public boolean isSearchFurniActive = false;
    public PlayerBattlePassInfo battlePass = null;
    public int viewPoints = 0;

    /**
     * Creates a player data instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param motto Motto supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param email Email supplied by the caller.
     * @param rank Rank supplied by the caller.
     * @param credits Credits supplied by the caller.
     * @param currency5 Currency5 supplied by the caller.
     * @param currency0 Currency0 supplied by the caller.
     * @param currency103 Currency103 supplied by the caller.
     * @param currency105 Currency105 supplied by the caller.
     * @param reg Reg supplied by the caller.
     * @param lastVisit Last visit supplied by the caller.
     * @param vip Vip supplied by the caller.
     * @param achievementPoints Achievement points supplied by the caller.
     * @param regTimestamp Reg timestamp supplied by the caller.
     * @param favouriteGroup Favourite group supplied by the caller.
     * @param ipAddress Ip address supplied by the caller.
     * @param questId Quest id supplied by the caller.
     * @param timeMuted Time muted supplied by the caller.
     * @param nameColour Name colour supplied by the caller.
     * @param tag Tag supplied by the caller.
     * @param job Job supplied by the caller.
     * @param viewPoints View points supplied by the caller.
     * @param player Player participating in the operation.
     */
    public PlayerData(int id, String username, String motto, String figure, String gender, String email, int rank, int credits, int currency5, int currency0,
                      int currency103, int currency105, String reg, int lastVisit, boolean vip, int achievementPoints, int regTimestamp, int favouriteGroup, String ipAddress, int questId, int timeMuted, String nameColour, String tag, String job, int viewPoints, Player player) {
        this.id = id;
        this.username = username;
        this.motto = motto;
        this.legacyMotto = motto;
        this.figure = figure;
        this.rank = rank;
        this.credits = credits;
        this.gender = gender;
        this.vip = vip;
        this.achievementPoints = achievementPoints;
        this.email = email;
        this.regDate = reg;
        this.lastVisit = lastVisit;
        this.regTimestamp = regTimestamp;
        this.favouriteGroup = favouriteGroup;
        this.ipAddress = ipAddress;
        this.questId = questId;
        this.timeMuted = timeMuted;
        this.nameColour = nameColour;
        this.tag = tag;
        this.job = job;
        this.viewPoints = viewPoints;
        this.currencyBalances.put(this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_0), currency0);
        this.currencyBalances.put(this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_5), currency5);
        this.currencyBalances.put(this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_103), currency103);
        this.currencyBalances.put(this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_105), currency105);

        if (this.figure != null) {
            if (!PlayerFigureValidator.isValidFigureCode(this.figure, this.gender.toLowerCase())) {
                this.figure = DEFAULT_FIGURE;
            }
        }

        PlayerBattlePassInfo bp = PlayerDao.getPlayerBattlePlassInfo(this.id);
        if(bp != null){
            this.battlePass = bp;
            this.battlePass.playerId = id;
            PlayerDao.PlayerBattlePassLoadMissionsCompleted(bp, id);
        }

        this.player = player;

        flush();
    }

    /**
     * Creates a player data instance for the player subsystem.
     *
     * @param data Data supplied by the caller.
     * @param player Player participating in the operation.
     * @throws SQLException When the operation cannot complete.
     */
    public PlayerData(ResultSet data, Player player) throws SQLException {
        this(data.getInt("playerId"),
                data.getString("playerData_username"),
                data.getString("playerData_motto"),
                data.getString("playerData_figure"),
                data.getString("playerData_gender"),
                data.getString("playerData_email"),
                data.getInt("playerData_rank"),
                data.getInt("playerData_credits"),
                data.getInt("playerData_currency5"),
                data.getInt("playerData_currency0"),
                data.getInt("playerData_currency103"),
                data.getInt("playerData_currency105"),
                data.getString("playerData_regDate"),
                data.getInt("playerData_lastOnline"),
                data.getString("playerData_vip").equals("1"),
                data.getInt("playerData_achievementPoints"),
                data.getInt("playerData_regTimestamp"),
                data.getInt("playerData_favouriteGroup"),
                data.getString("playerData_lastIp"),
                data.getInt("playerData_questId"),
                data.getInt("playerData_timeMuted"),
                data.getString("playerData_nameColour"),
                data.getString("playerData_tag"),
                data.getString("playerData_job"),
                data.getInt("playerData_viewPoints"),player);
    }

    /**
     * Executes temp data for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public Object tempData() {
        return this.tempData;
    }

    /**
     * Executes temp data for this player contract.
     *
     * @param obj Obj supplied by the caller.
     */
    @Override
    public void tempData(Object obj) {
        this.tempData = obj;
    }

    /**
     * Executes save for this player contract.
     */
    public void save() {
        this.saveNow();
    }

    /**
     * Persists now for this player contract.
     */
    public void saveNow() {
        PlayerDao.updatePlayerDataWithoutCurrencies(id, username, motto, figure, credits, gender, favouriteGroup, questId, achievementPoints, nameColour, tag, job);
    }

    /**
     * Executes decrease credits for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void decreaseCredits(int amount) {
        if (this.adjustInventory(CREDITS_CURRENCY_CODE, CurrencyOperation.REMOVE, amount)) {
            return;
        }

        this.credits -= amount;

        flush();
    }

    /**
     * Executes decrease currency for this player contract.
     *
     * @param currency Currency supplied by the caller.
     * @param amount Amount supplied by the caller.
     */
    public void decreaseCurrency(int currency, int amount){
        if (this.adjustInventory(this.currencyCodeForLegacyId(currency), CurrencyOperation.REMOVE, amount)) {
            return;
        }

        this.applyFallbackCurrencyDelta(this.currencyCodeForLegacyId(currency), -amount);

        flush();
    }

    /**
     * Executes increase credits for this player contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void increaseCredits(int amount) {
        if (this.adjustInventory(CREDITS_CURRENCY_CODE, CurrencyOperation.ADD, amount)) {
            return;
        }

        this.credits += amount;

        flush();
    }

    /**
     * Adds view point to this player contract.
     */
    public void addViewPoint(){
        this.viewPoints++;

        if (this.rank == 2) this.viewPoints++;

        PlayerDao.UpdateViewPoints(this.viewPoints, this.id);
    }

    /**
     * Executes increase achievement points for this player contract.
     *
     * @param points Points supplied by the caller.
     */
    public void increaseAchievementPoints(int points) {
        this.achievementPoints += points;

        int level = this.getPlayer().getStats().getLevel();
        boolean isLevelUp = this.achievementPoints >= (level * 1500);

        if(isLevelUp){
            this.getPlayer().getStats().incrementLevel();
            this.getPlayer().sendBubble("level_up", Locale.getOrDefault("level.up.text", "¡Acabas de subir a nivel %level%, recuerda que puedes recibir recompensas según tu nivel en la vista del hotel.").replace("%level%", level + 1 + ""));
        }

        this.getPlayer().poof();
        this.getPlayer().getSession().send(new AchievementPointsMessageComposer(this.achievementPoints, this.getPlayer().getStats() != null ? this.getPlayer().getStats().getLevel() : 1));
        flush();
    }

    /**
     * Returns the battle pass info for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getBattlePassInfo(){
        boolean isActive = false;
        int level = 0;
        int exp = 0;
        ArrayList<BattlePassMissionStats> missions = new ArrayList<BattlePassMissionStats>();

        if(this.battlePass != null){
            isActive = true;
            level = this.battlePass.level;
            exp = this.battlePass.exp;

            for(BattlePassMission mission : BattlePassGlobals.battlePassMissions){
                missions.add(new BattlePassMissionStats(mission.id, mission.missionName, this.battlePass.countExpMission(mission.id), mission.maxExp, mission.imageReward));
            }
        }
        else{
            for(BattlePassMission mission : BattlePassGlobals.battlePassMissions){
                missions.add(new BattlePassMissionStats(mission.id, mission.missionName, 0, mission.maxExp, mission.imageReward));
            }
        }

        return this.gson.toJson(Map.of(
                "handle", "openBattlePass",
                "isActive", isActive,
                "level", level,
                "exp", exp,
                "missionStats", missions,
                "name", this.username,
                "look", this.figure));
    }

    /**
     * Executes open battle pass for this player contract.
     */
    public void openBattlePass(){
        this.getPlayer().getSession().send(new MassEventMessageComposer("battlepass/open"));
    }

    /**
     * Updates battle pass for this player contract.
     */
    public void updateBattlePass(){
        this.getPlayer().getSession().send(new MassEventMessageComposer("battlepass/update"));
    }

    /**
     * Creates battle pass for this player contract.
     */
    public void createBattlePass(){
        this.battlePass = new PlayerBattlePassInfo(1, 1);
        this.battlePass.playerId = this.id;
        PlayerDao.PlayerBattlePassCreate(this.id);
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the rank for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Updates the rank for this player contract.
     *
     * @param rank Rank supplied by the caller.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Returns the username for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Updates the username for this player contract.
     *
     * @param username Username supplied by the caller.
     */
    public void setUsername(String username) {
        this.username = username;

        flush();
    }

    /**
     * Returns the legacy motto for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getLegacyMotto() {
        return legacyMotto;
    }

    /**
     * Returns the achievement points for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAchievementPoints() {
        return this.achievementPoints;
    }

    /**
     * Returns the karma for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getKarma() {
        return karma;
    }

    /**
     * Returns the prestige for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPrestige() {
        return prestige;
    }

    /**
     * Updates the achievement points for this player contract.
     *
     * @param achievementPoints Achievement points supplied by the caller.
     */
    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    /**
     * Returns the motto for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMotto() {
        return this.motto;
    }



    /**
     * Updates the motto for this player contract.
     *
     * @param motto Motto supplied by the caller.
     */
    public void setMotto(String motto) {
        this.motto = motto;

        flush();
    }

    /**
     * Updates the legacy motto for this player contract.
     *
     * @param legacyMotto Legacy motto supplied by the caller.
     */
    public void setLegacyMotto(String legacyMotto) {
        this.legacyMotto = legacyMotto;
    }

    /**
     * Returns the figure for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getFigure() {
        return this.figure;
    }

    /**
     * Updates the figure for this player contract.
     *
     * @param figure Figure supplied by the caller.
     */
    public void setFigure(String figure) {
        this.figure = figure;

        flush();
    }

    /**
     * Returns the gender for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Updates the gender for this player contract.
     *
     * @param gender Gender supplied by the caller.
     */
    public void setGender(String gender) {
        this.gender = gender;

        flush();
    }

    /**
     * Returns the credits for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCredits() {
        return this.credits;
    }

    /**
     * Updates the credits for this player contract.
     *
     * @param credits Credits supplied by the caller.
     */
    public void setCredits(int credits) {
        this.credits = credits;

        flush();
    }

    /**
     * Returns the last visit for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastVisit() {
        return this.lastVisit;
    }

    /**
     * Updates the last visit for this player contract.
     *
     * @param time Time supplied by the caller.
     */
    public void setLastVisit(long time) {
        this.lastVisit = (int) time;
    }

    /**
     * Returns the reg date for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRegDate() {
        return this.regDate;
    }

    /**
     * Indicates whether VIP applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isVip() {
        return this.vip;
    }

    /**
     * Updates the VIP for this player contract.
     *
     * @param vip Vip supplied by the caller.
     */
    public void setVip(boolean vip) {
        this.vip = vip;

        flush();
    }

    /**
     * Returns the job for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getJob() {
        return this.job;
    }

    /**
     * Updates the job for this player contract.
     *
     * @param job Job supplied by the caller.
     */
    public void setJob(String job) {
        this.job = job;
        this.save();
    }

    /**
     * Returns the ask for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getAsk() {
        return this.ask;
    }

    /**
     * Updates the ask for this player contract.
     *
     * @param ask Ask supplied by the caller.
     */
    public void setAsk(boolean ask) {
        this.ask = ask;
    }
    /**
     * Returns the reg timestamp for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRegTimestamp() {
        return regTimestamp;
    }

    /**
     * Updates the reg timestamp for this player contract.
     *
     * @param regTimestamp Reg timestamp supplied by the caller.
     */
    public void setRegTimestamp(int regTimestamp) {
        this.regTimestamp = regTimestamp;
    }

    /**
     * Returns the email for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email for this player contract.
     *
     * @param email Email supplied by the caller.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the favourite group for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getFavouriteGroup() {
        return favouriteGroup;
    }

    /**
     * Updates the favourite group for this player contract.
     *
     * @param favouriteGroup Favourite group supplied by the caller.
     */
    public void setFavouriteGroup(int favouriteGroup) {
        this.favouriteGroup = favouriteGroup;
    }

    /**
     * Returns the IP address for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Updates the IP address for this player contract.
     *
     * @param ipAddress Ip address supplied by the caller.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Returns the temporary figure for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTemporaryFigure() {
        return temporaryFigure;
    }

    /**
     * Updates the temporary figure for this player contract.
     *
     * @param temporaryFigure Temporary figure supplied by the caller.
     */
    public void setTemporaryFigure(String temporaryFigure) {
        this.temporaryFigure = temporaryFigure;
    }

    /**
     * Returns the quest id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getQuestId() {
        return questId;
    }

    /**
     * Updates the quest id for this player contract.
     *
     * @param questId Quest id supplied by the caller.
     */
    public void setQuestId(int questId) {
        this.questId = questId;
    }

    /**
     * Returns the time muted for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTimeMuted() {
        return this.timeMuted;
    }

    /**
     * Updates the time muted for this player contract.
     *
     * @param Time Time supplied by the caller.
     */
    public void setTimeMuted(int Time) {
        this.timeMuted = Time;

        flush();
    }

    /**
     * Returns the changing name for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getChangingName() {
        return this.changingName;
    }

    /**
     * Updates the changing name for this player contract.
     *
     * @param changingName Changing name supplied by the caller.
     */
    public void setChangingName(boolean changingName) {
        this.changingName = changingName;

        flush();
    }

    /**
     * Returns the flagging user for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean getFlaggingUser() {
        return this.flaggingUser;
    }

    /**
     * Updates the flagging user for this player contract.
     *
     * @param flaggingUser Flagging user supplied by the caller.
     */
    public void setFlaggingUser(boolean flaggingUser) {
        this.flaggingUser = flaggingUser;

        flush();
    }

    /**
     * Returns the name colour for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getNameColour() {
        return this.nameColour;
    }

    /**
     * Updates the name colour for this player contract.
     *
     * @param nameColour Name colour supplied by the caller.
     */
    @Override
    public void setNameColour(String nameColour) {
        this.nameColour = nameColour;

        flush();
    }

    /**
     * Returns the tag for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Updates the tag for this player contract.
     *
     * @param tag Tag supplied by the caller.
     */
    @Override
    public void setTag(String tag) {
        this.tag = tag;

        flush();
    }

    /**
     * Adds a configured inventory currency and refreshes in-memory snapshots.
     *
     * @param currencyCode the currency code or alias.
     * @param amount       the positive amount.
     */
    @Override
    public void increaseCurrency(final String currencyCode, final int amount) {
        if (this.adjustInventory(currencyCode, CurrencyOperation.ADD, amount)) {
            return;
        }

        this.applyFallbackCurrencyDelta(currencyCode, amount);
        flush();
    }

    /**
     * Removes a configured inventory currency and refreshes in-memory snapshots.
     *
     * @param currencyCode the currency code or alias.
     * @param amount       the positive amount.
     */
    @Override
    public void decreaseCurrency(final String currencyCode, final int amount) {
        if (this.adjustInventory(currencyCode, CurrencyOperation.REMOVE, amount)) {
            return;
        }

        this.applyFallbackCurrencyDelta(currencyCode, -amount);
        flush();
    }

    /**
     * Returns the current configured player inventory balance.
     *
     * @param currencyCode the currency code or alias.
     * @return the current balance, or zero when unavailable.
     */
    @Override
    public int getCurrencyBalance(final String currencyCode) {
        try {
            final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);

            return Math.toIntExact(currencyService.balance(this.id, currencyCode));
        } catch (IllegalStateException exception) {
            return this.cachedBalanceFor(currencyCode);
        }
    }

    /**
     * Applies a configured player inventory balance snapshot.
     *
     * @param currencyCode the currency code or alias.
     * @param balance      the exact balance.
     */
    @Override
    public void setCurrencyBalance(final String currencyCode, final int balance) {
        this.applyCurrencyBalance(currencyCode, balance);
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
     * Applies an inventory balance snapshot without recording a movement.
     *
     * @param currencyCode the canonical currency code.
     * @param balance      the exact balance.
     */
    public void applyCurrencyBalance(final String currencyCode, final long balance) {
        final int intBalance = Math.toIntExact(balance);
        final String resolvedCurrencyCode = this.resolveCurrencyCode(currencyCode);

        if (CREDITS_CURRENCY_CODE.equals(resolvedCurrencyCode)) {
            this.credits = intBalance;
        } else {
            this.currencyBalances.put(resolvedCurrencyCode, intBalance);
        }

        flush();
    }

    private String currencyCodeForLegacyId(final int currency) {
        return switch (currency) {
            case 2 -> this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_0);
            case 3 -> this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_5);
            case 4 -> this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_103);
            case 5 -> this.currencyCodeForProtocolId(PROTOCOL_CURRENCY_105);
            default -> CREDITS_CURRENCY_CODE;
        };
    }

    private String currencyCodeForProtocolId(final int protocolCurrencyId) {
        try {
            return CometBootstrap.resolve(ICurrencyService.class).currencyCodeForProtocolId(protocolCurrencyId);
        } catch (IllegalStateException exception) {
            return "currency_" + protocolCurrencyId;
        }
    }

    private void applyFallbackCurrencyDelta(final String currencyCode, final int delta) {
        final String resolvedCurrencyCode = this.resolveCurrencyCode(currencyCode);

        if (CREDITS_CURRENCY_CODE.equals(resolvedCurrencyCode)) {
            this.credits += delta;
            return;
        }

        this.currencyBalances.merge(resolvedCurrencyCode, delta, Integer::sum);
    }

    private int cachedBalanceFor(final String currencyCode) {
        final String resolvedCurrencyCode = this.resolveCurrencyCode(currencyCode);
        if (CREDITS_CURRENCY_CODE.equals(resolvedCurrencyCode)) {
            return this.credits;
        }

        return this.currencyBalances.getOrDefault(resolvedCurrencyCode, 0);
    }

    private boolean adjustInventory(final String currencyCode, final CurrencyOperation operation, final int amount) {
        try {
            final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
            final CurrencySource source = new CurrencySource(
                    "system",
                    "",
                    "legacy_player_data",
                    Integer.toString(this.id),
                    "Legacy PlayerData currency mutation");
            final CurrencyMovementResult result = switch (operation) {
                case ADD -> currencyService.add(this.id, currencyCode, amount, source);
                case REMOVE -> currencyService.remove(this.id, currencyCode, amount, source);
                case SET -> currencyService.set(this.id, currencyCode, amount, source);
            };

            this.applyCurrencyBalance(currencyCode, result.getNewBalance());
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private String resolveCurrencyCode(final String currencyCode) {
        try {
            return CometBootstrap.resolve(ICurrencyService.class).resolveCurrencyCode(currencyCode);
        } catch (RuntimeException exception) {
            return currencyCode;
        }
    }

    /**
     * Executes flush for this player contract.
     */
    public void flush() {
        if (getPlayer() != null) {
            getPlayer().flush();
        }
    }
}
