package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes publisher command behavior for the Comet subsystem.
 */
public class PublisherCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        globalNotification(Locale.getOrDefault("publisher.image", "PBL"), Locale.getOrDefault("publisher.text", "Oleada de publicidad abierta. Ven a participar y consigue fabulosos premios.\n\n- -u").replace("-u", client.getPlayer().getData().getUsername()), client.getPlayer().getEntity().getRoom().getId());
    }

    /**
     * Executes global notification for this Comet contract.
     *
     * @param image Image supplied by the caller.
     * @param message Message supplied by the caller.
     * @param roomId Room identifier used by the operation.
     */
    protected void globalNotification(String image, String message, int roomId) {
        NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(image, message, "navigator/goto/" + roomId));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "publisher_command";
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
        return Locale.get("command.publisher.description");
    }
}
