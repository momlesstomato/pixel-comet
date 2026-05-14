package com.cometproject.server.network.messages.incoming.room.item;

import com.cometproject.server.game.rooms.objects.items.types.floor.football.FootballGateFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the save football gate message event published by the network message subsystem.
 */
public class SaveFootballGateMessageEvent implements Event {

    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }
        if (!room.getRights().hasRights(client.getPlayer().getEntity().getPlayerId()) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            client.disconnect();
            return;
        }

        int itemId = msg.readInt();

        if (room.getItems().getFloorItem(itemId) == null || !(room.getItems().getFloorItem(itemId) instanceof FootballGateFloorItem))
            return;

        FootballGateFloorItem floorItem = ((FootballGateFloorItem) room.getItems().getFloorItem(itemId));

        String gender = msg.readString().toUpperCase();
        String figure = msg.readString();

        floorItem.setFigure(gender.toUpperCase(), figure.contains(";") ?
                figure.split(";")[gender.equals("M") ? 0 : 1] : figure);
        floorItem.saveData();

        floorItem.sendUpdate();
    }
}
