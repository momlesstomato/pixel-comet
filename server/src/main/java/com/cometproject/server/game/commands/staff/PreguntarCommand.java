package com.cometproject.server.game.commands.staff;

import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes preguntar command behavior for the Comet subsystem.
 */
public class PreguntarCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1) {
            sendNotif("¿Qué quieres preguntar?", client);
            return;
        }

        for (ISession session : NetworkManager.getInstance().getSessions().getSessions().values()){
            try{

                session.send(new TalkMessageComposer(session.getPlayer().getEntity().getId(), this.merge(params, 1), ChatEmotion.NONE, 2));
            }
            catch (Exception ignored) { }
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "preguntar_command";
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
        return Locale.get("command.preguntar.description");
    }
}
