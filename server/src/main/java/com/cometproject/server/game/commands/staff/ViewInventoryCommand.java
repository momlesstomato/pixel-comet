package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Describes view inventory command behavior for the Comet subsystem.
 */
public class ViewInventoryCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {

        boolean error = false;

        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.viewinventory.reset", "You returned back your inventory!"), client);
            client.getPlayer().getInventory().loadItems(0);
            client.send(new UpdateInventoryMessageComposer());
            return;
        }

        String username = params[0];

        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (user != null) {
            sendNotif(Locale.getOrDefault("command.user.mustbe.offline", "This user must offline!"), client);
            return;
        }

        try {
            Integer PlayerId = PlayerDao.getIdByUsername(username);
            if (PlayerId != 0) {
                client.getPlayer().getInventory().loadItems(PlayerId);
                client.send(new UpdateInventoryMessageComposer());
                sendNotif(Locale.getOrDefault("command.viewinventory.success", "You've changed your inventory successfully, write :viewinventory to return back your inventory."), client);
            } else {
                error = true;
            }
        } catch (Exception e) {
            error = true;
        } finally {
            if (error) {
                sendNotif(Locale.getOrDefault("command.viewinventory.error", "Error!, maybe the user you searched for does not exist!"), client);
            }
        }

        this.logDesc = "El staff %s ha hecho viewinventory al usuario '%b'"
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%b", username);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "viewinventory_command";
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
        return Locale.get("command.viewinventory.description");
    }

    /**
     * Indicates whether async applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAsync() {
        return true;
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription(){
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable(){
        return true;
    }
}
