package com.cometproject.server.game.commands.notifications.types;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes notification behavior for the Comet subsystem.
 */
public class Notification {
    private String trigger;
    private String text;
    private NotificationType type;
    private int minRank;
    private int coolDown;

    /**
     * Creates a notification instance for the Comet subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public Notification(ResultSet data) throws SQLException {
        this.trigger = data.getString("name");
        this.text = data.getString("text");
        this.type = NotificationType.valueOf(data.getString("type").toUpperCase());
        this.minRank = data.getInt("min_rank");
        this.coolDown = data.getInt("cooldown");
    }

    /**
     * Executes execute for this Comet contract.
     *
     * @param player Player participating in the operation.
     */
    public void execute(Player player) {
        if ((player.getNotifCooldown() + coolDown) >= Comet.getTime()) {
            return;
        }

        switch (this.type) {
            case GLOBAL:
                NetworkManager.getInstance().getSessions().broadcast(new AdvancedAlertMessageComposer(this.text + "\n\n-" + player.getData().getUsername()));
                break;

            case LOCAL:
                player.getSession().send(new AdvancedAlertMessageComposer(this.text));
                break;
        }

        player.setNotifCooldown((int) Comet.getTime());
    }

    /**
     * Returns the trigger for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTrigger() {
        return trigger;
    }

    /**
     * Returns the text for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * Returns the min rank for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMinRank() {
        return minRank;
    }

    /**
     * Returns the cool down for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCoolDown() {
        return coolDown;
    }
}
