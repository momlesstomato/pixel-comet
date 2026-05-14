package com.cometproject.server.network.messages.types.tasks;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.tasks.CometTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes message event task behavior for the network message subsystem.
 */
public class MessageEventTask implements CometTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEventTask.class);

    private final Event messageEvent;
    private final Session session;
    private final MessageEvent messageEventData;

    private final long start;

    /**
     * Creates a message event task instance for the network message subsystem.
     *
     * @param messageEvent Message event supplied by the caller.
     * @param session Session participating in the operation.
     * @param messageEventData Message event data supplied by the caller.
     */
    public MessageEventTask(Event messageEvent, Session session, MessageEvent messageEventData) {
        this.messageEvent = messageEvent;
        this.session = session;
        this.messageEventData = messageEventData;

        this.start = System.currentTimeMillis();
    }

    /**
     * Runs this network message task.
     */
    @Override
    public void run() {
        try {
            LOGGER.debug("Started packet process for packet: [" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "]");
            this.messageEvent.handle(this.session, this.messageEventData);

            long timeTakenSinceCreation = ((System.currentTimeMillis() - this.start));

            // If the packet took more than 750ms to be handled, red flag!
            if (timeTakenSinceCreation >= 100) {
                if (session.getPlayer() != null && session.getPlayer().getData() != null)
                    LOGGER.trace("[" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "][" + session.getPlayer().getId() + "][" + session.getPlayer().getData().getUsername() + "] Packet took " + timeTakenSinceCreation + "ms to execute");
                else
                    LOGGER.trace("[" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "] Packet took " + timeTakenSinceCreation + "ms to execute");
            }
            LOGGER.debug("Finished packet process for packet: [" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "] in " + timeTakenSinceCreation + "ms");

        } catch (Exception e) {
            if (this.session.getLogger() != null)
                session.getLogger().error("Error while handling event: " + this.messageEvent.getClass().getSimpleName(), e);
            else
                LOGGER.error("Error while handling event: " + this.messageEvent.getClass().getSimpleName(), e);
        }
    }
}
