package com.cometproject.api.events.players;

import com.cometproject.api.events.Cancellable;
import com.cometproject.api.events.Event;
import com.cometproject.api.game.players.IPlayer;

/**
 * Event fired after a player object is created during login.
 */
public final class OnPlayerLoginEvent extends Event implements Cancellable {
    private final IPlayer player;
    private boolean cancelled;

    /**
     * Creates a player-login event.
     *
     * @param player the logging-in player.
     */
    public OnPlayerLoginEvent(final IPlayer player) {
        this.player = player;
    }

    /**
     * Returns the logging-in player.
     *
     * @return the player.
     */
    public IPlayer getPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
