package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.rooms.RoomData;

public class DisableFunCommand extends ChatCommand {
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

    @Override
    public String getPermission() {
        return "disablefun_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault(null, "");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.disablefun.description");
    }
}

