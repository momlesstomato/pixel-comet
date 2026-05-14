package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes link command behavior for the Comet subsystem.
 */
public class LinkCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) return;

        String link = params[0];
        boolean needsCorrection = link.startsWith("www.");

        String variableLink = "<u><b><font color='#fd6305'><a href='" + (needsCorrection ? "https://" : "") + link + "' target='_blank'>" + link + "</a></font></b></u>";


        Room room = client.getPlayer().getEntity().getRoom();
        room.getEntities().broadcastMessage(new TalkMessageComposer(client.getPlayer().getEntity().getId(), variableLink, ChatEmotion.NONE, 0));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "link_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.link", "%link%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.link.description");
    }
}
