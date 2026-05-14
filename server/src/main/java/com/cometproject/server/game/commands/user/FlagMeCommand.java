package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes flag me command behavior for the Comet subsystem.
 */
public class FlagMeCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] message) {
        if (message.length != 1) {
            final String command = Locale.get("command.flagme.name");
            final String yes = Locale.getOrDefault("command.flagme.yes", "yes");
            sendNotif(Locale.getOrDefault("command.flagme.none", "Type:" + command + " " + yes + "'"), client);
            return;
        } else {
            if (client == null || client.getPlayer() == null || client.getPlayer().getData() == null) {
                return;
            }

            final String command = Locale.get("command.flagme.name");
            final String yes = Locale.getOrDefault("command.flagme.yes", "yes");

            if (!message[0].equals(yes)) {
                sendNotif(Locale.getOrDefault("command.flagme.incorrect", "To continue, type ':" + command + " " + yes + "'"), client);
                return;
            } else if (client.getPlayer().getData().getChangingName()) {
                sendNotif(Locale.getOrDefault("command.flagme.alreadybought", "Hey! You have entered this command already, click on your avatar and after that on the button 'Change your name'."), client);
                return;
            } else {
                client.getPlayer().getData().setChangingName(true);
                client.send(new UserObjectMessageComposer(client.getPlayer()));
                sendNotif(Locale.getOrDefault("command.flagme.bought", "You can change your username by clicking on yourself in a room and then click on 'Change your name'. You will be banned if your new name violates the rules!"), client);
            }
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "flagme_command";
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
        return Locale.get("command.flagme.description");
    }
}