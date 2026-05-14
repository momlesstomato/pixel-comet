package com.cometproject.gamecenter.fastfood;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cometproject.gamecenter.fastfood.net.FastFoodGameSession;
import com.cometproject.gamecenter.fastfood.net.FastFoodNetSession;
import com.cometproject.gamecenter.fastfood.net.composers.DropFoodMessageComposer;
import com.cometproject.gamecenter.fastfood.net.composers.PlayerJoinGameMessageComposer;
import com.cometproject.gamecenter.fastfood.objects.FoodPlate;
import com.cometproject.gamecenter.fastfood.players.MockPlayerBuilder;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Sets;

/**
 * Describes fast food game behavior for the Fast Food game subsystem.
 */
public class FastFoodGame {
    private Set<FastFoodNetSession> players;

    private boolean started = false;
    private Future scheduledFuture;

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Creates a fast food game instance for the Fast Food game subsystem.
     */
    public FastFoodGame() {
        this.players = Sets.newConcurrentHashSet();

        this.players.add(new MockPlayerBuilder()
                .withPlayerId(1337)
                .withFigure("sh-3338-93.hd-180-1.ha-3362-0.ca-1813-63.lg-3337-92.ch-3334-93-92")
                .withUsername("JaidenGay")
                .create());

        this.players.add(new MockPlayerBuilder()
                .withPlayerId(1338)
                .withFigure("lg-270-64.wa-2001-1408.sh-305-62.hd-3095-10.ca-3084-75-75.cc-3075-64.ch-3022-1408-1408.hr-155-61")
                .withUsername("KebProducsions")
                .create());
    }

    private void tick() {
        for (FastFoodNetSession netSession : this.players) {
            final FoodPlate foodPlate = netSession.getGameSession().getCurrentPlate();

            if (foodPlate != null && !foodPlate.isFinalized()) {
                foodPlate.tick(netSession.getGameSession(), this);
            }
        }
    }

    /**
     * Executes start game for this Fast Food game contract.
     *
     * @param executorService Executor service supplied by the caller.
     */
    public void startGame(ScheduledExecutorService executorService) {
        for (FastFoodNetSession netSession : this.getPlayers()) {
            if (netSession.getConnection() != null) {
                netSession.getConnection().send(new PlayerJoinGameMessageComposer(this,
                        netSession.getGameSession()));
            }
        }

        this.scheduledFuture = executorService.scheduleAtFixedRate(this::tick, 1000, 100,
                TimeUnit.MILLISECONDS);

        this.started = true;
    }

    /**
     * Executes stop game for this Fast Food game contract.
     */
    public void stopGame() {
        this.scheduledFuture.cancel(true);
    }

    /**
     * Executes launch for this Fast Food game contract.
     *
     * @param type Type supplied by the caller.
     * @param gameSession Game session supplied by the caller.
     * @param isCompleted Is completed supplied by the caller.
     */
    public void launch(int type, FastFoodGameSession gameSession, boolean isCompleted) {
        switch (type){
            case 0:
                if (gameSession.getCurrentPlate() != null && !gameSession.getCurrentPlate().isFinalized())
                    return;


                if (gameSession.getCurrentPlate() == null || gameSession.getCurrentPlate().isFinalized()) {
                    final int objectId = this.counter.getAndIncrement();
                    final FoodPlate foodPlate = new FoodPlate(objectId, gameSession.getPlayerId());

                    gameSession.setCurrentPlate(foodPlate);
                    this.broadcast(new DropFoodMessageComposer(objectId, foodPlate, false));
                    return;
                }
                break;
            case 1:
                if (gameSession.getCurrentPlate() != null && !gameSession.getCurrentPlate().isFinalized()) {
                    gameSession.getCurrentPlate().openParachute(this);
                    return;
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                if (gameSession.getCurrentPlate() != null && !gameSession.getCurrentPlate().isFinalized()) {
                    gameSession.getCurrentPlate().applyShield(this);
                    return;
                }
                break;
        }
    }

    /**
     * Executes broadcast for this Fast Food game contract.
     *
     * @param messageComposer Message composer supplied by the caller.
     */
    public void broadcast(MessageComposer messageComposer) {
        for (FastFoodNetSession netSession : this.players) {
            if (netSession.getConnection() != null) {
                netSession.getConnection().send(messageComposer);
            }
        }
    }

    /**
     * Executes broadcast for this Fast Food game contract.
     *
     * @param messageComposer Message composer supplied by the caller.
     * @param gameSession Game session supplied by the caller.
     */
    public void broadcast(MessageComposer messageComposer, FastFoodGameSession gameSession) {
        for (FastFoodNetSession netSession : this.players) {
            if (netSession.getConnection() != null && netSession.getGameSession() == gameSession) {
                netSession.getConnection().send(messageComposer);
            }
        }
    }

    /**
     * Indicates whether this Fast Food game contract has started.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasStarted() {
        return this.started;
    }

    /**
     * Indicates whether this Fast Food game contract can start.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canStart() {
        return this.players.size() == 3;
    }

    /**
     * Returns the players for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<FastFoodNetSession> getPlayers() {
        return this.players;
    }

    /**
     * Returns the counter for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public AtomicInteger getCounter() {
        return counter;
    }
}
