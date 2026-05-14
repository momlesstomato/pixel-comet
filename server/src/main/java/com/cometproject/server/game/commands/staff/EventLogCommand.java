package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.logging.database.queries.LogQueries;
import com.cometproject.server.logging.entries.CommandLogEntry;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Describes event log command behavior for the Comet subsystem.
 */
public class EventLogCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {

        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        List<CommandLogEntry> events = LogQueries.getOpenEvents();

        Date d;

        for (CommandLogEntry e : events) {
            d = new Date((long)e.getTimestamp()*1000);
            DateFormat f = new SimpleDateFormat("dd/MM' a las 'HH : mm");
            list.add("- " + PlayerDao.getUsernameByPlayerId(e.getPlayerId()) + " el día " + f.format(d) + " abrió " + e.getString() + ".\n");

        }

        for (String value : list) {
            builder.append(value);
        }

        client.send(new MotdNotificationMessageComposer("Lista de eventos abiertos:\n\n" + builder.toString()));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "eventlog_command";
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
        return Locale.get("command.eventlog.description");
    }
}
