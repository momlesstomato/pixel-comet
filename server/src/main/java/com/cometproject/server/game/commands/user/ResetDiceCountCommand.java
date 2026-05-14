package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes reset dice count command behavior for the Comet subsystem.
 */
public class ResetDiceCountCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        try {
            if(client == null || client.getPlayer() == null || client.getPlayer().getEntity() == null)
                return;

            String messInfo = "Acabo de reiniciar mi tirada en dados, llevaba " + client.getPlayer().getEntity().getDiceCount() + ".";

            client.getPlayer().getEntity().resetDiceCount();
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new TalkMessageComposer(client.getPlayer().getEntity().getId(), messInfo, ChatEmotion.NONE, 26));
        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.resetdicecount.invalid", "Oops, algo ha salido mal."), client);
        }
    }


    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "resetdicecount_command";
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
        return Locale.get("command.resetdicecount.description");
    }
}
