package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.sessions.Session;

import static com.cometproject.api.game.rooms.entities.RoomEntityStatus.LAY;
import static com.cometproject.api.game.rooms.entities.RoomEntityStatus.SIT;

/**
 * Describes sit command behavior for the Comet subsystem.
 */
public class SitCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        PlayerEntity playerEntity = client.getPlayer().getEntity();

        if(playerEntity.hasStatus(SIT) && playerEntity.hasStatus(LAY)) {
            playerEntity.removeStatus(LAY);
            playerEntity.removeStatus(SIT);
        }

        if(playerEntity.hasStatus(SIT)) {
            playerEntity.removeStatus(SIT);
            playerEntity.markNeedsUpdate();
        } else {
            playerEntity.sit(0.5, playerEntity.getBodyRotation());
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "sit_command";
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
        return Locale.get("command.sit.description");
    }
}
