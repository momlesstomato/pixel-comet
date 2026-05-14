package com.cometproject.server.game.commands.staff.banning;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.moderation.BanManager;
import com.cometproject.server.game.moderation.types.BanType;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes trade ban command behavior for the Comet subsystem.
 */
public class TradeBanCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) {
            sendNotif(Locale.getOrDefault("command.paramsZ.length", "Recuerda introducir %usuario% %tiempo% %razón%."), client);
            return;
        }

        String username = params[0];
        int length = Integer.parseInt(params[1]);

        Session user = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        if (user == null) {
            sendNotif(Locale.getOrDefault("command.userZ.offline", "El usuario está desconectado."), client);
            return;
        }

        if (user == client || !user.getPlayer().getPermissions().getRank().bannable() || user.getPlayer().getPermissions().getRank().getId() >= client.getPlayer().getPermissions().getRank().getId()) {
            sendNotif(Locale.getOrDefault("command.userZ.notbannable", "No tienes permisos para banear a este usuario."), client);
            return;
        }

        long expire = Comet.getTime() + (length * 3600);

        user.getPlayer().getStats().setTradeLock(expire);
        user.getPlayer().getSettings().setAllowTrade(false);
        user.getPlayer().getSettings().flush();

        user.getPlayer().getStats().addBan();
        user.getPlayer().getStats().save();

        user.send(new NotificationMessageComposer("trade_block", Locale.getOrDefault("user.got.tradeblocked", "Se ha detectado una actividad sospechosa en tu cuenta y tus tradeos han sido bloqueados durante " + length + " minutos.")));
        client.send(new NotificationMessageComposer("trade_block", Locale.getOrDefault("user.got.tradeblocked.success", "Has bloqueado correctamente los tradeos de " + username + " durante " + length + " minutos.")));

        BanManager.getInstance().banPlayer(BanType.TRADE, user.getPlayer().getId() + "", length, expire, params.length > 2 ? this.merge(params, 2) : "", client.getPlayer().getId());


        this.logDesc = "El Staff -c ha bloqueado los tradeos a -d por -e minutos."
                .replace("-c", client.getPlayer().getData().getUsername())
                .replace("-d", user.getPlayer().getData().getUsername())
                .replace("-e", Integer.toString(length));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "tradeban_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.ban", "%username% %time% %reason%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.tradeban.description");
    }

    /**
     * Executes bypass filter for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean bypassFilter() {
        return true;
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
