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

    @Override
    public Object tempData() {
        return this.tempData;
    }

    @Override
    public void tempData(Object obj) {
        this.tempData = obj;
    }

    public void save() {
        this.saveNow();
    }

    public void saveNow() {
        PlayerDao.updatePlayerDataWithoutCurrencies(id, username, motto, figure, credits, gender, favouriteGroup, questId, achievementPoints, nameColour, tag, job);
    }

    public void decreaseCredits(int amount) {
        if (this.adjustInventory(CREDITS_CURRENCY_CODE, CurrencyOperation.REMOVE, amount)) {
            return;
        }

        this.credits -= amount;

        flush();
    }

    public void decreaseCurrency(int currency, int amount){
        if (this.adjustInventory(this.currencyCodeForLegacyId(currency), CurrencyOperation.REMOVE, amount)) {
            return;
        }

        this.applyFallbackCurrencyDelta(this.currencyCodeForLegacyId(currency), -amount);

        flush();
    }

    public void increaseCredits(int amount) {
        if (this.adjustInventory(CREDITS_CURRENCY_CODE, CurrencyOperation.ADD, amount)) {
            return;
        }

        this.credits += amount;

        flush();
    }

    public void addViewPoint(){
        this.viewPoints++;

        if (this.rank == 2) this.viewPoints++;

        PlayerDao.UpdateViewPoints(this.viewPoints, this.id);
    }

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

    public void openBattlePass(){
        this.getPlayer().getSession().send(new MassEventMessageComposer("battlepass/open"));
    }

    public void updateBattlePass(){
        this.getPlayer().getSession().send(new MassEventMessageComposer("battlepass/update"));
    }

    public void createBattlePass(){
        this.battlePass = new PlayerBattlePassInfo(1, 1);
        this.battlePass.playerId = this.id;
        PlayerDao.PlayerBattlePassCreate(this.id);
    }

    public int getId() {
        return this.id;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;

        flush();
    }

    public String getLegacyMotto() {
        return legacyMotto;
    }

    public int getAchievementPoints() {
        return this.achievementPoints;
    }

    public int getKarma() {
        return karma;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public String getMotto() {
        return this.motto;
    }



    public void setMotto(String motto) {
        this.motto = motto;

        flush();
    }

    public void setLegacyMotto(String legacyMotto) {
        this.legacyMotto = legacyMotto;
    }

    public String getFigure() {
        return this.figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;

        flush();
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;

        flush();
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;

        flush();
    }

    public int getLastVisit() {
        return this.lastVisit;
    }

    public void setLastVisit(long time) {
        this.lastVisit = (int) time;
    }

    public String getRegDate() {
        return this.regDate;
    }

    public boolean isVip() {
        return this.vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;

        flush();
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
        this.save();
    }

    public boolean getAsk() {
        return this.ask;
    }

    public void setAsk(boolean ask) {
        this.ask = ask;
    }
    public int getRegTimestamp() {
        return regTimestamp;
    }

    public void setRegTimestamp(int regTimestamp) {
        this.regTimestamp = regTimestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFavouriteGroup() {
        return favouriteGroup;
    }

    public void setFavouriteGroup(int favouriteGroup) {
        this.favouriteGroup = favouriteGroup;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTemporaryFigure() {
        return temporaryFigure;
    }

    public void setTemporaryFigure(String temporaryFigure) {
        this.temporaryFigure = temporaryFigure;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getTimeMuted() {
        return this.timeMuted;
    }

    public void setTimeMuted(int Time) {
        this.timeMuted = Time;

        flush();
    }

    public boolean getChangingName() {
        return this.changingName;
    }

    public void setChangingName(boolean changingName) {
        this.changingName = changingName;

        flush();
    }

    public boolean getFlaggingUser() {
        return this.flaggingUser;
    }

    public void setFlaggingUser(boolean flaggingUser) {
        this.flaggingUser = flaggingUser;

        flush();
    }

    public String getNameColour() {
        return this.nameColour;
    }

    @Override
    public void setNameColour(String nameColour) {
        this.nameColour = nameColour;

        flush();
    }

    public String getTag() {
        return this.tag;
    }

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

    public void flush() {
        if (getPlayer() != null) {
            getPlayer().flush();
        }
    }
}
