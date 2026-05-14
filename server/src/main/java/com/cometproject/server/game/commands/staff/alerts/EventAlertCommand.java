package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.messenger.InstantChatMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketSessionManager;
import com.cometproject.server.network.websockets.packets.outgoing.alerts.EventAlertWebPacket;

/**
 * Describes event alert command behavior for the Comet subsystem.
 */
public class EventAlertCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {
            return;
        }

        int roomId = client.getPlayer().getEntity().getRoom().getId();

        String logDesc;

            CometSettings.setCurrentEventRoom(roomId);
            WebSocketSessionManager.getInstance().sendMessage(new EventAlertWebPacket("sendEventAlert", client.getPlayer().getData().getFigure(), client.getPlayer().getData().getUsername(), this.merge(params), roomId + ""));

            final IMessageComposer whisper = new TalkMessageComposer(
                    -1, Locale.getOrDefault("none.ishere", "Hay un nuevo evento creado por %username%, haz click <a href=\'event:navigator/goto/" + roomId + "'><b>aquí</b></a> para ir al evento.").replace("%message%", this.merge(params)) .replace("%username%", client.getPlayer().getData().getUsername()) + "<br><br><i> " + client.getPlayer().getData().getUsername() + "</i>", ChatEmotion.NONE, 33);

            for (ISession session : NetworkManager.getInstance().getSessions().getSessions().values()) {
                if (session.getPlayer() != null && !session.getPlayer().getSettings().ignoreEvents())
                            session.send(whisper);
                    }

            logDesc = "El staff %s ha creado un evento en la sala '%b'."
                    .replace("%s", client.getPlayer().getData().getUsername())
                    .replace("%b", client.getPlayer().getEntity().getRoom().getData().getName());

            for (Session player : ModerationManager.getInstance().getLogChatUsers()) {
                player.send(new InstantChatMessageComposer(logDesc, Integer.MAX_VALUE - 1));
            }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "eventalert_command";
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
        return Locale.get("command.eventalert.description");
    }
}
