package com.cometproject.server.game.commands.user.room;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.GameContext;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomDataMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes set max command behavior for the Comet subsystem.
 */
public class SetMaxCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.get("command.setmax.invalid"), client);
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();
        final boolean hasRights = room.getRights().hasRights(client.getPlayer().getId());
        final boolean isStaff = client.getPlayer().getPermissions().getRank().roomFullControl();

        if (hasRights || isStaff) {
            try {
                final int maxPlayers = Integer.parseInt(params[0]);

                if ((maxPlayers > CometSettings.roomMaxPlayers && !isStaff) || maxPlayers < 1) {
                    sendNotif(Locale.get("command.setmax.toomany").replace("%i", CometSettings.roomMaxPlayers + ""), client);
                    return;
                }

                room.getData().setMaxUsers(maxPlayers);
                GameContext.getCurrent().getRoomService().saveRoomData(room.getData());

                sendNotif(Locale.get("command.setmax.done").replace("%i", maxPlayers + ""), client);
                room.getEntities().broadcastMessage(new RoomDataMessageComposer(room));
            } catch (Exception e) {
                sendNotif(Locale.get("command.setmax.invalid"), client);
            }
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "setmax_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.amount", "%amount%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.setmax.description");
    }
}
