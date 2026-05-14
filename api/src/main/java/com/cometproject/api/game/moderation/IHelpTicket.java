package com.cometproject.api.game.moderation;

import com.cometproject.api.game.rooms.chat.IChatMessage;
import com.cometproject.api.networking.messages.IComposer;

import java.util.List;

/**
 * Defines the i help ticket contract for the Comet subsystem.
 */
public interface IHelpTicket {
    /**
     * Executes the save operation for this moderation contract.
     */
    void save();

    /**
     * Executes the compose operation for this moderation contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void compose(IComposer msg);

    /**
     * Returns the id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Updates the id value for this moderation contract.
     *
     * @param id Id value supplied by the caller.
     */
    void setId(int id);

    /**
     * Returns the date submitted associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDateSubmitted();

    /**
     * Updates the date submitted value for this moderation contract.
     *
     * @param dateSubmitted Date submitted value supplied by the caller.
     */
    void setDateSubmitted(int dateSubmitted);

    /**
     * Returns the date closed associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDateClosed();

    /**
     * Updates the date closed value for this moderation contract.
     *
     * @param dateClosed Date closed value supplied by the caller.
     */
    void setDateClosed(int dateClosed);

    /**
     * Returns the submitter id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSubmitterId();

    /**
     * Updates the submitter id value for this moderation contract.
     *
     * @param submitterId Submitter id value supplied by the caller.
     */
    void setSubmitterId(int submitterId);

    /**
     * Returns the reported id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getReportedId();

    /**
     * Updates the reported id value for this moderation contract.
     *
     * @param reportedId Reported id value supplied by the caller.
     */
    void setReportedId(int reportedId);

    /**
     * Returns the moderator id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getModeratorId();

    /**
     * Updates the moderator id value for this moderation contract.
     *
     * @param moderatorId Moderator id value supplied by the caller.
     */
    void setModeratorId(int moderatorId);

    /**
     * Returns the message associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMessage();

    /**
     * Updates the message value for this moderation contract.
     *
     * @param message Message value supplied by the caller.
     */
    void setMessage(String message);

    /**
     * Returns the state associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    HelpTicketState getState();

    /**
     * Updates the state value for this moderation contract.
     *
     * @param state State value supplied by the caller.
     */
    void setState(HelpTicketState state);

    /**
     * Returns the chat messages associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IChatMessage> getChatMessages();

    /**
     * Updates the chat messages value for this moderation contract.
     *
     * @param chatMessages Chat messages value supplied by the caller.
     */
    void setChatMessages(List<IChatMessage> chatMessages);

    /**
     * Returns the category id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCategoryId();

    /**
     * Updates the category id value for this moderation contract.
     *
     * @param categoryId Category id value supplied by the caller.
     */
    void setCategoryId(int categoryId);

    /**
     * Returns the room id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRoomId();

    /**
     * Updates the room id value for this moderation contract.
     *
     * @param roomId Room id value supplied by the caller.
     */
    void setRoomId(int roomId);

    /**
     * Returns the submitter username associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getSubmitterUsername();

    /**
     * Returns the reported username associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getReportedUsername();

    /**
     * Returns the moderator username associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getModeratorUsername();
}
