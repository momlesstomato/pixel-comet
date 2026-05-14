package com.cometproject.server.game.commands;

import com.cometproject.server.config.Locale;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes chat command behavior for the Comet subsystem.
 */
public abstract class ChatCommand {
    /**
     * Executes send notif for this Comet contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param session Session participating in the operation.
     */
    public static void sendNotif(String msg, Session session) {
        session.send(new NotificationMessageComposer("generic", msg));
    }

    /**
     * Executes send bubble for this Comet contract.
     *
     * @param image Image supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param session Session participating in the operation.
     */
    public static void sendBubble(String image, String msg, Session session) {
        session.send(new NotificationMessageComposer(image, msg));
    }

    /**
     * Executes send alert for this Comet contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param session Session participating in the operation.
     */
    public static void sendAlert(String msg, Session session) {
        session.send(new AlertMessageComposer(msg));
    }

    /**
     * Executes send whisper for this Comet contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param session Session participating in the operation.
     */
    public static void sendWhisper(String msg, Session session) {
        session.send(new WhisperMessageComposer(session.getPlayer().getEntity().getId(), msg));
    }

    /**
     * Indicates whether executed applies to this Comet contract.
     *
     * @param session Session participating in the operation.
     */
    public static void isExecuted(Session session) {
        session.send(new NotificationMessageComposer("up", Locale.getOrDefault("command.executed", "Command is executed succesfully.")));
    }

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    public abstract void execute(Session client, String[] params);

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract String getPermission();

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract String getParameter();

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract String getDescription();

    /**
     * Executes merge for this Comet contract.
     *
     * @param params Params supplied by the caller.
     * @return Result produced by the operation.
     */
    public final String merge(String[] params) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (String s : params) {
            if (!params[params.length - 1].equals(s))
                stringBuilder.append(s).append(" ");
            else
                stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }

    /**
     * Executes merge for this Comet contract.
     *
     * @param params Params supplied by the caller.
     * @param begin Begin supplied by the caller.
     * @return Result produced by the operation.
     */
    public String merge(String[] params, int begin) {
        final StringBuilder mergedParams = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            if (i >= begin) {
                mergedParams.append(params[i]).append(" ");
            }
        }

        return mergedParams.toString();
    }
    
    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getLoggableDescription() { return getDescription(); }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean Loggable() { return false; }

    /**
     * Indicates whether hidden applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isHidden() {
        return false;
    }

    /**
     * Indicates whether this Comet contract can disable.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canDisable() {
        return false;
    }

    /**
     * Indicates whether async applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isAsync() {
        return false;
    }

    /**
     * Executes bypass filter for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean bypassFilter() {
        return false;
    }

    /**
     * Describes execution behavior for the Comet subsystem.
     */
    public static class Execution implements Runnable {
        private ChatCommand command;
        private String[] params;
        private Session session;

        /**
         * Executes execution for this Comet contract.
         *
         * @param command Command supplied by the caller.
         * @param params Params supplied by the caller.
         * @param session Session participating in the operation.
         */
        public Execution(ChatCommand command, String[] params, Session session) {
            this.command = command;
            this.params = params;
            this.session = session;
        }

        /**
         * Runs this Comet task.
         */
        @Override
        public void run() {
            command.execute(session, params);
        }
    }
}
