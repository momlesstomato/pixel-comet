package com.cometproject.server.game.commands.user;

import com.cometproject.api.config.CometSettings;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.commands.CommandManager;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

import java.util.List;


/**
 * Describes commands command behavior for the Comet subsystem.
 */
public class CommandsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        StringBuilder message = new StringBuilder();
        List<ChatCommand> commands = CommandManager.getInstance().getCommandsForRank(client.getPlayer().getData().getRank());

        message.append("<b>Lista de comandos de %hotelName%</b>\n".replace("%hotelName%", CometSettings.hotelName));
        message.append("Los comandos son otra forma de interactuar con el hotel o con otros jugadores. Aquí tienes la lista con todos los comandos del hotel: \n\n");

        for (ChatCommand c : commands) {
            if(!c.getDescription().equals("")) message.append(c.getDescription()).append("\n");
        }

        client.send(new MotdNotificationMessageComposer(message.toString()));

    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "commands_command";
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
        return Locale.get("command.commands.description");
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
}
