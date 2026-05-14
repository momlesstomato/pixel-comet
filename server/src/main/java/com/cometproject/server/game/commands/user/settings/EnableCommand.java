package com.cometproject.server.game.commands.user.settings;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes enable command behavior for the Comet subsystem.
 */
public class EnableCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        Room room = client.getPlayer().getEntity().getRoom();

        if ((room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            sendNotif(Locale.getOrDefault("command.need.rights", "You have no rights to use this command in this room."), client);
            return;
        }

        if (params.length != 1) {
            return;
        }

        if (client.getPlayer().getEntity().getMountedEntity() != null) {
            return;
        }

        String disabledCommand = params[0];

        if (room.getData().getDisabledCommands().contains(disabledCommand)) {
            room.getData().getDisabledCommands().remove(disabledCommand);
            GameContext.getCurrent().getRoomService().saveRoomData(room.getData());

            sendNotif(Locale.get("command.enablecommand.success"), client);
        } else {
            client.send(new AdvancedAlertMessageComposer(Locale.get("command.enablecommand.error")));
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "enablecommand_command";
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
        return Locale.get("command.enablecommand.description");
    }
}
