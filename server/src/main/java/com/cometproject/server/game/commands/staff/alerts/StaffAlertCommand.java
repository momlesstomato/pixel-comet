package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes staff alert command behavior for the Comet subsystem.
 */
public class StaffAlertCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] message) {
        final MotdNotificationMessageComposer msg = new MotdNotificationMessageComposer(Locale.getOrDefault("message.staffalert", "Staff Alert: ") + " " + this.merge(message) + "\n\n- " + client.getPlayer().getData().getUsername());
        if (message.length == 0) {
            return;
        }

        for (Session player : ModerationManager.getInstance().getModerators()) {
            if(player.getPlayer().getEntity() != null){
                player.send(new TalkMessageComposer(-1, "<b>" + client.getPlayer().getData().getUsername() + "</b>: " + this.merge(message), ChatEmotion.NONE, 34));
            } else
            player.send(msg);
        }

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
        return "staffalert_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.message", "%message%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.staffalert.description");
    }
}