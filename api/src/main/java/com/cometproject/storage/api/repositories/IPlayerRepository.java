package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.game.players.data.PlayerAvatar;

import java.util.function.Consumer;

/**
 * Provides persistent storage operations for player accounts.
 *
 * <p>Implementations must be thread-safe because player authentication requests are processed
 * concurrently by the login worker pool.
 */
public interface IPlayerRepository {
    /**
     * Loads the complete player aggregate needed by the login pipeline.
     *
     * @param playerId the database identifier resolved from the SSO ticket.
     * @param consumer the callback that receives the loaded player; it is not called when no
     *                 player exists.
     */
    void findById(int playerId, Consumer<IPlayer> consumer);

    /**
     * Loads mutable account data without session-bound components.
     *
     * @param playerId the database identifier of the player.
     * @param consumer the callback that receives the loaded player data; it is not called when no
     *                 player exists.
     */
    void findDataById(int playerId, Consumer<IPlayerData> consumer);

    /**
     * Loads the lightweight avatar view used by profile and group summaries.
     *
     * @param playerId the database identifier of the player.
     * @param mode     the requested avatar projection, following {@link PlayerAvatar} constants.
     * @param consumer the callback that receives the avatar; it is not called when no player
     *                 exists.
     */
    void findAvatarById(int playerId, byte mode, Consumer<PlayerAvatar> consumer);

    /**
     * Persists the mutable player account fields that are flushed during normal gameplay.
     *
     * @param data the player data snapshot to persist.
     */
    void save(IPlayerData data);

    /**
     * Updates a player's last-online timestamp and optional last known IP address.
     *
     * @param playerId  the database identifier of the player.
     * @param ipAddress the last known IP address, or an empty value when unavailable.
     */
    void updateLastOnline(int playerId, String ipAddress);

    /**
     * Updates a player's online flag without changing other account fields.
     *
     * @param playerId the database identifier of the player.
     * @param online   whether the player should be marked online.
     */
    void updateOnlineStatus(int playerId, boolean online);

    /**
     * Clears stale online flags left behind by an unclean server shutdown.
     */
    void resetOnlineStatus();
}
