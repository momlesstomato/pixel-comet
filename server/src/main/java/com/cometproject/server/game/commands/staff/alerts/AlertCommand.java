package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes alert command behavior for the Comet subsystem.
 */
public class AlertCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            sendNotif(Locale.getOrDefault("command.alert.none", "Who do you want to send a alert?"), client);
            return;
        }

        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);

        if (user == null) {
            sendNotif(Locale.getOrDefault("command.user.offline", "This user is offline!"), client);
            return;
        }

        user.send(new AlertMessageComposer(this.merge(params, 1)));
        sendNotif("Alerta enviada con éxito", client);

        this.logDesc = "El Staff -c le ha mandado una alerta a -d<. [-e]"
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-d", user.getPlayer().getData().getUsername())
                .replace("-e", this.merge(params));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "alert_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.name" + " " + "command.parameter.message", "%username% %message%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.alert.description");
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription() {
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable() {
        return true;
    }
}
