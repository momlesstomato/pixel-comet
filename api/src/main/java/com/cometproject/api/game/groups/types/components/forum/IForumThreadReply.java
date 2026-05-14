package com.cometproject.api.game.groups.types.components.forum;

import com.cometproject.api.networking.messages.IComposer;

/**
 * Defines the i forum thread reply contract for the group subsystem.
 */
public interface IForumThreadReply {
    /**
     * Executes the compose operation for this group contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void compose(IComposer msg);

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
     * Returns the message associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMessage();

    /**
     * Updates the message value for this group contract.
     *
     * @param message Message value supplied by the caller.
     */
    void setMessage(String message);

    /**
     * Returns the author id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAuthorId();

    /**
     * Updates the author id value for this group contract.
     *
     * @param authorId Author id value supplied by the caller.
     */
    void setAuthorId(int authorId);

    /**
     * Returns the author timestamp associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAuthorTimestamp();

    /**
     * Updates the author timestamp value for this group contract.
     *
     * @param authorTimestamp Author timestamp value supplied by the caller.
     */
    void setAuthorTimestamp(int authorTimestamp);

    /**
     * Returns the thread id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getThreadId();

    /**
     * Returns the index associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getIndex();

    /**
     * Updates the index value for this group contract.
     *
     * @param index Index value supplied by the caller.
     */
    void setIndex(int index);

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
     * Returns the admin id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAdminId();

    /**
     * Updates the admin id value for this group contract.
     *
     * @param adminId Admin id value supplied by the caller.
     */
    void setAdminId(int adminId);

    /**
     * Returns the admin username associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getAdminUsername();

    /**
     * Updates the admin username value for this group contract.
     *
     * @param adminUsername Admin username value supplied by the caller.
     */
    void setAdminUsername(String adminUsername);
}
