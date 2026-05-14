package com.cometproject.server.game.rooms.filter;

import com.cometproject.api.game.rooms.filter.IFilterResult;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.messages.outgoing.messenger.InstantChatMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes filter result behavior for the room subsystem.
 */
public class FilterResult implements IFilterResult {
    private boolean isBlocked;
    private boolean wasModified;
    private String message;

    /**
     * Creates a filter result instance for the room subsystem.
     *
     * @param chatMessage Chat message supplied by the caller.
     */
    public FilterResult(String chatMessage) {
        this.isBlocked = false;
        this.wasModified = false;
        this.message = chatMessage;
    }

    /**
     * Creates a filter result instance for the room subsystem.
     *
     * @param isBlocked Is blocked supplied by the caller.
     * @param chatMessage Chat message supplied by the caller.
     */
    public FilterResult(boolean isBlocked, String chatMessage) {
        this.isBlocked = isBlocked;
        this.wasModified = false;
        this.message = chatMessage;
    }

    /**
     * Creates a filter result instance for the room subsystem.
     *
     * @param chatMessage Chat message supplied by the caller.
     * @param wasModified Was modified supplied by the caller.
     */
    public FilterResult(String chatMessage, boolean wasModified) {
        this.isBlocked = false;
        this.wasModified = wasModified;
        this.message = chatMessage;
    }

    /**
     * Indicates whether blocked applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Executes send log to staffs for this room contract.
     *
     * @param client Client supplied by the caller.
     * @param where Where supplied by the caller.
     */
    public void sendLogToStaffs(Session client, String where) {
        for (Session player : ModerationManager.getInstance().getModerators()) {
            player.send(new InstantChatMessageComposer(Locale.getOrDefault("staff.chat.filter", "The user %s has triggered the filter on %b: [%c]")
                    .replace("%s", client.getPlayer().getData().getUsername())
                    .replace("%b", where)
                    .replace("%c", this.message), Integer.MIN_VALUE + 5000, "Spammer detected:", "sh-3089-92.ch-5516282-64-1336.lg-3058-1336.ha-51908423-73-1322.fa-1211-62.hd-3091-8.hr-115-1036.cc-58788570-73-73", 0));


            player.send(new TalkMessageComposer(-1, "<b>" + client.getPlayer().getData().getUsername() + "</b> acaba de saltarse el filtro diciendo: <i>" + this.message + "</i>. <b>Método:</b> " + where +".", ChatEmotion.NONE, 34));

        }
    }

    /**
     * Returns the message for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Executes was modified for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean wasModified() {
        return wasModified;
    }
}
