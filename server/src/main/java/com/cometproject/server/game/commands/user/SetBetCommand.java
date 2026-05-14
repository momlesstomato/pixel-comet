package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes set bet command behavior for the Comet subsystem.
 */
public class SetBetCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendWhisper(Locale.getOrDefault("command.setbet.none", "Debes colocar un número válido para apostar, :setbet CANTIDAD."), client);
            return;
        }

        try {
            int amount = Integer.parseInt(params[0]);

            if (amount < 0) {
                amount = 5;
            } else if (amount > 100) {
                sendWhisper(Locale.getOrDefault("command.setbet.limit", "No puedes apostar más de 100 Tokens."), client);
                amount = 100;
            }

            client.getPlayer().getEntity().setBetAmount(amount);
            sendNotif(Locale.getOrDefault("command.setbet.set", "Has colocado tus apuestas en %s Tokens.").replace("%s", amount + ""), client);
        } catch (Exception e) {
            sendNotif(Locale.getOrDefault("command.setbet.invalid", "Por favor, introduce únicamente valores numéricos"), client);
        }
    }


    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "setbet_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.setbet.number", "%amount%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.setbet.description");
    }
}
