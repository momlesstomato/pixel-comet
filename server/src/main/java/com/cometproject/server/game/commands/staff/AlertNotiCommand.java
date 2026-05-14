package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes alert noti command behavior for the Comet subsystem.
 */
public class AlertNotiCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1) {
            sendNotif("Parámetros inválidos. :alertnoti tipo texto", client);
            return;
        }

        int roomId = client.getPlayer().getEntity().getRoom().getId();

        if(params[0].equals("inter")){
            NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(Locale.get("command.alertinter.image"), this.merge(params, 1) + "\n\n- " + client.getPlayer().getData().getUsername(), "navigator/goto/" + roomId));
            sendNotif("Enviado", client);
        }

        if(params[0].equals("master")){
            NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(Locale.get("command.alertmaster.image"), this.merge(params, 1) + "\n\n- " + client.getPlayer().getData().getUsername(), "navigator/goto/" + roomId));
            sendNotif("Enviado", client);
        }

        if(params[0].equals("dj")){
            NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(Locale.get("command.alertdj.image"), this.merge(params, 1) + "\n\n- " + client.getPlayer().getData().getUsername(), "navigator/goto/" + roomId));
            sendNotif("Enviado", client);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "alertnoti_command";
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
        return Locale.get("command.alertnoti.description");
    }
}
