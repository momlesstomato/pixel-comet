package com.cometproject.server.game.moderation;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.moderation.types.Ban;
import com.cometproject.server.game.moderation.types.BanType;
import com.cometproject.server.storage.queries.moderation.BanDao;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Manages ban runtime state for the moderation subsystem.
 */
public class BanManager implements Startable {
    Logger LOGGER = LoggerFactory.getLogger(BanManager.class.getName());
    private Map<String, Ban> bans;
    private Set<Integer> mutedPlayers;

    /**
     * Creates a ban manager instance for the moderation subsystem.
     */
    public BanManager() {

    }

    /**
     * Returns the instance for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public static BanManager getInstance() {
        return CometBootstrap.resolve(BanManager.class);
    }

    /**
     * Starts this moderation component.
     */
    @Override
    public void start() {
        this.mutedPlayers = new ConcurrentHashSet<>();

        loadBans();
        LOGGER.info("BanManager initialized");
    }

    /**
     * Loads bans for this moderation contract.
     */
    public void loadBans() {
        if (this.bans != null)
            this.bans.clear();

        try {
            this.bans = BanDao.getActiveBans();
            LOGGER.info("Loaded " + this.bans.size() + " bans");
        } catch (Exception e) {
            LOGGER.error("Error while loading bans", e);
        }
    }

    /**
     * Processes bans for this moderation contract.
     */
    public void processBans() {
        List<Ban> bansToRemove = Lists.newArrayList();

        for (Ban ban : this.bans.values()) {
            if (ban.getExpire() != 0 && Comet.getTime() >= ban.getExpire()) {
                bansToRemove.add(ban);
            }
        }

        if (bansToRemove.size() != 0) {
            for (Ban ban : bansToRemove) {
                this.bans.remove(ban.getData());
            }
        }

        bansToRemove.clear();
    }

    /**
     * Executes ban player for this moderation contract.
     *
     * @param type Type supplied by the caller.
     * @param data Data supplied by the caller.
     * @param length Length supplied by the caller.
     * @param expire Expire supplied by the caller.
     * @param reason Reason supplied by the caller.
     * @param bannerId Banner id supplied by the caller.
     */
    public void banPlayer(BanType type, String data, int length, long expire, String reason, int bannerId) {
        int banId = BanDao.createBan(type, length, expire, data, bannerId, reason);
        this.add(new Ban(banId, data, length == 0 ? length : expire, type, reason));
    }

    private void add(Ban ban) {
        this.bans.put(ban.getData(), ban);
    }

    /**
     * Indicates whether this moderation contract has ban.
     *
     * @param data Data supplied by the caller.
     * @param type Type supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasBan(String data, BanType type) {
        if (this.bans.containsKey(data)) {
            Ban ban = this.bans.get(data);

            if (ban != null && ban.getType() == type) {
                return ban.getExpire() == 0 || Comet.getTime() < ban.getExpire();
            }
        }

        return false;
    }

    /**
     * Executes un ban for this moderation contract.
     *
     * @param data Data supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean unBan(String data) {
        if(!data.equals("0")) {
            if (this.bans.containsKey(data)) {
                this.bans.remove(data);
                removeBan(data);

                start();
                return true;
            } else return false;
        } else return false;
    }

    private void removeBan(String data) {
        BanDao.deleteBan(data);
    }

    /**
     * Executes get for this moderation contract.
     *
     * @param data Data supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Ban get(String data) {
        return this.bans.get(data);
    }

    /**
     * Indicates whether muted applies to this moderation contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isMuted(int playerId) {
        return this.mutedPlayers.contains(playerId);
    }

    /**
     * Executes mute for this moderation contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void mute(int playerId) {
        this.mutedPlayers.add(playerId);
    }

    /**
     * Executes unmute for this moderation contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void unmute(int playerId) {
        this.mutedPlayers.remove(playerId);
    }
}
