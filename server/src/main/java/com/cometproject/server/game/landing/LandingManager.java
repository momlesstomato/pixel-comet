package com.cometproject.server.game.landing;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.landing.calendar.CalendarDay;
import com.cometproject.server.game.landing.types.PromoArticle;
import com.cometproject.server.storage.queries.landing.LandingDao;
import com.cometproject.server.tasks.CometThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Manages landing runtime state for the Comet subsystem.
 */
public class LandingManager implements Startable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LandingManager.class.getName());
    private Map<Integer, PromoArticle> articles;
    private Map<Integer, CalendarDay> calendarDays;

    private Map<PlayerAvatar, Integer> hallOfFame;

    /**
     * Creates a landing manager instance for the Comet subsystem.
     */
    public LandingManager() {
    }

    /**
     * Returns the instance for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public static LandingManager getInstance() {
        return CometBootstrap.resolve(LandingManager.class);
    }

    /**
     * Starts this Comet component.
     */
    @Override
    public void start() {
        this.loadArticles();
        this.loadHallOfFame();
        this.loadCalendar();

        LOGGER.info("LandingManager initialized");
    }

    /**
     * Loads articles for this Comet contract.
     */
    public void loadArticles() {
        if (this.articles != null) {
            this.articles.clear();
        }

        this.articles = LandingDao.getArticles();
    }

    /**
     * Loads calendar for this Comet contract.
     */
    public void loadCalendar() {
        if (this.calendarDays != null) {
            this.calendarDays.clear();
        }

        this.calendarDays = LandingDao.getCalendarDays();
    }

    private void loadHallOfFame() {
        if (this.hallOfFame != null) {
            this.hallOfFame.clear();
        }

        if (CometSettings.hallOfFameEnabled) {
            this.hallOfFame = LandingDao.getHallOfFame(CometSettings.hallOfFameCurrency, 10);

            // Queue it to be refreshed again in X minutes
            CometThreadManager.getInstance().executeSchedule(
                    this::loadHallOfFame,
                    CometSettings.hallOfFameRefreshMinutes,
                    TimeUnit.MINUTES);
        }
    }

    /**
     * Returns the articles for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, PromoArticle> getArticles() {
        return articles;
    }

    /**
     * Returns the hall of fame for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<PlayerAvatar, Integer> getHallOfFame() {
        return this.hallOfFame;
    }

    /**
     * Returns the unlock days for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getUnlockDays(){
        int time = (int) ((System.currentTimeMillis() / 1000L)) - CometSettings.calendarTimestamp; // CALENDAR TIMESTAMP
        return (((time / 60) / 60) / 24);
    }

    /**
     * Returns the total days for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTotalDays(){
        return this.calendarDays.size();
    }

    /**
     * Returns the gift by day for this Comet contract.
     *
     * @param i I supplied by the caller.
     * @return Value exposed by the contract.
     */
    public String getGiftByDay(int i){
        if(this.calendarDays.containsKey(i))
            return this.calendarDays.get(i).getGift();

        return "";
    }

    /**
     * Returns the campaign day for this Comet contract.
     *
     * @param i I supplied by the caller.
     * @return Value exposed by the contract.
     */
    public CalendarDay getCampaignDay(int i){
        if(calendarDays.containsKey(i))
            return calendarDays.get(i);

        return null;
    }
}
