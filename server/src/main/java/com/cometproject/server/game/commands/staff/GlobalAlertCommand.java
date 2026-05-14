package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes global alert command behavior for the Comet subsystem.
 */
public class GlobalAlertCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1) {
            sendNotif("Parámetros inválidos. :globalalert tipo texto", client);
            return;
        }

        if(params[0].equals("game")){
            NetworkManager.getInstance().getSessions().broadcast(new MassEventMessageComposer("galert/@gm/"+client.getPlayer().getEntity().getRoom().getId()+"/"+client.getPlayer().getData().getUsername()+"/"+this.merge(params, 1)));
            sendNotif("Enviado", client);
        }

        if(params[0].equals("event")){
            NetworkManager.getInstance().getSessions().broadcast(new MassEventMessageComposer("galert/@ev/"+client.getPlayer().getEntity().getRoom().getId()+"/"+client.getPlayer().getData().getUsername()+"/"+this.merge(params, 1)));
            sendNotif("Enviado", client);
        }

        if(params[0].equals("publi")){
            NetworkManager.getInstance().getSessions().broadcast(new MassEventMessageComposer("galert/@pbl/"+client.getPlayer().getEntity().getRoom().getId()+"/"+client.getPlayer().getData().getUsername()+"/"+this.merge(params, 1)));
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
        return "globalalert_command";
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
        return Locale.get("command.globalalert.description");
    }
}
