package com.cometproject.server.game.commands.user.settings;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.types.PlayerSettings;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Describes disable whisper command behavior for the Comet subsystem.
 */
public class DisableWhisperCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        final PlayerSettings playerSettings = client.getPlayer().getSettings();

        playerSettings.setDisableWhisper(!playerSettings.disableWhisper());

        final String msg = playerSettings.disableWhisper() ? "disabled" : "enabled";
        sendNotif(Locale.getOrDefault("command.disablewhisper." + msg, String.format("Whispers are now %s", msg)), client);
        PlayerDao.updateDisableWhisper(playerSettings.disableWhisper(), client.getPlayer().getId());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "disablewhisper_command";
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
        return Locale.get("command.disablewhisper.description");
    }
}
