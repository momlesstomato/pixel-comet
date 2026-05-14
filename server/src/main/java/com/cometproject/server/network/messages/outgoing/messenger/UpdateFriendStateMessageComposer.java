package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.players.data.components.messenger.RelationshipLevel;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the update friend state message for the Pixel Protocol client.
 */
public class UpdateFriendStateMessageComposer extends MessageComposer {
    private final PlayerAvatar playerAvatar;
    private final IGroupData group;

    private final boolean online;
    private final boolean inRoom;
    private final RelationshipLevel relationshipLevel;
    private int action;
    private int friendId;

    /**
     * Creates a update friend state message composer instance for the network message subsystem.
     *
     * @param playerAvatar Player avatar supplied by the caller.
     * @param online Online supplied by the caller.
     * @param inRoom In room supplied by the caller.
     * @param level Level supplied by the caller.
     */
    public UpdateFriendStateMessageComposer(final PlayerAvatar playerAvatar, final boolean online, final boolean inRoom, final RelationshipLevel level) {
        this.playerAvatar = playerAvatar;
        this.group = null;
        this.online = online;
        this.inRoom = inRoom;
        this.relationshipLevel = level;
    }

    /**
     * Creates a update friend state message composer instance for the network message subsystem.
     *
     * @param group Group supplied by the caller.
     */
    public UpdateFriendStateMessageComposer(final IGroupData group) {
        this.playerAvatar = null;
        this.group = group;
        this.online = true;
        this.inRoom = false;
        this.relationshipLevel = null;
    }

    /**
     * Creates a update friend state message composer instance for the network message subsystem.
     *
     * @param action Action supplied by the caller.
     * @param friendId Friend id supplied by the caller.
     */
    public UpdateFriendStateMessageComposer(int action, int friendId) {
        this.playerAvatar = null;
        this.group = null;
        this.online = false;
        this.inRoom = false;
        this.action = action;
        this.friendId = friendId;
        this.relationshipLevel = null;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FriendListUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.playerAvatar == null && this.group == null) {
            msg.writeInt(0);
            msg.writeInt(1);
            msg.writeInt(this.action);
            msg.writeInt(this.friendId);

            return;
        }

        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(0);

        msg.writeInt(this.group != null ? -this.group.getId() : this.playerAvatar.getId());
        msg.writeString(this.group != null ? this.group.getTitle() : this.playerAvatar.getUsername());
        msg.writeInt(1);
        msg.writeBoolean(online);
        msg.writeBoolean(inRoom);
        msg.writeString(this.group != null ? this.group.getBadge() : this.playerAvatar.getFigure());
        msg.writeInt(this.group != null ? 1 : 0);
        msg.writeString(this.group != null ? "" : this.playerAvatar.getMotto());
        msg.writeString(""); // facebook name ?
        msg.writeString("");
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeShort(this.relationshipLevel != null ? relationshipLevel.getLevelId() : 0);
    }
}
