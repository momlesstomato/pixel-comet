package com.cometproject.server.game.commands.development;

import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes item virtual id command behavior for the Comet subsystem.
 */
public class ItemVirtualIdCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length == 0) {
            client.send(new AlertMessageComposer("There are currently " + ItemManager.getInstance().getItemIdToVirtualIds().size() + " item virtual IDs in memory."));
            return;
        }

        try {
            final int virtualId = Integer.parseInt(params[0]);

            client.send(new AlertMessageComposer("Virtual ID: " + virtualId + "\nReal ID: " + ItemManager.getInstance().getItemIdByVirtualId(virtualId)));
        } catch (Exception e) {

        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "dev";
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
        return null;
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
