package com.cometproject.server.game.commands.staff.banning;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.moderation.BanManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;


/**
 * Describes un ban command behavior for the Comet subsystem.
 */
public class UnBanCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {
            sendNotif(Locale.getOrDefault("command.params.length", "Oops! You did something wrong!"), client);
            return;
        }

        String username = params[0];
        Integer PlayerId = PlayerDao.getIdByUsername(username);

        if(BanManager.getInstance().unBan(PlayerId.toString())) {
            sendNotif(Locale.getOrDefault("command.unban.success", "You unbanned %s successfully!")
                    .replace("%s", username), client);
        } else {
            sendNotif(Locale.getOrDefault("command.unban.notbanned", "Oops! Maybe this user isn't banned or has machine ban."), client);
        }

        this.logDesc = "El Staff -c ha desbaneado a -d"
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-d", username);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "unban_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.unban", "%username%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.unban.description");
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
