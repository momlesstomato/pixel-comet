package com.cometproject.server.game.commands.user;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.stats.CometStats;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.GameCycle;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

import java.text.NumberFormat;


/**
 * Describes about command behavior for the Comet subsystem.
 */
public class AboutCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String message[]) {
        StringBuilder about = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance();

        CometStats cometStats = Comet.getStats();

        boolean aboutDetailed = client.getPlayer().getPermissions().getRank().aboutDetailed();

        about.append("<center><img src='%about_image%' /></center>\n".replace("%about_image%", CometSettings.aboutImg));
        about.append("<center>%hotelName% es un hotel creado por KeyServers. Todos los derechos reservados a sus respectivos autores.</center>\n\n".replace("%hotelName%", CometSettings.hotelName));

        if (CometSettings.aboutShowRoomsActive || CometSettings.aboutShowUptime || aboutDetailed) {
            about.append("<b>Estadísticas del hotel:</b>\n");

            if (CometSettings.aboutShowPlayersOnline || aboutDetailed)
                about.append("• Usuarios en linea - " + format.format(cometStats.getPlayers()) + "\n");

            if (CometSettings.aboutShowRoomsActive || aboutDetailed)
                about.append("• Salas activas - " + format.format(cometStats.getRooms()) + "\n");

            /*if (CometSettings.aboutShowUptime || aboutDetailed)
                about.append("— Tiempo desde el último reinicio - " + cometStats.getUptime() + "\n\n");*/
        }

        about.append("¶ Desarrolladores principales:\n");
        about.append("µ Key.\n");
        about.append("All Comet developers for all of his work, including in special Custom wich was the one who made most of this emulator.");
        about.append("Agradecimientos especiales al equipo de Comet y al equipo de Nitro, ya que el hotel usa sus bases.\n\n");
        about.append("Record de usuarios en linea: " + GameCycle.getInstance().getOnlineRecord() + "\n");

        client.send(new MotdNotificationMessageComposer(about.toString()));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "about_command";
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
        return Locale.get("command.about.description");
    }
}
