package com.cometproject.server.game.commands.user.room;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes entity sorting command behavior for the Comet subsystem.
 */
public class EntitySortingCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        final Room room = client.getPlayer().getEntity().getRoom();

        if(room == null || room.getData() == null)
            return;

        if (!client.getPlayer().getPermissions().getRank().roomFullControl() && !client.getPlayer().getEntity().hasRights()) {
            return;
        }

        String msg;

        if (client.getPlayer().getEntity().getRoom().getData().hasSorting()) {
            // show wireds
            room.getData().setHasEntitySort(false);
            msg = Locale.getOrDefault("command.entity.shown", "Has desactivado la V.");
        } else {
            // hide wireds
            room.getData().setHasEntitySort(true);
            msg = Locale.getOrDefault("command.entity.hidden", "Has activado la V.");
        }

        sendNotif(msg, client);
        GameContext.getCurrent().getRoomService().saveRoomData(room.getData());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "sorting_command";
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
        return Locale.get("command.sorting.description");
    }
}
