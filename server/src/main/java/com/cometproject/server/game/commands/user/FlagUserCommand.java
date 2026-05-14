package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes flag user command behavior for the Comet subsystem.
 */
public class FlagUserCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.flaguser.none", "Who do you want to change his name?"), client);
            return;
        }

        String username = params[0];

        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (user == null || user.getPlayer() == null || user.getPlayer().getData() == null) {
            return;
        }

        user.getPlayer().getData().setFlaggingUser(true);
        user.getPlayer().getData().setChangingName(true);
        user.send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.flaguser.title", "Grrrr!"), Locale.getOrDefault("command.flaguser.message", "Your name is inappropriate! You can change your name by clicking on yourself. Do this within a minute or you will be banned!")));
        user.send(new UserObjectMessageComposer(user.getPlayer()));
        sendNotif("Cambio de nombre enviado con éxito", client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "flaguser_command";
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
        return Locale.get("command.flaguser.description");
    }
}