package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.messenger.InstantChatMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.websockets.WebSocketSessionManager;
import com.cometproject.server.network.websockets.packets.outgoing.alerts.HAWebPacket;


/**
 * Describes hotel alert command behavior for the Comet subsystem.
 */
public class HotelAlertCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] message) {
        if (message.length == 0) {
            return;
        }

        String title = Locale.get("command.hotelalert.title");
        String msg = this.merge(message) + "<br><br><i>- " + client.getPlayer().getData().getUsername() + "</i>";
        String img = "${image.library.url}album1358/frank_wave_001.gif";

        if(CometSettings.hotelName.equals("Habbo")) {
            WebSocketSessionManager.getInstance().sendMessage(new HAWebPacket("sendHotelAlert", client.getPlayer().getData().getFigure(), client.getPlayer().getData().getUsername(), this.merge(message)));
        } else {
            IMessageComposer msg1 = new AdvancedAlertMessageComposer(title, msg, img);
            NetworkManager.getInstance().getSessions().broadcast(msg1);
        }

        this.logDesc = "El Staff -c ha mandado una alerta a todo el hotel. [-s]"
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-s", this.merge(message));

        for (Session player : ModerationManager.getInstance().getLogChatUsers()) {
            player.send(new InstantChatMessageComposer(this.logDesc, Integer.MAX_VALUE - 1));
        }
    }

    /**
     * Indicates whether async applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAsync() {
        return true;
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "hotelalert_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.hotelalert.description");
    }
}
