package com.cometproject.server.game.players.components;

import com.cometproject.api.game.players.data.components.PlayerPermissions;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.permissions.PermissionsManager;
import com.cometproject.server.game.permissions.types.Rank;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.storage.api.data.permissions.PermissionContext;
import com.cometproject.storage.api.services.IPermissionService;

import java.util.Locale;


/**
 * Owns permission behavior inside the player subsystem.
 */
public class PermissionComponent implements PlayerPermissions {
    private Player player;

    /**
     * Creates a permission component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public PermissionComponent(Player player) {
        this.player = player;
    }

    /**
     * Returns the rank for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Rank getRank() {
        return PermissionsManager.getInstance().getRank(this.player.getData().getRank());
    }

    /**
     * Indicates whether this player contract has command.
     *
     * @param key Key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasCommand(String key) {
        final String permissionNode = this.commandPermissionNode(key);

        if (!permissionNode.isEmpty() && this.permissionService().hasPermission(
                this.player.getData().getId(),
                permissionNode,
                this.roomContext())) {
            return true;
        }

        final boolean hasRoomRights = this.player.getEntity() != null
                && this.player.getEntity().getRoom() != null
                && this.player.getEntity().hasRights();

        return PermissionsManager.getInstance().hasPermission(this.player, key)
                || this.player.getPermissions().getRank().hasPermission(key, hasRoomRights);
    }

    /**
     * Returns the effective legacy rank id for packets and legacy comparisons.
     *
     * @return the effective legacy rank id.
     */
    public int getLegacyRankId() {
        return this.permissionService().legacyRankId(this.player.getData().getId(), this.player.getData().getRank());
    }

    /**
     * Returns the highest effective permission group priority.
     *
     * @return the highest effective priority.
     */
    public int getHighestPriority() {
        return this.permissionService().highestPriority(this.player.getData().getId(), this.player.getData().getRank());
    }

    /**
     * Returns the player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Releases resources owned by this player component.
     */
    @Override
    public void dispose() {

    }

    private PermissionContext roomContext() {
        if (this.player.getEntity() == null || this.player.getEntity().getRoom() == null) {
            return PermissionContext.global();
        }

        return new PermissionContext(
                this.player.getEntity().getRoom().getId(),
                this.player.getEntity().getRoom().getData().getOwnerId() == this.player.getId(),
                this.player.getEntity().hasRights(),
                "command",
                java.util.Map.of());
    }

    private IPermissionService permissionService() {
        return CometBootstrap.resolve(IPermissionService.class);
    }

    private String commandPermissionNode(final String key) {
        if (key == null || key.isBlank()) {
            return "";
        }

        String commandName = key.trim().toLowerCase(Locale.ROOT);
        if (commandName.endsWith("_command")) {
            commandName = commandName.substring(0, commandName.length() - "_command".length());
        }

        return "commands." + commandName;
    }
}
