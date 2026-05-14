package com.cometproject.server.game.commands.staff.rewards;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.achievements.BattlePassGlobals;
import com.cometproject.server.game.achievements.types.BattlePassMission;
import com.cometproject.server.game.achievements.types.BattlePassMissionEnums;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;

/**
 * Describes event reward command behavior for the Comet subsystem.
 */
public class EventRewardCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1){
            sendNotif("Uso del comando incorrecto. :eventreward nombre", client);
        }

        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);
        if(session != null){
            session.getPlayer().getData().increaseCurrency(
                    CometBootstrap.resolve(ICurrencyService.class).currencyCodeForUseCase(CurrencyUseCases.STAFF_EVENT_PRIMARY),
                    7);
            session.getPlayer().getData().increaseCredits(10);
            session.getPlayer().getData().flush();
            session.getPlayer().sendBalance();

            sendNotif("Comando ejecutado con éxito", client);
            NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer("generic", "¡%username% ha ganado un evento!".replace("%username%", session.getPlayer().getData().getUsername())));

            BattlePassMission ms = BattlePassGlobals.battlePassMissions.stream().filter(x -> x.type == BattlePassMissionEnums.MissionType.EVENTWON).findAny().orElse(null);
            if(ms != null){
                if(session.getPlayer().getData().battlePass != null) session.getPlayer().getData().battlePass.addExperiencePoint(ms.id);
            }
        }
        else sendNotif("Usuario no encontrado", client);

        this.logDesc = "El staff %s ha hecho eventreward al usuario '%b'"
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%b", params[0]);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "eventreward_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "%username% %badge%";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.eventreward.description", "Gives a player a badge & points");
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
