package com.cometproject.storage.mysql.models;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.components.forum.IForumThreadReply;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IComposer;

/**
 * Carries group forum thread message data data for the MySQL storage subsystem.
 */
public class GroupForumThreadMessageData implements IForumThreadReply {
    private int id;
    private int index;

    private String message;
    private int threadId;
    private int authorId;
    private int authorTimestamp;

    private int state;

    private int adminId;
    private String adminUsername;

    /**
     * Creates a group forum thread message data instance for the MySQL storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param index Index supplied by the caller.
     * @param message Message supplied by the caller.
     * @param threadId Thread id supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param authorTimestamp Author timestamp supplied by the caller.
     * @param state State supplied by the caller.
     * @param adminId Admin id supplied by the caller.
     * @param adminUsername Admin username supplied by the caller.
     */
    public GroupForumThreadMessageData(int id, int index, String message, int threadId, int authorId, int authorTimestamp, int state, int adminId, String adminUsername) {
        this.id = id;
        this.index = index;
        this.message = message;
        this.threadId = threadId;
        this.authorId = authorId;
        this.authorTimestamp = authorTimestamp;
        this.state = state;
        this.adminId = adminId;
        this.adminUsername = adminUsername;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        final PlayerAvatar playerAvatar = GameContext.getCurrent().getPlayerService().getAvatarByPlayerId(this.getAuthorId(),
                PlayerAvatar.USERNAME_FIGURE);

        msg.writeInt(this.getId());
        msg.writeInt(this.index);

        msg.writeInt(this.getAuthorId());
        msg.writeString(playerAvatar.getUsername());
        msg.writeString(playerAvatar.getFigure());

        msg.writeInt((int) (System.currentTimeMillis() / 1000l) - this.getAuthorTimestamp());
        msg.writeString(this.getMessage());
        msg.writeByte(this.getState()); // state

        msg.writeInt(this.adminId); // _adminId
        msg.writeString(this.adminUsername); // _adminName
        msg.writeInt(0); // _adminOperationTimeAsSeccondsAgo
//        msg.writeInt(GroupForumThreadDao.getPlayerMessageCount(playerAvatar.getId())); // messages by author todo: optimise if needed
        msg.writeInt(0);
    }

    /**
     * Returns the id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Updates the id for this MySQL storage contract.
     *
     * @param id Id supplied by the caller.
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the message for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Updates the message for this MySQL storage contract.
     *
     * @param message Message supplied by the caller.
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the author id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Updates the author id for this MySQL storage contract.
     *
     * @param authorId Author id supplied by the caller.
     */
    @Override
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Returns the author timestamp for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getAuthorTimestamp() {
        return authorTimestamp;
    }

    /**
     * Updates the author timestamp for this MySQL storage contract.
     *
     * @param authorTimestamp Author timestamp supplied by the caller.
     */
    @Override
    public void setAuthorTimestamp(int authorTimestamp) {
        this.authorTimestamp = authorTimestamp;
    }

    /**
     * Returns the thread id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getThreadId() {
        return threadId;
    }

    /**
     * Returns the index for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * Updates the index for this MySQL storage contract.
     *
     * @param index Index supplied by the caller.
     */
    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the state for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getState() {
        return state;
    }

    /**
     * Updates the state for this MySQL storage contract.
     *
     * @param state State supplied by the caller.
     */
    @Override
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Returns the admin id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getAdminId() {
        return adminId;
    }

    /**
     * Updates the admin id for this MySQL storage contract.
     *
     * @param adminId Admin id supplied by the caller.
     */
    @Override
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /**
     * Returns the admin username for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getAdminUsername() {
        return adminUsername;
    }

    /**
     * Updates the admin username for this MySQL storage contract.
     *
     * @param adminUsername Admin username supplied by the caller.
     */
    @Override
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
}
