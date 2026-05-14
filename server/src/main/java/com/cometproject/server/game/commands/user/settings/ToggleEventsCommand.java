package com.cometproject.server.game.commands.user.settings;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.types.PlayerSettings;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Describes toggle events command behavior for the Comet subsystem.
 */
public class ToggleEventsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {

        final PlayerSettings playerSettings = client.getPlayer().getSettings();

        if (playerSettings == null) {
            return;
        }

        playerSettings.setIgnoreInvites(!playerSettings.ignoreEvents());

        final String msg = playerSettings.ignoreEvents() ? "You are now ignoring event notifications!" : "Event notifications are now enabled.";
        sendNotif(Locale.getOrDefault("command.toggleevents.msg." + playerSettings.ignoreEvents(), msg), client);

        PlayerDao.saveIgnoreEvents(playerSettings.ignoreEvents(), client.getPlayer().getId());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "ignoreevents_command";
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
        return Locale.get("command.toggleevents.description");
    }
}
