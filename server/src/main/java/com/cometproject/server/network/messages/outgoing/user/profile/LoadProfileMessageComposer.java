package com.cometproject.server.network.messages.outgoing.user.profile;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.types.PlayerStatistics;
import com.cometproject.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Serializes the load profile message for the Pixel Protocol client.
 */
public class LoadProfileMessageComposer extends MessageComposer {
    private final PlayerData player;
    private final PlayerStatistics stats;
    private final Set<Integer> groups;
    private final boolean isMyFriend;
    private final boolean requestSent;

    /**
     * Creates a load profile message composer instance for the network message subsystem.
     *
     * @param player Player participating in the operation.
     * @param stats Stats supplied by the caller.
     * @param groups Groups supplied by the caller.
     * @param isMyFriend Is my friend supplied by the caller.
     * @param hasSentRequest Has sent request supplied by the caller.
     */
    public LoadProfileMessageComposer(PlayerData player, PlayerStatistics stats, Set<Integer> groups, boolean isMyFriend, boolean hasSentRequest) {
        this.player = player;
        this.stats = stats;
        this.groups = groups;
        this.isMyFriend = isMyFriend;
        this.requestSent = hasSentRequest;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ProfileInformationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(player.getId());
        msg.writeString(player.getUsername());
        msg.writeString(player.getFigure());
        msg.writeString(player.getMotto());

        boolean isTimestamp = false;
        int timestamp = 0;

        try {
            timestamp = Integer.parseInt(player.getRegDate());
            isTimestamp = true;
        } catch (Exception ignored) {
        }

        msg.writeString(isTimestamp ? UserObjectMessageComposer.getDate(timestamp) : player.getRegDate());
        msg.writeInt(player.getAchievementPoints());
        msg.writeInt(stats.getFriendCount());
        msg.writeBoolean(isMyFriend);
        msg.writeBoolean(requestSent);
        msg.writeBoolean(PlayerManager.getInstance().isOnline(player.getId()));

        List<IGroupData> groups = new ArrayList<>();

        if (this.groups != null) {
            for (int groupId : this.groups) {
                IGroupData group = GameContext.getCurrent().getGroupService().getData(groupId);

                if (group != null) {
                    groups.add(group);
                }
            }
        }

        msg.writeInt(groups.size());

        for (IGroupData group : groups) {
            if (group != null) {
                msg.writeInt(group.getId());
                msg.writeString(group.getTitle());
                msg.writeString(group.getBadge());
                msg.writeString(group.getColourA());
                msg.writeString(group.getColourB());
                msg.writeBoolean(player.getFavouriteGroup() == group.getId());
                msg.writeInt(-1);
                msg.writeBoolean(group.hasForum());
            }
        }

        groups.clear();

        msg.writeInt((int) Comet.getTime() - player.getLastVisit());
        msg.writeBoolean(true);
    }
}
