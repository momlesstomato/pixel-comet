package com.cometproject.server.game.commands.user;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes lay command behavior for the Comet subsystem.
 */
public class LayCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        PlayerEntity playerEntity = client.getPlayer().getEntity();
        if (playerEntity.hasStatus(RoomEntityStatus.LAY)) {
            playerEntity.removeStatus(RoomEntityStatus.LAY);
            playerEntity.markNeedsUpdate();
            isExecuted(client);
        } else if (playerEntity.hasStatus(RoomEntityStatus.SIT)) {
            playerEntity.removeStatus(RoomEntityStatus.SIT);
            playerEntity.addStatus(RoomEntityStatus.LAY, "0.5");
            playerEntity.markNeedsUpdate();
            isExecuted(client);
        } else {
            playerEntity.addStatus(RoomEntityStatus.LAY, "0.5");
            playerEntity.markNeedsUpdate();
            isExecuted(client);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "lay_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.lay.description");
    }

    /**
     * Indicates whether this Comet contract can disable.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean canDisable() {
        return true;
    }
}
