package com.cometproject.server.game.commands.gimmicks;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.rooms.RoomData;

import java.util.Timer;
import java.util.TimerTask;

public class SmokeCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        RoomData data = (RoomData)client.getPlayer().getEntity().getRoom().getData();
        if(!data.funCommands){
            SmokeCommand.sendNotif("Los FunCommands están desactivados en esta sala.", client);
            return;
        }
        String messagePlayer = "* Se enciende un blunt para entrar en onda. *";
        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), messagePlayer, 11));
        client.getPlayer().getEntity().applyEffect(new PlayerEffect(751, 14));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String messagePlayer2 = "* Se siente en jamaica *";
                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), messagePlayer2, 6));
                client.getPlayer().getEntity().applyEffect(new PlayerEffect(537, 14));
            }
        }, 6000);
    }

    @Override
    public String getPermission() {
        return "smoke_command";
    }

    @Override
    public String getParameter() {
        return "%yes%";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.smoke.description");
    }
}
