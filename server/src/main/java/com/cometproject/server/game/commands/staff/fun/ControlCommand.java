package com.cometproject.server.game.commands.staff.fun;

import com.cometproject.api.config.CometSettings;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalQueue;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes control command behavior for the Comet subsystem.
 */
public class ControlCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if(params[0].equals("survivalToggle")){
            CometSettings.setSurvivalEnabled(!CometSettings.survivalEnabled);
            client.send(new NotificationMessageComposer("newuser", "Properly did the job boss, nothing is now " + CometSettings.survivalEnabled + ".", ""));
            return;
        }

        if(params[0].equals("status")){
            client.send(new NotificationMessageComposer("newuser", "Size: " + SurvivalQueue.getInstance().getFigures(7360).size() + ".\n" + SurvivalQueue.getInstance().getFigures(7360).toString()));
        }

        if(client.getPlayer().getEntity().hasAttribute("control")){
            client.send(new NotificationMessageComposer("control", "Has dejado de controlar a " + client.getPlayer().getEntity().getAttribute("control") + ".\n\nVuelve a seleccionar a otro siervo cuando lo desees."));
            client.getPlayer().getEntity().removeAttribute("control");

            this.logDesc = "%s ha dejado de controlar a %r en la sala '%b'."
                    .replace("%s", client.getPlayer().getData().getUsername())
                    .replace("%b", client.getPlayer().getEntity().getRoom().getData().getName())
                    .replace("%r", params[0]);
            return;
        }

        if (params.length != 1){
            return;
        }

        if (NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]) == null) {
            return;
        }

        client.getPlayer().getEntity().setAttribute("control", params[0]);
        client.send(new NotificationMessageComposer("control", "Has empezado a controlar a " + params[0] + "."));

        this.logDesc = "%s ha empezado a controlar a %r en la sala '%b'."
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%b", client.getPlayer().getEntity().getRoom().getData().getName())
                .replace("%r", params[0]);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "control_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.control.description");
    }

    /**
     * Indicates whether hidden applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isHidden() {
        return true;
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription(){
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable(){
        return true;
    }

}
