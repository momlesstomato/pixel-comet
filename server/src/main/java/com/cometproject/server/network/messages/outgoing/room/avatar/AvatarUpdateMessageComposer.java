package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.network.messages.outgoing.room.items.SlideObjectBundleMessageComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Serializes the avatar update message for the Pixel Protocol client.
 */
public class AvatarUpdateMessageComposer extends MessageComposer {

    private final int count;
    private final AvatarState singleEntity;
    private final List<AvatarState> entities;

    /**
     * Creates a avatar update message composer instance for the network message subsystem.
     *
     * @param entities Entities supplied by the caller.
     */
    public AvatarUpdateMessageComposer(final Collection<RoomEntity> entities) {
        this.entities = Lists.newArrayList();

        for (RoomEntity entity : entities) {
            if (!entity.isVisible() || !entity.sendUpdateMessage()) {
                entity.setSendUpdateMessage(true);
                continue;
            }

            if(entity.isTransformed()){
                entity.getRoom().getEntities().broadcastMessage(new SlideObjectBundleMessageComposer(new Position(entity.getPosition().getX(),entity.getPosition().getY()), new Position(entity.getPosition().getX(),entity.getPosition().getY()), 0, 0, 2147418112 - entity.getId()));
            }

            this.entities.add(new AvatarState(entity));
        }

        this.count = this.entities.size();
        this.singleEntity = null;
    }

    /**
     * Creates a avatar update message composer instance for the network message subsystem.
     *
     * @param entity Entity supplied by the caller.
     */
    public AvatarUpdateMessageComposer(final RoomEntity entity) {
        this.count = 1;
        this.singleEntity = new AvatarState(entity);
        this.entities = null;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.count);

        if (this.singleEntity != null) {
            this.composeEntity(msg, this.singleEntity);
        } else {
            for (final AvatarState entity : this.entities) {
                this.composeEntity(msg, entity);
            }
        }
    }

    private void composeEntity(IComposer msg, AvatarState entity) {
        msg.writeInt(entity.getId());

        msg.writeInt(entity.getPosition().getX());
        msg.writeInt(entity.getPosition().getY());
        msg.writeString(String.valueOf(entity.getPosition().getZ()));

        msg.writeInt(entity.getHeadRotation());
        msg.writeInt(entity.getBodyRotation());

        msg.writeString(entity.getStatusString());
    }

    private static class AvatarState {
        private final int id;
        private final Position position;
        private final int headRotation;
        private final int bodyRotation;
        private final String statusString;

        /**
         * Executes avatar state for this network message contract.
         *
         * @param entity Entity supplied by the caller.
         */
        public AvatarState(RoomEntity entity) {
            this.id = entity.getId();
            this.position = entity.getPosition().copy();
            this.headRotation = entity.getHeadRotation();
            this.bodyRotation = entity.getBodyRotation();

            StringBuilder statusString = new StringBuilder();
            statusString.append("/");

            for (Map.Entry<RoomEntityStatus, String> status : entity.getStatuses().entrySet()) {

                statusString.append(status.getKey().getStatusCode());

                if (!status.getValue().isEmpty()) {
                    statusString.append(" ");
                    statusString.append(status.getValue());
                }

                statusString.append("/");
            }

            statusString.append("/");

            this.statusString = statusString.toString();
        }

        /**
         * Returns the id for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the position for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        public Position getPosition() {
            return position;
        }

        /**
         * Returns the head rotation for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        public int getHeadRotation() {
            return headRotation;
        }

        /**
         * Returns the body rotation for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        public int getBodyRotation() {
            return bodyRotation;
        }

        /**
         * Returns the status string for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        public String getStatusString() {
            return statusString;
        }

        /**
         * Executes to string for this network message contract.
         *
         * @return Result produced by the operation.
         */
        @Override
        public String toString() {
            return String.format("AvatarState[%s, %s, %s, %s, %s]", this.id, this.position, this.headRotation, this.bodyRotation, this.statusString);
        }
    }
}
