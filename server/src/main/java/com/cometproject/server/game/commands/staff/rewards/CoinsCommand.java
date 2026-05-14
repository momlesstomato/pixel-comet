package com.cometproject.server.game.commands.staff.rewards;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;


/**
 * Describes coins command behavior for the Comet subsystem.
 */
public class CoinsCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2)
            return;

        String username = params[0];
        String credits_ = params[1];


        try {
            int credits = Integer.parseInt(credits_);
            Session player = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

            if (player == null) {
                PlayerData playerData = PlayerDao.getDataByUsername(username);

                if (playerData == null) return;

                playerData.increaseCredits(credits);
                playerData.save();
                return;
            }

            player.getPlayer().getData().increaseCredits(credits);

            player.send(new NotificationMessageComposer(
                    Locale.getOrDefault("command.koins.image", "koins"),
                    Locale.getOrDefault("command.koins.successmessage", "Acabas de recibir %amount% Koins por participar en un evento.").replace("%amount%", String.valueOf(credits))
            ));

            player.getPlayer().getData().save();
            player.getPlayer().sendBalance();
        } catch (Exception e) {
            client.send(new AdvancedAlertMessageComposer(Locale.get("command.coins.errortitle"), Locale.get("command.coins.formaterror")));
        }

        this.logDesc = "El Staff -c le ha dado -m Arinos a -d"
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-m", credits_)
                .replace("-d", username);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "coins_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username" + " " + "command.parameter.amount", "%username% %amount%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.coins.description");
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription() {
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable() {
        return true;
    }
}
