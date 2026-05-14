package com.cometproject.storage.mysql.models;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.game.groups.types.components.forum.IForumThreadReply;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IComposer;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Carries group forum thread data data for the MySQL storage subsystem.
 */
public class GroupForumThreadData implements IForumThread {
    private static final int MAX_MESSAGES_PER_PAGE = 20;

    private int id;
    private String title;
    private int authorId;
    private int authorTimestamp;
    private int state;
    private boolean isLocked;
    private boolean isPinned;

    private int adminId;
    private String adminUsername;

    private List<IForumThreadReply> replies;

    /**
     * Creates a group forum thread data instance for the MySQL storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param title Title supplied by the caller.
     * @param message Message supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param authorTimestamp Author timestamp supplied by the caller.
     * @param state State supplied by the caller.
     * @param isLocked Is locked supplied by the caller.
     * @param isPinned Is pinned supplied by the caller.
     * @param adminId Admin id supplied by the caller.
     * @param adminUsername Admin username supplied by the caller.
     */
    public GroupForumThreadData(int id, String title, String message, int authorId, int authorTimestamp, int state, boolean isLocked, boolean isPinned, int adminId, String adminUsername) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorTimestamp = authorTimestamp;
        this.state = state;
        this.isLocked = isLocked;
        this.isPinned = isPinned;
        this.adminId = adminId;
        this.adminUsername = adminUsername;

        this.replies = new ArrayList<>();

        // Add the OP.
        this.replies.add(new GroupForumThreadMessageData(id, 0, message, this.id, authorId, authorTimestamp, 1, this.adminId, this.adminUsername));
    }

    /**
     * Creates a group forum thread data instance for the MySQL storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param title Title supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param authorTimestamp Author timestamp supplied by the caller.
     * @param state State supplied by the caller.
     * @param isLocked Is locked supplied by the caller.
     * @param isPinned Is pinned supplied by the caller.
     * @param replies Replies supplied by the caller.
     * @param adminId Admin id supplied by the caller.
     * @param adminUsername Admin username supplied by the caller.
     */
    public GroupForumThreadData(int id, String title, int authorId, int authorTimestamp, int state, boolean isLocked, boolean isPinned, List<IForumThreadReply> replies, int adminId, String adminUsername) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorTimestamp = authorTimestamp;
        this.state = state;
        this.isLocked = isLocked;
        this.isPinned = isPinned;
        this.replies = replies;
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
        msg.writeInt(this.getId());

        final PlayerAvatar authorAvatar = GameContext.getCurrent().getPlayerService().getAvatarByPlayerId(this.getAuthorId(), PlayerAvatar.USERNAME_FIGURE);

        msg.writeInt(authorAvatar == null ? 0 : authorAvatar.getId());
        msg.writeString(authorAvatar == null ? "Unknown Player" : authorAvatar.getUsername());

        msg.writeString(this.getTitle());
        msg.writeBoolean(this.isPinned());
        msg.writeBoolean(this.isLocked());
        msg.writeInt((int) (System.currentTimeMillis() / 1000) - this.getAuthorTimestamp());
        msg.writeInt(this.getReplies().size());
        msg.writeInt(0); // unread messages
        msg.writeInt(this.getMostRecentPost().getId());

        final PlayerAvatar replyAuthor = GameContext.getCurrent().getPlayerService().getAvatarByPlayerId(this.getMostRecentPost().getAuthorId(), PlayerAvatar.USERNAME_FIGURE);

        msg.writeInt(replyAuthor == null ? 0 : replyAuthor.getId());
        msg.writeString(replyAuthor == null ? "Unknown Player" : replyAuthor.getUsername());
        msg.writeInt((int) (System.currentTimeMillis() / 1000) - this.getMostRecentPost().getAuthorTimestamp());
        msg.writeByte(this.getState());
        msg.writeInt(this.adminId); //admin id
        msg.writeString(this.adminUsername); // admin username
        msg.writeInt(0); // admin action time ago.
    }

    /**
     * Returns the replies for this MySQL storage contract.
     *
     * @param start Start supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public List<IForumThreadReply> getReplies(int start) {
        List<IForumThreadReply> replies = Lists.newArrayList();

        for (int i = start; replies.size() < MAX_MESSAGES_PER_PAGE; i++) {
            if (i >= this.replies.size())
                break;

            replies.add(this.replies.get(i));
        }

        return replies;
    }

    /**
     * Returns the reply by id for this MySQL storage contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IForumThreadReply getReplyById(final int id) {
        for (IForumThreadReply reply : this.replies) {
            if (reply.getId() == id) {
                return reply;
            }
        }

        return null;
    }

    /**
     * Returns the most recent post for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IForumThreadReply getMostRecentPost() {
        return this.replies.get(this.replies.size() - 1);
    }

    /**
     * Adds reply to this MySQL storage contract.
     *
     * @param reply Reply supplied by the caller.
     */
    @Override
    public void addReply(IForumThreadReply reply) {
        this.replies.add(reply);
    }

    /**
     * Releases resources owned by this MySQL storage component.
     */
    @Override
    public void dispose() {
        this.replies.clear();
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
     * Returns the title for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title for this MySQL storage contract.
     *
     * @param title Title supplied by the caller.
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the replies for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<IForumThreadReply> getReplies() {
        return replies;
    }

    /**
     * Updates the replies for this MySQL storage contract.
     *
     * @param replies Replies supplied by the caller.
     */
    @Override
    public void setReplies(List<IForumThreadReply> replies) {
        this.replies = replies;
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
     * Returns the author timestamp for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getAuthorTimestamp() {
        return authorTimestamp;
    }

    /**
     * Indicates whether locked applies to this MySQL storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Updates the is locked for this MySQL storage contract.
     *
     * @param isLocked Is locked supplied by the caller.
     */
    @Override
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
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
     * Indicates whether pinned applies to this MySQL storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isPinned() {
        return isPinned;
    }

    /**
     * Updates the is pinned for this MySQL storage contract.
     *
     * @param isPinned Is pinned supplied by the caller.
     */
    @Override
    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

}
