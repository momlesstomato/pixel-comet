package com.cometproject.api.game.groups.types.components.forum;

import com.cometproject.api.networking.messages.IComposer;

import java.util.List;

/**
 * Defines the i forum thread contract for the group subsystem.
 */
public interface IForumThread {
    /**
     * Executes the compose operation for this group contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void compose(IComposer msg);

    /**
     * Returns the replies associated with this group contract.
     *
     * @param start Start value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IForumThreadReply> getReplies(int start);

    /**
     * Returns the reply by id associated with this group contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IForumThreadReply getReplyById(int id);

    /**
     * Returns the most recent post associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IForumThreadReply getMostRecentPost();

    /**
     * Adds reply data to this group contract.
     *
     * @param reply Reply value supplied by the caller.
     */
    void addReply(IForumThreadReply reply);

    /**
     * Executes the dispose operation for this group contract.
     */
    void dispose();

    /**
     * Returns the id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Updates the id value for this group contract.
     *
     * @param id Id value supplied by the caller.
     */
    void setId(int id);

    /**
     * Returns the title associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getTitle();

    /**
     * Updates the title value for this group contract.
     *
     * @param title Title value supplied by the caller.
     */
    void setTitle(String title);

    /**
     * Returns the replies associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IForumThreadReply> getReplies();

    /**
     * Updates the replies value for this group contract.
     *
     * @param replies Replies value supplied by the caller.
     */
    void setReplies(List<IForumThreadReply> replies);

    /**
     * Returns the author id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAuthorId();

    /**
     * Returns the author timestamp associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAuthorTimestamp();

    /**
     * Indicates whether locked is enabled for this group contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isLocked();

    /**
     * Updates the is locked value for this group contract.
     *
     * @param isLocked Is locked value supplied by the caller.
     */
    void setIsLocked(boolean isLocked);

    /**
     * Returns the state associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getState();

    /**
     * Updates the state value for this group contract.
     *
     * @param state State value supplied by the caller.
     */
    void setState(int state);

    /**
     * Indicates whether pinned is enabled for this group contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isPinned();

    /**
     * Updates the is pinned value for this group contract.
     *
     * @param isPinned Is pinned value supplied by the caller.
     */
    void setIsPinned(boolean isPinned);
}
