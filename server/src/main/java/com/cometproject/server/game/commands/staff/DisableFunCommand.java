package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.rooms.RoomData;

/**
 * Describes disable fun command behavior for the Comet subsystem.
 */
public class DisableFunCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        RoomData data = (RoomData)client.getPlayer().getEntity().getRoom().getData();
        if(data.funCommands){
            data.funCommands = false;
            DisableFunCommand.sendNotif("Has desactivado los fun commands en esta sala.", client);
            return;
        }
        else{
            data.funCommands = true;
            DisableFunCommand.sendNotif("Has activado los fun commands en esta sala.", client);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "disablefun_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault(null, "");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.disablefun.description");
    }
}

