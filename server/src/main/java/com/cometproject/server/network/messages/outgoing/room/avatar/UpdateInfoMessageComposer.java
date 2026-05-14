package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the update info message for the Pixel Protocol client.
 */
public class UpdateInfoMessageComposer extends MessageComposer {
    private final int playerId;
    private final String figure;
    private final String gender;
    private final String motto;
    private final int achievementPoints;

    /**
     * Creates a update info message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param figure Figure supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param motto Motto supplied by the caller.
     * @param achievementPoints Achievement points supplied by the caller.
     */
    public UpdateInfoMessageComposer(final int playerId, final String figure, final String gender, final String motto, final int achievementPoints) {
        this.playerId = playerId;
        this.figure = figure;
        this.gender = gender;
        this.motto = motto;
        this.achievementPoints = achievementPoints;
    }

    /**
     * Creates a update info message composer instance for the network message subsystem.
     *
     * @param entity Entity supplied by the caller.
     */
    public UpdateInfoMessageComposer(RoomEntity entity) {
        this(entity.getId(), entity.getFigure(), entity.getGender(), entity.getMotto(), (entity instanceof PlayerEntity) ? ((PlayerEntity) entity).getPlayer().getData().getAchievementPoints() : 0);
    }

    /**
     * Creates a update info message composer instance for the network message subsystem.
     *
     * @param id Id supplied by the caller.
     * @param entity Entity supplied by the caller.
     */
    public UpdateInfoMessageComposer(int id, RoomEntity entity) {
        this(id, entity.getFigure(), entity.getGender(), entity.getMotto(), (entity instanceof PlayerEntity) ? ((PlayerEntity) entity).getPlayer().getData().getAchievementPoints() : 0);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserChangeMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeString(figure);
        msg.writeString(gender.toLowerCase());
        msg.writeString(motto);
        msg.writeInt(achievementPoints);
    }
}
