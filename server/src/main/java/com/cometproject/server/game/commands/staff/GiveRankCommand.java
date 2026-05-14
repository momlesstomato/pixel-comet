package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Describes give rank command behavior for the Comet subsystem.
 */
public class GiveRankCommand extends ChatCommand {
    private String logDesc = "";


    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            GiveRankCommand.sendNotif("Argumentos inválidos", client);
            return;
        }
        String username = params[0];
        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);
        if(user != null){
            try{
                int rank = Integer.parseInt(params[1]);
                if(!(rank >= 20)){
                    user.getPlayer().getData().setRank(rank);
                    PlayerDao.updateRank(rank, user.getPlayer().getEntity().getUsername());
                    GiveRankCommand.sendNotif("Le has entregado el rango al usuario con éxito", client);
                }
                else GiveRankCommand.sendNotif("Rango inválido", client);
            }
            catch (Exception ex){
                GiveRankCommand.sendNotif("Rango inválido.", client);
            }
        }
        else{
            try{
                int rank = Integer.parseInt(params[1]);
                if(!(rank >= 20)){
                    PlayerDao.updateRank(rank, username);
                    GiveRankCommand.sendNotif("Le has entregado el rango al usuario con éxito", client);
                }
                else GiveRankCommand.sendNotif("Rango inválido", client);
            }
            catch (Exception ex){
                GiveRankCommand.sendNotif("Rango inválido.", client);
            }
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "giverank_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "%username% %rank%";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.giverank.description");
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
