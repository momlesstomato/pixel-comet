package com.cometproject.server.game.commands.user;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.highscore.HighscoreClassicFloorItem;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import java.util.List;

/**
 * Describes clear highscore command behavior for the Comet subsystem.
 */
public class ClearHighscoreCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String message[]) {
        if(client == null)
            return;

        if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId()) &&
                !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            sendNotif(Locale.getOrDefault("command.need.rights", "You need rights to use this command in this room!"), client);
            return;
        }

        final List<HighscoreClassicFloorItem> scoreboards = client.getPlayer().getEntity().getRoom().getItems().getByClass(HighscoreClassicFloorItem.class);

        if (scoreboards.size() != 0) {
                for (HighscoreClassicFloorItem scoreboard : scoreboards) {
                    scoreboard.resetScoreboard();
                }
        }

        client.send(new NotificationMessageComposer("highscore", "Has reiniciado correctamente la clasificación de la sala."));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "highscore_command";
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
        return Locale.get("command.highscore.description");
    }
}