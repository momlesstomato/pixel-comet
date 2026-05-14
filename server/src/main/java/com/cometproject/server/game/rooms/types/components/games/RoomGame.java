package com.cometproject.server.game.rooms.types.components.games;

import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.GameComponent;
import com.cometproject.server.tasks.CometTask;
import com.cometproject.server.tasks.CometThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * Describes room game behavior for the room processing subsystem.
 */
public abstract class RoomGame implements CometTask {
    protected int timer;
    protected int gameLength;
    protected boolean active = false;
    protected boolean finished = false;
    protected Room room;
    private GameType type;
    private ScheduledFuture future;

    private Logger LOGGER;

    /**
     * Creates a room game instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     * @param gameType Game type supplied by the caller.
     */
    public RoomGame(Room room, GameType gameType) {
        this.type = gameType;
        this.LOGGER = LoggerFactory.getLogger("RoomGame [" + room.getData().getName() + "][" + room.getData().getId() + "][" + this.type + "]");
        this.room = room;
    }

    /**
     * Runs this room processing task.
     */
    @Override
    public void run() {
        try {
            if (timer == 0) {
                this.active = true;
                /*final List<WiredAddonBlob> blobs = room.getItems().getByClass(WiredAddonBlob.class);
                Collections.shuffle(blobs);

                for (WiredAddonBlob blob : blobs) {
                    blob.onGameStarted();
                }*/

                onGameStarts();
            }

            try {
                /*if (this.getGameComponent().getBlobCounter().get() < 2) {
                    if (RandomUtil.getRandomBool(0.1)) {
                        final List<WiredAddonBlob> blobs = room.getItems().getByClass(WiredAddonBlob.class);
                        Collections.shuffle(blobs);

                        for (WiredAddonBlob blob : blobs) {
                            blob.onGameStarted();
                        }
                    }
                }*/

                tick();
            } catch (Exception e) {
                LOGGER.error("Failed to process game tick", e);
            }

            if (timer >= gameLength) {
                onGameEnds();
                room.getGame().stop();
                this.stop();
            }

            timer++;
        } catch (Exception e) {
            LOGGER.error("Error during game process", e);
        }
    }

    /**
     * Stops this room processing component.
     */
    public void stop() {
        /*for (WiredAddonBlob blob : room.getItems().getByClass(WiredAddonBlob.class)) {
            blob.hideBlob();
        }*/

        if (this.active && this.future != null) {
            this.future.cancel(false);

            this.active = false;
            this.gameLength = 0;
            this.timer = 0;
        }
    }

    /**
     * Executes start timer for this room processing contract.
     *
     * @param amount Amount supplied by the caller.
     */
    public void startTimer(int amount) {
        if (this.active && this.future != null) {
            this.future.cancel(false);
        }

        this.future = CometThreadManager.getInstance().executePeriodic(this, 0, 1, TimeUnit.SECONDS);

        this.gameLength = amount;
        this.active = true;

        LOGGER.debug("Game active for " + amount + " seconds");
    }

    /**
     * Returns the game component for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    protected GameComponent getGameComponent() {
        return this.room.getGame();
    }

    /**
     * Executes tick for this room processing contract.
     */
    public abstract void tick();

    /**
     * Handles the game ends callback for this room processing contract.
     */
    public abstract void onGameEnds();

    /**
     * Handles the game starts callback for this room processing contract.
     */
    public abstract void onGameStarts();

    /**
     * Returns the type for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public GameType getType() {
        return this.type;
    }

    /**
     * Returns the log for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Logger getLog() {
        return this.LOGGER;
    }

    /**
     * Indicates whether active applies to this room processing contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isActive() {
        return active;
    }
}
