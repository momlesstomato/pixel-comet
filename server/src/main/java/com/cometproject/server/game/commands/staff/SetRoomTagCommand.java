package com.cometproject.server.game.commands.staff;

import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes set room tag command behavior for the Comet subsystem.
 */
public class SetRoomTagCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif("Etiqueta inválida", client);
            return;
        }

        client.getPlayer().getEntity().getRoom().getData().setTags(params);
        sendNotif("Seteado", client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "warp_command";
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
}
