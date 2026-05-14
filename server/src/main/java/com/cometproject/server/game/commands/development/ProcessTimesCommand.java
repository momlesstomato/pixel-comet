package com.cometproject.server.game.commands.development;

import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;

import java.util.ArrayList;

/**
 * Describes process times command behavior for the Comet subsystem.
 */
public class ProcessTimesCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        final StringBuilder processTimesBuilder = new StringBuilder();

        Room room = client.getPlayer().getEntity().getRoom();

        if (room.getProcess().getProcessTimes() == null) {
            room.getProcess().setProcessTimes(new ArrayList<>());

            client.send(new AlertMessageComposer("Process times for this room are now being recorded. (Max: 30)"));
            return;
        }

        for (Long processTime : room.getProcess().getProcessTimes()) {
            processTimesBuilder.append(processTime + "\n");
        }

        client.send(new AlertMessageComposer("<b>Process Times</b><br><br>" + processTimesBuilder.toString()));

        room.getProcess().getProcessTimes().clear();
        room.getProcess().setProcessTimes(null);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "debug";
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
        return "";
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
