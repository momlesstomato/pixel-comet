package com.cometproject.server.game.commands.notifications;

import com.cometproject.server.game.commands.notifications.types.Notification;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.storage.queries.system.NotificationCommandsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Manages notification runtime state for the Comet subsystem.
 */
public class NotificationManager {
    private Map<String, Notification> notifications;

    private Logger LOGGER = LoggerFactory.getLogger(NotificationManager.class);

    /**
     * Creates a notification manager instance for the Comet subsystem.
     */
    public NotificationManager() {
        this.notifications = NotificationCommandsDao.getAll();

        LOGGER.info("Loaded " + notifications.size() + " notification commands");
    }

    /**
     * Indicates whether notification executor applies to this Comet contract.
     *
     * @param text Text supplied by the caller.
     * @param rank Rank supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isNotificationExecutor(String text, int rank) {
        return this.notifications.containsKey(text.substring(1)) && this.notifications.get(text.substring(1)).getMinRank() <= rank;
    }

    /**
     * Executes execute for this Comet contract.
     *
     * @param player Player participating in the operation.
     * @param command Command supplied by the caller.
     */
    public void execute(Player player, String command) {
        Notification notification = this.notifications.get(command);

        if (notification == null)
            return;

        notification.execute(player);
    }
}
