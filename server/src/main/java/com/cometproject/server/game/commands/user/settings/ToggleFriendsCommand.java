package com.cometproject.server.game.commands.user.settings;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Describes toggle friends command behavior for the Comet subsystem.
 */
public class ToggleFriendsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (client.getPlayer().getSettings().getAllowFriendRequests()) {
            client.getPlayer().getSettings().setAllowFriendRequests(false);
            sendNotif(Locale.getOrDefault("command.togglefriends.disabled", "You have disabled friend requests."), client);
        } else {
            client.getPlayer().getSettings().setAllowFriendRequests(true);
            sendNotif(Locale.getOrDefault("command.togglefriends.enabled", "You have enabled friend requests."), client);
        }

        PlayerDao.saveAllowFriendRequests(client.getPlayer().getSettings().getAllowFriendRequests(), client.getPlayer().getId());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "togglefriends_command";
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
        return Locale.get("command.togglefriends.description");
    }
}
