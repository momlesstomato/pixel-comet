package com.cometproject.server.game.commands.vip;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes toggle diagonal command behavior for the Comet subsystem.
 */
public class ToggleDiagonalCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer().getEntity().getRoom().getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            sendNotif(Locale.getOrDefault("command.togglediagonal.nopermission", "You don't have permission to use this command!"), client);
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        if (room.hasAttribute("disableDiagonal")) {
            sendNotif(Locale.getOrDefault("command.togglediagonal.enabled", "Diagonal walking has been enabled!"), client);
            room.removeAttribute("disableDiagonal");
        } else {
            sendNotif(Locale.getOrDefault("command.togglediagonal.disabled", "Diagonal walking has been disabled!"), client);
            room.setAttribute("disableDiagonal", true);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "togglediagonal_command";
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
        return Locale.get("command.togglediagonal.description");
    }
}
