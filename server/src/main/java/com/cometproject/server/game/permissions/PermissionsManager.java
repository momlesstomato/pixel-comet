package com.cometproject.server.game.permissions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.permissions.types.Perk;
import com.cometproject.server.game.permissions.types.Rank;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.storage.queries.permissions.PermissionsDao;


/**
 * Manages permissions runtime state for the permission subsystem.
 */
public class PermissionsManager implements Startable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionsManager.class.getName());
    private Map<Integer, Perk> perks;
    private Map<Integer, Rank> permissions;

    /**
     * Creates a permissions manager instance for the permission subsystem.
     */
    public PermissionsManager() {

    }

    /**
     * Returns the instance for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public static PermissionsManager getInstance() {
        return CometBootstrap.resolve(PermissionsManager.class);
    }

    /**
     * Starts this permission component.
     */
    @Override
    public void start() {
        this.perks = new ConcurrentHashMap<>();
        this.permissions = new ConcurrentHashMap<>();

        this.loadPerks();
        this.loadPermissions();

        LOGGER.info("PermissionsManager initialized");
    }

    /**
     * Loads perks for this permission contract.
     */
    public void loadPerks() {
        try {
            if (this.getPerks().size() != 0) {
                this.getPerks().clear();
            }

            this.perks = PermissionsDao.getPerks();

        } catch (Exception e) {
            LOGGER.error("Error while loading perk permissions", e);
            return;
        }

        LOGGER.info("Loaded " + this.getPerks().size() + " perks");
    }

    /**
     * Loads permissions for this permission contract.
     */
    public void loadPermissions() {
        try {
            if (this.getPermissions().size() != 0) {
                this.getPermissions().clear();
            }

            this.permissions = PermissionsDao.getPermissions();
        } catch (Exception e) {
            LOGGER.error("Error while loading rank permissions", e);
            return;
        }

        LOGGER.info("Loaded " + this.getPermissions().size() + " ranks");
    }


    /**
     * Returns the rank for this permission contract.
     *
     * @param playerRankId Player rank id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Rank getRank(final int playerRankId) {
        final Rank rank = this.permissions.get(playerRankId);

        if (rank == null) {
            LOGGER.warn("Failed to find rank by rank ID: " + playerRankId + ", are you sure it exists?");
            return this.permissions.get(1);
        }

        return rank;
    }

    /**
     * Returns the permissions for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, Rank> getPermissions() {
        return this.permissions;
    }

    /**
     * Returns the perks for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, Perk> getPerks() {
        return perks;
    }

    /**
     * Executes rank exists for this permission contract.
     *
     * @param rankId Rank id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean rankExists(int rankId) {
        return this.permissions.containsKey(rankId);
    }

    /**
     * Indicates whether this permission contract has permission.
     *
     * @param player Player participating in the operation.
     * @param permission Permission supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPermission(Player player, String permission) {
        return this.hasPermission(player, permission, false);
    }

    /**
     * Indicates whether this permission contract has permission.
     *
     * @param player Player participating in the operation.
     * @param permission Permission supplied by the caller.
     * @param withRoomRights With room rights supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPermission(Player player, String permission, boolean withRoomRights) {
        return this.hasPermission(player.getPermissions().getRank(), permission, withRoomRights);
    }

    /**
     * Indicates whether this permission contract has permission.
     *
     * @param rank Rank supplied by the caller.
     * @param permission Permission supplied by the caller.
     * @param withRoomRights With room rights supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPermission(Rank rank, String permission, boolean withRoomRights) {
        return rank.hasPermission(permission, withRoomRights);
    }
}
