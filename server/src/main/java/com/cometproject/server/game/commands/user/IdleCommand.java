package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes idle command behavior for the Comet subsystem.
 */
public class IdleCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 0) return;

        PlayerEntity u = client.getPlayer().getEntity();

        if(client.getPlayer() == null || u == null)
            return;

        if(u.isForcedIdle()){
            u.unIdle();
            u.getPlayer().getData().setMotto(client.getPlayer().getData().getLegacyMotto());
            u.getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(client.getPlayer().getEntity()));
            client.send(new UpdateInfoMessageComposer(-1, client.getPlayer().getEntity()));
            u.applyEffect(new PlayerEffect(0));
            u.getPlayer().sendNotif("idle", "Ya no estás ausente.");
            return;
        }

        u.resetIdleTime();
        u.setIdleStatus(true);
        u.applyEffect(new PlayerEffect(903));

    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "idle_command";
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
        return Locale.get("command.idle.description");
    }
}
