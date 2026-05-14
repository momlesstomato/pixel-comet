package com.cometproject.server.game.commands.staff;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.permissions.PermissionsManager;
import com.cometproject.server.game.permissions.types.Rank;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;

/**
 * Describes player info command behavior for the Comet subsystem.
 */
public class PlayerInfoCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) return;

        final String username = params[0];
        Session session = NetworkManager.getInstance().getSessions().getByPlayerUsername(username);

        PlayerData playerData;

        if (session == null || session.getPlayer() == null || session.getPlayer().getData() == null || session.getPlayer().getSettings() == null) {
            playerData = PlayerDao.getDataByUsername(username);
        } else {
            playerData = session.getPlayer().getData();
        }

        if (playerData == null) return;

        final Rank playerRank = PermissionsManager.getInstance().getRank(playerData.getRank());

        if (playerRank.modTool() && !client.getPlayer().getPermissions().getRank().modTool()) {
            // send player info failed alert
            client.send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.playerinfo.title", "Información de") + ": " + username, Locale.getOrDefault("command.playerinfo.staff", "You cannot view the information of a staff member!")));
            return;
        }

        final StringBuilder userInfo = new StringBuilder();

        if (client.getPlayer().getPermissions().getRank().modTool()) {
            userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.id", "ID") + "</b>: " + playerData.getId() + "<br>");
        }

        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.username", "Username") + "</b>: " + playerData.getUsername() + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.motto", "Misión") + "</b>: " + playerData.getMotto() + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.gender", "Género") + "</b>: " + (playerData.getGender().toLowerCase().equals("m") ? Locale.getOrDefault("command.playerinfo.male", "Male") : Locale.getOrDefault("command.playerinfo.female", "Female")) + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.status", "Conectado") + "</b>: " + (session == null ? Locale.getOrDefault("command.playerinfo.offline", "Offline") : Locale.getOrDefault("command.playerinfo.online", "Online")) + "<br>");
        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.achievementPoints", "Puntos de Recompensa") + "</b>: " + playerData.getAchievementPoints() + "<br>");
        //userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.karma", "Karma") + "</b>: " + playerData.getKarma() + "<br>");
        //userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.prestige", "Karma Prestige") + "</b>: " + playerData.getPrestige() + "<br>");


        if (client.getPlayer().getPermissions().getRank().modTool()) {
            userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.rank", "Rank") + "</b>: " + playerData.getRank() + "<br>");
        }

        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.currencyBalances", "Monedas del usuario") + "</b><br>");
        userInfo.append("<i>" + playerData.getCredits() + " <b><font color='#E79D1C'>" + Locale.getOrDefault("command.playerinfo.koins", "créditos") + "</font></b></i><br>");

        final ICurrencyService currencyService = CometBootstrap.resolve(ICurrencyService.class);
        for (ICurrencyDefinition definition : currencyService.definitionsForRank(playerData.getRank())) {
            if (definition.isCredits() || !definition.isEnabled()) {
                continue;
            }

            userInfo.append("<i>")
                    .append(playerData.getCurrencyBalance(definition.getCode()))
                    .append(" <b><font color='#28BCD4'>")
                    .append(displayName(definition))
                    .append("</font></b></i><br>");
        }

        userInfo.append("<br>");


        userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.roomInfo", "Información de la sala:") + "</b><br>");

        if (session != null && session.getPlayer().getEntity() != null) {
            userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.roomId", "ID de la sala:") + "</b>: " + session.getPlayer().getEntity().getRoom().getData().getId() + "<br>");
            userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.roomName", "Nombre de la sala:") + "</b>: " + session.getPlayer().getEntity().getRoom().getData().getName() + "<br>");
            userInfo.append("<b>" + Locale.getOrDefault("command.playerinfo.roomOwner", "Anfitrión:") + "</b>: " + session.getPlayer().getEntity().getRoom().getData().getOwner() + "<br>");
        } else {
            if (session == null)
                userInfo.append("<i>" + Locale.getOrDefault("command.playerinfo.notOnline", "¡No está en línea!") + "</i>");
            else
                userInfo.append("<i>" + Locale.getOrDefault("command.playerinfo.notInRoom", "¡No está en ninguna sala!") + "</i>");
        }

        client.send(new AdvancedAlertMessageComposer(Locale.getOrDefault("command.playerinfo.title", "Información de") + ": " + username, userInfo.toString(), "usr/body/" + playerData.getUsername()));
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
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "playerinfo_command";
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
        return Locale.get("command.playerinfo.description");
    }

    private static String displayName(final ICurrencyDefinition definition) {
        return definition.getNounPlural() == null || definition.getNounPlural().isBlank()
                ? definition.getDisplayName()
                : definition.getNounPlural();
    }
}
