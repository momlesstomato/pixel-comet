package com.cometproject.server.game.moderation.types.tickets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.types.components.types.ChatMessage;
import com.cometproject.server.storage.queries.moderation.TicketDao;
import com.cometproject.server.storage.queries.player.PlayerDao;

import java.util.List;


/**
 * Describes help ticket behavior for the moderation subsystem.
 */
public class HelpTicket {

    private int id;
    private int categoryId;
    private int roomId;

    private int dateSubmitted;
    private int dateClosed;

    private int submitterId;
    private int reportedId;
    private int moderatorId;

    private String message;

    private String submitterUsername;
    private String reportedUsername;
    private String moderatorUsername;

    private HelpTicketState state;
    private List<ChatMessage> chatMessages;

    /**
     * Creates a help ticket instance for the moderation subsystem.
     *
     * @param id Id supplied by the caller.
     * @param categoryId Category id supplied by the caller.
     * @param dateSubmitted Date submitted supplied by the caller.
     * @param dateClosed Date closed supplied by the caller.
     * @param submitterId Submitter id supplied by the caller.
     * @param reportedId Reported id supplied by the caller.
     * @param moderatorId Moderator id supplied by the caller.
     * @param message Message supplied by the caller.
     * @param state State supplied by the caller.
     * @param chatMessages Chat messages supplied by the caller.
     * @param roomId Room identifier used by the operation.
     */
    public HelpTicket(int id, int categoryId, int dateSubmitted, int dateClosed, int submitterId, int reportedId, int moderatorId, String message, HelpTicketState state, List<ChatMessage> chatMessages, int roomId) {
        this.id = id;
        this.categoryId = categoryId;
        this.dateSubmitted = dateSubmitted;
        this.dateClosed = dateClosed;
        this.submitterId = submitterId;
        this.reportedId = reportedId;
        this.moderatorId = moderatorId;
        this.message = message;
        this.state = state;
        this.chatMessages = chatMessages;
        this.roomId = roomId;
    }

    /**
     * Executes save for this moderation contract.
     */
    public void save() {
        // Queue to be saved ??

        TicketDao.saveTicket(this);
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeInt(this.getId());
        msg.writeInt(this.getState().getTabId());
        msg.writeInt(3); // style
        msg.writeInt(this.getCategoryId());
        msg.writeInt((int) (Comet.getTime() - this.getDateSubmitted()) * 1000);
        msg.writeInt(1); // Priority.
        msg.writeInt(0);
        msg.writeInt(this.getSubmitterId());
        msg.writeString(this.getSubmitterUsername());
        msg.writeInt(this.getReportedId());
        msg.writeString(this.getReportedUsername());
        msg.writeInt(this.getModeratorId());
        msg.writeString(this.getModeratorId() != 0 ? this.getModeratorUsername() : "");
        msg.writeString(this.getMessage());
        msg.writeInt(0); // Public room?
        msg.writeInt(this.getChatMessages().size());

        for (ChatMessage chatMessage : this.getChatMessages()) {
            msg.writeString(chatMessage.getMessage());
            msg.writeInt(-1);
            msg.writeInt(-1);
        }
    }

    /**
     * Returns the id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the id for this moderation contract.
     *
     * @param id Id supplied by the caller.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the date submitted for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDateSubmitted() {
        return dateSubmitted;
    }

    /**
     * Updates the date submitted for this moderation contract.
     *
     * @param dateSubmitted Date submitted supplied by the caller.
     */
    public void setDateSubmitted(int dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    /**
     * Returns the date closed for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDateClosed() {
        return dateClosed;
    }

    /**
     * Updates the date closed for this moderation contract.
     *
     * @param dateClosed Date closed supplied by the caller.
     */
    public void setDateClosed(int dateClosed) {
        this.dateClosed = dateClosed;
    }

    /**
     * Returns the submitter id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSubmitterId() {
        return submitterId;
    }

    /**
     * Updates the submitter id for this moderation contract.
     *
     * @param submitterId Submitter id supplied by the caller.
     */
    public void setSubmitterId(int submitterId) {
        this.submitterId = submitterId;
    }

    /**
     * Returns the reported id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getReportedId() {
        return reportedId;
    }

    /**
     * Updates the reported id for this moderation contract.
     *
     * @param reportedId Reported id supplied by the caller.
     */
    public void setReportedId(int reportedId) {
        this.reportedId = reportedId;
    }

    /**
     * Returns the moderator id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getModeratorId() {
        return moderatorId;
    }

    /**
     * Updates the moderator id for this moderation contract.
     *
     * @param moderatorId Moderator id supplied by the caller.
     */
    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }

    /**
     * Returns the message for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Updates the message for this moderation contract.
     *
     * @param message Message supplied by the caller.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the state for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public HelpTicketState getState() {
        return state;
    }

    /**
     * Updates the state for this moderation contract.
     *
     * @param state State supplied by the caller.
     */
    public void setState(HelpTicketState state) {
        this.state = state;
    }

    /**
     * Returns the chat messages for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    /**
     * Updates the chat messages for this moderation contract.
     *
     * @param chatMessages Chat messages supplied by the caller.
     */
    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    /**
     * Returns the category id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Updates the category id for this moderation contract.
     *
     * @param categoryId Category id supplied by the caller.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Returns the room id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Updates the room id for this moderation contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the submitter username for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getSubmitterUsername() {
        if (this.submitterUsername == null) {
            this.submitterUsername = PlayerDao.getUsernameByPlayerId(this.getSubmitterId());
        }

        return submitterUsername;
    }

    /**
     * Returns the reported username for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getReportedUsername() {
        if (this.reportedUsername == null) {
            this.reportedUsername = PlayerDao.getUsernameByPlayerId(this.getReportedId());
        }

        return reportedUsername;
    }

    /**
     * Returns the moderator username for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getModeratorUsername() {
        if (this.moderatorUsername == null) {
            this.moderatorUsername = PlayerDao.getUsernameByPlayerId(this.getModeratorId());
        }

        return moderatorUsername;
    }
}
