package com.cometproject.server.game.commands.user;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;


public class BankCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if(params.length != 3){
            BankCommand.sendNotif("Uso del comando incorrecto. :pay usuario cantidad moneda.", client);
            return;
        }

        if(((PlayerData)client.getPlayer().getData()).viewPoints <= 2){
            BankCommand.sendNotif("Debes de tener mas de dos horas jugadas para usar el pay", client);
            return;
        }

        try{
            int cantidad = Integer.parseInt(params[1]);
            Session kissedSession = NetworkManager.getInstance().getSessions().getByPlayerUsername(params[0]);
            if(kissedSession == null){
                BankCommand.sendNotif(Locale.getOrDefault("command.user.offline", "\u00a1El usuario no est\u00e1 en l\u00ednea!"), client);
                return;
            }
            if (kissedSession.getPlayer().getEntity() == null) {
                BankCommand.sendNotif(Locale.getOrDefault("command.user.notinroom", "El usuario no est\u00e1 en ninguna sala."), client);
                return;
            }
            if (kissedSession.getPlayer().getData().getUsername().equals(client.getPlayer().getData().getUsername())) {
                BankCommand.sendNotif(Locale.getOrDefault("command.puke.himself", "No puedes darte amor a ti mismo."), client);
                return;
            }

            final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
            final String requestedCurrency = params[2].toLowerCase();
            final String currencyCode = "creditos".equals(requestedCurrency)
                    ? "credits"
                    : currencyService.resolveCurrencyCode(requestedCurrency);
            final ICurrencyDefinition definition = currencyService.definition(currencyCode);

            if(definition.isCredits()){
                if(cantidad < client.getPlayer().getData().getCredits()){
                    client.getPlayer().getData().decreaseCredits(cantidad);
                    kissedSession.getPlayer().getData().increaseCredits(cantidad);
                    client.getPlayer().sendBalance();
                    kissedSession.getPlayer().sendBalance();

                    BankCommand.sendNotif("Le has enviado " + cantidad + " credits a " + params[0], client);
                    BankCommand.sendNotif(client.getPlayer().getEntity().getUsername() + " te ha enviado " + cantidad + " credits.", kissedSession);
                }
            }
            else {
                if(cantidad < client.getPlayer().getData().getCurrencyBalance(currencyCode)){
                    client.getPlayer().getData().decreaseCurrency(currencyCode, cantidad);
                    kissedSession.getPlayer().getData().increaseCurrency(currencyCode, cantidad);
                    client.getPlayer().sendBalance();
                    kissedSession.getPlayer().sendBalance();

                    final String noun = cantidad == 1 ? definition.getNounSingular() : definition.getNounPlural();
                    BankCommand.sendNotif("Le has enviado " + cantidad + " " + noun + " a " + params[0], client);
                    BankCommand.sendNotif(client.getPlayer().getEntity().getUsername() + " te ha enviado " + cantidad + " " + noun + ".", kissedSession);
                }
            }
        }
        catch (Exception ex){
            BankCommand.sendNotif("Cantidad incorrecta.", client);
        }
    }

    @Override
    public String getPermission() {
        return "bank_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username", "%username%");
    }

    @Override
    public String getDescription() {
        return Locale.get("command.bank.description");
    }
}
