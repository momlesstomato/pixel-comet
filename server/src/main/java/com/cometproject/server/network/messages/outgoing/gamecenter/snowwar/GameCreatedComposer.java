package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.games.snowwar.RoomQueue;
import com.cometproject.server.network.messages.outgoing.gamecenter.snowwar.parse.SerializeGame2;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

    /**
     * Describes game created composer behavior for the network message subsystem.
     */
    public class GameCreatedComposer extends MessageComposer {
        private final RoomQueue roomQueue;

        /**
         * Creates a game created composer instance for the network message subsystem.
         *
         * @param roomQueue Room queue supplied by the caller.
         */
        public GameCreatedComposer(RoomQueue roomQueue) {
            this.roomQueue = roomQueue;
        }

        /**
         * Writes this message body using the Pixel Protocol field order.
         *
         * @param msg Composer buffer that receives serialized protocol fields.
         */
        @Override
        public void compose(IComposer msg) {
            SerializeGame2.parse(msg, this.roomQueue);
        }

        /**
         * Returns the id for this network message contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
        public short getId() {
            return Composers.GameOwnerSerializationMessageComposer;
        }
    }