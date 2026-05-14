package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.PetInventoryMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.bots.PlayerBotDao;
import com.cometproject.server.storage.queries.pets.PetDao;


/**
 * Describes empty bots command behavior for the Comet subsystem.
 */
public class EmptyBotsCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendAlert(Locale.getOrDefault("command.emptypets.confirm", "<b>Warning!</b>\rAre you sure you want to delete all of your bots?\r\rIf you are sure type  <b>:" + Locale.get("command.emptypets.name") + " yes</b>"), client);
        } else {
            final String yes = Locale.getOrDefault("command.empty.yes", "yes");

            if (!params[0].equals(yes)) {
                sendAlert(Locale.getOrDefault("command.emptybots.confirm", "<b>Warning!</b>\rAre you sure you want to delete all of your bots?\r\rIf you are sure type  <b>:" + Locale.get("command.emptypets.name") + " yes</b>"), client);
            } else {
                PetDao.deletePets(client.getPlayer().getId());
                client.getPlayer().getPets().clearPets();

                client.send(new PetInventoryMessageComposer(client.getPlayer().getPets().getPets()));

                PlayerBotDao.deleteBots(client.getPlayer().getId());
                client.getPlayer().getBots().clearBots();
                client.send(new BotInventoryMessageComposer());

                sendNotif(Locale.getOrDefault("command.emptybots.emptied", "Your bots inventory was cleared."), client);
            }

            client.send(new UpdateInventoryMessageComposer());
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "emptybots_command";
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
        return Locale.get("command.emptybots.description");
    }
}
