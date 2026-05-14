package com.cometproject.server.game.commands.user;

import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes staff on command behavior for the Comet subsystem.
 */
public class StaffOnCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        StringBuilder text = new StringBuilder();
        for (ISession session : NetworkManager.getInstance().getSessions().getSessions().values()){
            if(session.getPlayer().getData().getRank() != 1){
                text.append(session.getPlayer().getData().getUsername() + " | " + session.getPlayer().getData().getRank()).append("\n");
            }
        }

        StaffOnCommand.sendAlert("Staffs en linea:\n\n" + text, client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "staffonline_command";
    }
    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault(null, "");
    }
    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.staffonline.description");
    }
}

