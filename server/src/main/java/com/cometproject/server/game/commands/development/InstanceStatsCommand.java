package com.cometproject.server.game.commands.development;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes instance stats command behavior for the Comet subsystem.
 */
public class InstanceStatsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        StringBuilder message = new StringBuilder("<b>Comet Server - Instance Statistics </b><br><br>");

        message.append("Build: " + Comet.getBuild() + "<br><br>");
        message.append("<b>Game Statistics</b><br>Players online: " + PlayerManager.getInstance().size() + "<br>Active rooms: " + RoomManager.getInstance().getRoomInstances().size() + "<br><br>");
//        message.append("<b>Room Data</b><br>" + "Cached data instances: " + RoomManager.getInstance().getRoomDataInstances().size() + "<br>" + "<br>" + "<b>Group Data</b><br>" + "Cached data instances: " + GroupManager.getInstance().getGroupData().size() + "<br>" + "Cached instances: " + GroupManager.getInstance().getGroupInstances().size());

        client.send(new AlertMessageComposer(message.toString()));
//
//        final StringBuilder queryStats = new StringBuilder("Queries\n==============================================\n");
//
//        for(Map.Entry<String, AtomicInteger> query : SqlHelper.getQueryCounters().entrySet()) {
//            queryStats.append("\n\nQuery: " + query.getKey()).append("\nCount: " + query.getValue().get());
//        }
//
//        client.send(new MotdNotificationMessageComposer(queryStats.toString()));
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
