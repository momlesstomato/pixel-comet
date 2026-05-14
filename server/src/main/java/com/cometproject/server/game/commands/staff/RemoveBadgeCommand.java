package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes remove badge command behavior for the Comet subsystem.
 */
public class RemoveBadgeCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2)
            return;

        final String username = params[0];
        final String badge = params[1];

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);

        if (session != null) {
            session.getPlayer().getInventory().removeBadge(params[1], true);
            sendNotif(Locale.get("command.removebadge.success").replace("%username%", username).replace("%badge%", badge), client);
        } else {
            sendNotif(Locale.getOrDefault("command.removebadge.userisoff", "This user is not online, you can only remove badges from online users"), client);
        }

        this.logDesc = "El Staff -c le ha quitado la placa -d a -e"
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-d", badge)
                .replace("-e", username);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "removebadge_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username" + " " + "command.parameter.badge", "%username% %badge%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.removebadge.description");
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
