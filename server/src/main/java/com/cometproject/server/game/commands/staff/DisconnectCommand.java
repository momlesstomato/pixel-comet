package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes disconnect command behavior for the Comet subsystem.
 */
public class DisconnectCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.disconnect.none", "Who do you want to disconnect?"), client);
            return;
        }

        String username = params[0];

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (session == null) {
            sendNotif(Locale.get("command.user.offline"), client);
            return;
        }

        if (session == client) {
            sendNotif(Locale.get("command.disconnect.himself"), client);
            return;
        }

        if (!session.getPlayer().getPermissions().getRank().disconnectable()) {
            sendNotif(Locale.get("command.disconnect.undisconnectable"), client);
            return;
        }

        this.logDesc = "El Staff -c le ha dado DC a -d"
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-d", session.getPlayer().getData().getUsername());

        session.disconnect();
        isExecuted(client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "disconnect_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.disconnect.description");
    }

    /**
     * Executes bypass filter for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean bypassFilter() {
        return true;
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
