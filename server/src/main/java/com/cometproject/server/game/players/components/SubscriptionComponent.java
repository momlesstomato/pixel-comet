package com.cometproject.server.game.players.components;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.components.SubsComponent;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.network.messages.outgoing.user.club.ClubStatusMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.club.SubscriptionCenterInfoMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.permissions.FuserightsMessageComposer;
import com.cometproject.server.storage.queries.player.SubscriptionDao;


/**
 * Owns subscription behavior inside the player subsystem.
 */
public class SubscriptionComponent extends PlayerComponent implements SubsComponent {
    private boolean hasSub;
    private int expire;
    private int start;
    private int presents;

    /**
     * Creates a subscription component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public SubscriptionComponent(IPlayer player) {
        super(player);
        this.load();
    }

    /**
     * Executes load for this player contract.
     */
    public void load() {
        this.expire = this.getExpireFromDao();
        this.start = this.getStartFromDao();
        this.presents = this.getPresentsFromDao();
        this.hasSub = this.isValid();
    }

    /**
     * Executes add for this player contract.
     *
     * @param days Days supplied by the caller.
     */
    @Override
    public void add(int days) {
        if (this.hasSub) {
            SubscriptionDao.renewSubscription(this.player.getId(), this.getExpire() + 86400 * days);
        } else {
            SubscriptionDao.addSubscription(this.player.getId(), (int)Comet.getTime() + 86400 * days);
        }
        this.load();
    }

    /**
     * Executes delete for this player contract.
     */
    @Override
    public void delete() {
        this.hasSub = false;
    }

    /**
     * Releases resources owned by this player component.
     */
    @Override
    public void dispose() {

    }

    /**
     * Executes deliver for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public IMessageComposer deliver(){
        return new SubscriptionCenterInfoMessageComposer(this);
    }

    /**
     * Executes confirm for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public IMessageComposer confirm(){
        return new ClubStatusMessageComposer(this);
    }

    /**
     * Executes update for this player contract.
     *
     * @return Result produced by the mutation.
     */
    @Override
    public IMessageComposer update(){
        return new FuserightsMessageComposer(this.hasSub, this.getPlayer().getPermissions().getLegacyRankId());
    }

    /**
     * Indicates whether valid applies to this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isValid() {
        return (long)this.getExpire() >= Comet.getTime();
    }

    /**
     * Executes exists for this player contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean exists() {
        return this.hasSub;
    }

    /**
     * Returns the expire for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getExpire() {
        return this.expire;
    }

    /**
     * Returns the start for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Returns the presents for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPresents() {
        return this.presents;
    }

    /**
     * Executes decrement presents for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void decrementPresents(int playerId) {
        --this.presents;
        SubscriptionDao.decrementPresents(playerId, this.presents);
    }

    /**
     * Returns the time left for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTimeLeft() {
        return this.getExpire() - (int)Comet.getTime();
    }

    /**
     * Returns the days left for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDaysLeft() {
        return this.getTimeLeft() / 86400;
    }

    /**
     * Returns the years left for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getYearsLeft() {
        return (int)Math.floor(this.getDaysLeft() / 365);
    }

    /**
     * Returns the minutes left for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMinutesLeft() {
        return (int)Math.ceil((double)this.getTimeLeft() / 60.0);
    }

    /**
     * Returns the expire from dao for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getExpireFromDao() {
        return SubscriptionDao.getExpireTime(this.player.getId());
    }

    /**
     * Returns the start from dao for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getStartFromDao() {
        return SubscriptionDao.getStartTime(this.player.getId());
    }

    /**
     * Returns the presents from dao for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPresentsFromDao() {
        return SubscriptionDao.getPresents(this.player.getId());
    }

    /**
     * Returns the player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IPlayer getPlayer() {
        return this.player;
    }
}
