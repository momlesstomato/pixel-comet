package com.cometproject.server.game.commands.gimmicks;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.rooms.RoomData;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Describes smoke command behavior for the Comet subsystem.
 */
public class SmokeCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
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
            /**
             * Runs this Comet task.
             */
            @Override
            public void run() {
                String messagePlayer2 = "* Se siente en jamaica *";
                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), messagePlayer2, 6));
                client.getPlayer().getEntity().applyEffect(new PlayerEffect(537, 14));
            }
        }, 6000);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "smoke_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "%yes%";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.smoke.description");
    }
}
