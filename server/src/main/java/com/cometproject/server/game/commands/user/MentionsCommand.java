package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.types.PlayerMention;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes mentions command behavior for the Comet subsystem.
 */
public class MentionsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        StringBuilder builder = new StringBuilder();
        for (PlayerMention mention : client.getPlayer().getMentions()) {
            mention.getUsername().replace("%username%", mention.getUsername());
            mention.getMessage().replace("%message%", mention.getMessage());
            builder.append(mention.getUsername()).append(": ").append(mention.getMessage()).append("\n");
        }
        client.getPlayer().getSession().send(new MotdNotificationMessageComposer(builder.toString()));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "mentions_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return null;
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.mentions.description");
    }
}