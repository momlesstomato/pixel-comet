package com.cometproject.server.game.players.components;

import com.cometproject.api.game.players.data.components.PlayerPermissions;
import com.cometproject.server.game.permissions.PermissionsManager;
import com.cometproject.server.game.permissions.types.OverrideCommandPermission;
import com.cometproject.server.game.permissions.types.Rank;
import com.cometproject.server.game.players.types.Player;


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

        if (PermissionsManager.getInstance().getOverrideCommands().containsKey(key)) {
            OverrideCommandPermission permission = PermissionsManager.getInstance().getOverrideCommands().get(key);

            if(permission == null)
                return false;

            if (permission.getPlayerId() == this.getPlayer().getData().getId() && permission.isEnabled()) {
                return true;
            }
        }

        return PermissionsManager.getInstance().hasPermission(this.player, key) || this.player.getPermissions().getRank().hasPermission(key, this.player.getEntity().getRoom() != null && (this.player.getEntity().hasRights()));
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
}