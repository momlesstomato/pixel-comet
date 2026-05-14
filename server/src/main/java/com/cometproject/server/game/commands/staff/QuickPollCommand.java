package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.tasks.CometThreadManager;

import java.util.concurrent.TimeUnit;

/**
 * Describes quick poll command behavior for the Comet subsystem.
 */
public class QuickPollCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        if (!room.getRights().hasRights(client.getPlayer().getId())) {
            if (!client.getPlayer().getPermissions().getRank().modTool()) {
                return;
            }
        }

        String question = this.merge(params);

        if (params[0].equals("end")) {
            room.endQuestion();
        } else if(params[0].equals("infobus")) {
            if(params[1] != null)
            room.startInfobus(params[1]);
            CometThreadManager.getInstance().executeSchedule(room::endInfobus, 30, TimeUnit.SECONDS);
        } else {
            room.startQuestion(question);
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "quickpoll_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.quickpoll.description");
    }
}
