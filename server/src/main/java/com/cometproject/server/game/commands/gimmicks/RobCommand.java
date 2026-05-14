package com.cometproject.server.game.commands.gimmicks;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.utilities.RandomUtil;


/**
 * Describes rob command behavior for the Comet subsystem.
 */
public class RobCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.user.invalid", "Invalid username!"), client);
            return;
        }

        String object;

        String robbedPlayer = params[0];
        if(RandomUtil.getRandomInt(0, 100) <= 50) {
            object = Locale.getOrDefault("command.rob.success", "%s robbed %b")
                    .replace("%s", client.getPlayer().getData().getUsername())
                    .replace("%b", robbedPlayer);
        } else {
            object = Locale.getOrDefault("command.rob.failed", "%s tried to rob %b but he couldn't.")
                    .replace("%s", client.getPlayer().getData().getUsername())
                    .replace("%b", robbedPlayer);
        }

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), object, 34));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "rob_command";
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
        return Locale.get("command.rob.description");
    }
}
