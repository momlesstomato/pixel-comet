package com.cometproject.server.network.messages.incoming.catalog.ads;

import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.RoomPromotion;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.events.RoomPromotionMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.rooms.RoomDao;

/**
 * Represents the promotion update message event published by the network message subsystem.
 */
public class PromotionUpdateMessageEvent
implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int id = msg.readInt();
        String promotionName = msg.readString();
        String promotionDescription = msg.readString();
        Room room = client.getPlayer().getEntity().getRoom();
        if (room == null || room.getData().getOwnerId() != client.getPlayer().getId() && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }
        RoomPromotion roomPromotion = room.getPromotion();
        if (roomPromotion != null) {
            roomPromotion.setPromotionName(promotionName);
            roomPromotion.setPromotionDescription(promotionDescription);
            RoomDao.updatePromotedRoom(roomPromotion);
            room.getEntities().broadcastMessage(new RoomPromotionMessageComposer(room.getData(), roomPromotion));
        }
    }
}

