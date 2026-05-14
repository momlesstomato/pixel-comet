package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.items.ItemDao;

/**
 * Describes search command behavior for the Comet subsystem.
 */
public class SearchCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if(params.length == 0){
            if(client.getPlayer().getData().isSearchFurniActive){
                client.getPlayer().getData().isSearchFurniActive = false;
                client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Has desactivado la búsqueda de furnis.", 1));
                return;
            }
            else{
                client.getPlayer().getData().isSearchFurniActive = true;
                client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Has activado la búsqueda de furnis. Haz doble click en un furni para saber su sección en el catálogo.", 1));
            }
        }
        else{
            int furniId = ItemDao.getFurniIdByName(params[0]);
            if(furniId != 0){
                int catalogId = ItemDao.getCatalogIdByItem(furniId);
                String catalogName = ItemDao.getCatalogNameByCatalogId(catalogId);
                if(catalogName != "no"){
                    String messageFurni = "Este furni se encuentra en la sección " + catalogName + ". [" + furniId +"]";
                    client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), messageFurni, 1));
                }
                else client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Este furni no se ha podido encontrar en el catálogo.", 1));
            }
            else client.send(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "Este furni no se ha podido encontrar en el catálogo.", 1));
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "cmd_search";
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
        return Locale.get("command.search.description");
    }
}

