package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes staff info command behavior for the Comet subsystem.
 */
public class StaffInfoCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] message) {
        final StringBuilder staffinfo = new StringBuilder("Current online staffs:\r\r");

        for (Session player : ModerationManager.getInstance().getModerators()) {
            staffinfo.append("* Username: ")
                    .append(player.getPlayer().getData().getUsername())
                    .append(" / Room: ")
                    .append(player.getPlayer().getEntity() != null
                            && player.getPlayer().getEntity().getRoom() != null
                            ? player.getPlayer().getEntity().getRoom().getData().getName() : "none")
                    .append(" / Rank: ")
                    .append(player.getPlayer().getData().getRank())
                    .append("\r");
        }

        final MotdNotificationMessageComposer msg = new MotdNotificationMessageComposer(staffinfo.toString());

        client.send(msg);
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
        return "staffinfo_command";
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
        return Locale.get("command.staffinfo.description");
    }
}