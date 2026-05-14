package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes notification command behavior for the Comet subsystem.
 */
public class NotificationCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        String image = "generic";
        String message = "";

        if (params.length > 1) {
            image = params[0];
            message = this.merge(params, 1);
        } else {
            message = this.merge(params);
        }

        globalNotification(image, message, client);

        this.logDesc = "El staff %s ha hecho notification con el parámetro '%p'"
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%p", message);
    }

    /**
     * Executes global notification for this Comet contract.
     *
     * @param image Image supplied by the caller.
     * @param message Message supplied by the caller.
     * @param client Client supplied by the caller.
     */
    protected void globalNotification(String image, String message, Session client) {
        NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(image, message));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "notification_command";
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
        return Locale.get("command.notification.description");
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
