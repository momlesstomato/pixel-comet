package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Lists;

import java.util.List;


/**
 * Serializes the avatars message for the Pixel Protocol client.
 */
public class AvatarsMessageComposer extends MessageComposer {
    private final RoomEntity singleEntity;
    private final List<RoomEntity> entities;

    /**
     * Creates a avatars message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     */
    public AvatarsMessageComposer(final Room room) {
        this.entities = Lists.newArrayList();

        for (RoomEntity entity : room.getEntities().getAllEntities().values()) {
            if(entity.isTransformed())
                continue;

            if (entity.isVisible()) {
                if (entity instanceof PlayerEntity) {
                    if (((PlayerEntity) entity).getPlayer() == null) continue;
                }

                this.entities.add(entity);
            }
        }

        this.singleEntity = null;
    }

    /**
     * Creates a avatars message composer instance for the network message subsystem.
     *
     * @param entity Entity supplied by the caller.
     */
    public AvatarsMessageComposer(RoomEntity entity) {
        this.singleEntity = entity;
        this.entities = null;
    }

    /**
     * Creates a avatars message composer instance for the network message subsystem.
     *
     * @param entities Entities supplied by the caller.
     */
    public AvatarsMessageComposer(List<RoomEntity> entities) {
        this.singleEntity = null;
        this.entities = entities;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UsersMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.singleEntity != null) {
            msg.writeInt(1);

            this.singleEntity.compose(msg);
        } else {
            msg.writeInt(this.entities.size());
            for (RoomEntity entity : this.entities) {
                entity.compose(msg);
            }
        }
    }
}
