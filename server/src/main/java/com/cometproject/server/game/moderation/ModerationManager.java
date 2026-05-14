package com.cometproject.server.game.moderation;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.moderation.types.actions.ActionCategory;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.game.moderation.types.tickets.HelpTicketState;
import com.cometproject.server.game.rooms.types.components.types.ChatMessage;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.moderation.tickets.HelpTicketMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.moderation.PresetDao;
import com.cometproject.server.storage.queries.moderation.TicketDao;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manages moderation runtime state for the moderation subsystem.
 */
public class ModerationManager implements Startable {

    private List<String> userPresets;
    private List<String> roomPresets;
    private List<ActionCategory> actionCategories;

    private Map<Integer, HelpTicket> tickets;

    private ConcurrentHashSet<Session> moderators;
    private ConcurrentHashSet<Session> alfas;
    private ConcurrentHashSet<Session> police;
    private ConcurrentHashSet<Session> logChatUsers;

    private Logger LOGGER = LoggerFactory.getLogger(ModerationManager.class.getName());

    /**
     * Creates a moderation manager instance for the moderation subsystem.
     */
    public ModerationManager() {

    }

    /**
     * Returns the instance for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public static ModerationManager getInstance() {
        return CometBootstrap.resolve(ModerationManager.class);
    }

    /**
     * Starts this moderation component.
     */
    @Override
    public void start() {
        this.moderators = new ConcurrentHashSet<>();
        this.logChatUsers = new ConcurrentHashSet<>();
        this.alfas = new ConcurrentHashSet<>();
        this.police = new ConcurrentHashSet<>();

        loadPresets();
        loadActiveTickets();

        LOGGER.info("ModerationManager initialized");
    }

    /**
     * Loads presets for this moderation contract.
     */
    public void loadPresets() {
        if (userPresets == null) {
            userPresets = new ArrayList<>();
        } else {
            userPresets.clear();
        }

        if (roomPresets == null) {
            roomPresets = new ArrayList<>();
        } else {
            roomPresets.clear();
        }

        if (actionCategories == null) {
            actionCategories = new ArrayList<>();
        } else {
            for (ActionCategory actionCategory : actionCategories) {
                actionCategory.dispose();
            }

            actionCategories.clear();
        }

        try {
            PresetDao.getPresets(userPresets, roomPresets);
            PresetDao.getPresetActions(actionCategories);

            LOGGER.info("Loaded " + (this.getRoomPresets().size() + this.getUserPresets().size() + this.getActionCategories().size()) + " moderation presets");
        } catch (Exception e) {
            LOGGER.error("Error while loading moderation presets", e);
        }
    }

    /**
     * Adds moderator to this moderation contract.
     *
     * @param session Session participating in the operation.
     */
    public void addModerator(Session session) {
        this.moderators.add(session);
    }

    /**
     * Adds alfa to this moderation contract.
     *
     * @param session Session participating in the operation.
     */
    public void addAlfa(Session session) {
        this.alfas.add(session);
    }

    /**
     * Adds log chat user to this moderation contract.
     *
     * @param session Session participating in the operation.
     */
    public void addLogChatUser(Session session) {
        this.logChatUsers.add(session);
    }

    /**
     * Removes log chat user from this moderation contract.
     *
     * @param session Session participating in the operation.
     */
    public void removeLogChatUser(Session session) {
        this.logChatUsers.remove(session);
    }

    /**
     * Removes alfa chat user from this moderation contract.
     *
     * @param session Session participating in the operation.
     */
    public void removeAlfaChatUser(Session session) {
        this.alfas.remove(session);
    }

    /**
     * Removes moderator from this moderation contract.
     *
     * @param session Session participating in the operation.
     */
    public void removeModerator(Session session) {
        this.moderators.remove(session);
    }

    /**
     * Loads active tickets for this moderation contract.
     */
    public void loadActiveTickets() {
        if (tickets == null) {
            tickets = new HashMap<>();
        } else {
            tickets.clear();
        }

        try {
            this.tickets = TicketDao.getOpenTickets();
            LOGGER.info("Loaded " + this.tickets.size() + " active help tickets");
        } catch (Exception e) {
            LOGGER.error("Error while loading active tickets", e);
        }
    }

    private void addTicket(HelpTicket ticket) {
        this.tickets.put(ticket.getId(), ticket);
        this.broadcastTicket(ticket);
    }

    /**
     * Executes broadcast ticket for this moderation contract.
     *
     * @param ticket Ticket supplied by the caller.
     */
    public void broadcastTicket(final HelpTicket ticket) {
        NetworkManager.getInstance().getSessions().broadcastToModerators(new HelpTicketMessageComposer(ticket));
    }

    /**
     * Creates ticket for this moderation contract.
     *
     * @param submitterId Submitter id supplied by the caller.
     * @param message Message supplied by the caller.
     * @param category Category supplied by the caller.
     * @param reportedId Reported id supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param chatMessages Chat messages supplied by the caller.
     */
    public void createTicket(int submitterId, String message, int category, int reportedId, int timestamp, int roomId, List<ChatMessage> chatMessages) {
        int ticketId = TicketDao.createTicket(submitterId, message, category, reportedId, timestamp, roomId, chatMessages);

        final HelpTicket ticket = new HelpTicket(ticketId, category, timestamp, 0, submitterId, reportedId, 0, message, HelpTicketState.OPEN, chatMessages, roomId);
        this.addTicket(ticket);
    }

    /**
     * Returns the ticket for this moderation contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public HelpTicket getTicket(int id) {
        return this.tickets.get(id);
    }

    /**
     * Returns the ticket by user id for this moderation contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public HelpTicket getTicketByUserId(int id) {
        for (HelpTicket ticket : tickets.values()) {
            if (ticket.getSubmitterId() == id)
                return ticket;
        }

        return null;
    }

    /**
     * Returns the user presets for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public List<String> getUserPresets() {
        return this.userPresets;
    }

    /**
     * Returns the room presets for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public List<String> getRoomPresets() {
        return this.roomPresets;
    }

    /**
     * Returns the action categories for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public List<ActionCategory> getActionCategories() {
        return this.actionCategories;
    }

    /**
     * Returns the active ticket by player id for this moderation contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public HelpTicket getActiveTicketByPlayerId(int playerId) {
        HelpTicket ticket = this.getTicketByUserId(playerId);

        if (ticket != null) {
            if (ticket.getState() != HelpTicketState.CLOSED) {
                return ticket;
            }
        }

        return null;
    }

    /**
     * Returns the tickets for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, HelpTicket> getTickets() {
        return tickets;
    }


    /**
     * Returns the moderators for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public ConcurrentHashSet<Session> getModerators() {
        return moderators;
    }

    /**
     * Returns the alfas for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public ConcurrentHashSet<Session> getAlfas() {
        return alfas;
    }

    /**
     * Returns the log chat users for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public ConcurrentHashSet<Session> getLogChatUsers() {
        return logChatUsers;
    }
}
