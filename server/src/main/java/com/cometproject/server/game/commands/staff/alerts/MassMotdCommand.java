package com.cometproject.server.game.commands.staff.alerts;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes mass motd command behavior for the Comet subsystem.
 */
public class MassMotdCommand extends ChatCommand {
    private String logDesc;

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] message) {
        final MotdNotificationMessageComposer msg = new MotdNotificationMessageComposer(this.merge(message) + "\n\n- " + client.getPlayer().getData().getUsername());

        NetworkManager.getInstance().getSessions().broadcast(msg);

        this.logDesc = "El Staff <b>-s</b> ha mandado una alerta <i>MOTD</i> a todo el hotel. [<b>-c</b>]"
                .replace("-s", client.getPlayer().getData().getUsername())
                .replace("-c", this.merge(message));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "massmotd_command";
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
        return Locale.get("command.massmotd.description");
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
