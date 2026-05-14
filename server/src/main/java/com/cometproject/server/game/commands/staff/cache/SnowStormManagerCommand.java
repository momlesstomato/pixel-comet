package com.cometproject.server.game.commands.staff.cache;

import com.cometproject.api.config.CometSettings;
import com.cometproject.games.snowwar.SnowPlayerQueue;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes snow storm manager command behavior for the Comet subsystem.
 */
public class SnowStormManagerCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) return;

        final String type = params[0];
        final String alert;

        switch (type){
            case "clear":
                SnowPlayerQueue.cleanupQueues();
                alert = "Acabas de limpiar las colas del Snow Storm.";
                break;
            case "toggle":
                CometSettings.toggleSnowStorm(true);
                alert = "Acabas de activar el Snow Storm.";
                break;
            case "disable":
                CometSettings.toggleSnowStorm(false);
                alert = "Acabas de desactivar el Snow Storm.";
                break;
            default:
                alert = "";
        }

        client.getPlayer().sendBubble("snow", alert);

    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "vipbundle_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.type", "%type%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.vipbundle.description", "Da un pack de VIP al usuario seleccionado.");
    }
}
