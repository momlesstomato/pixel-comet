package com.cometproject.server.network.messages.incoming.group;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.server.composers.group.GroupFurnitureWidgetMessageComposer;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.groups.GroupFloorItem;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the group furniture widget message event published by the network message subsystem.
 */
public class GroupFurnitureWidgetMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int virtualId = msg.readInt();

        long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if (client.getPlayer().getEntity() != null && client.getPlayer().getEntity().getRoom() != null) {
            RoomItemFloor floorItem = client.getPlayer().getEntity().getRoom().getItems().getFloorItem(itemId);

            if (floorItem != null && floorItem instanceof GroupFloorItem) {
                IGroup group = GameContext.getCurrent().getGroupService().getGroup(((GroupFloorItem) floorItem).getGroupId());

                if (group != null) {
                    client.send(new GroupFurnitureWidgetMessageComposer(virtualId, group.getId(), group.getData().getTitle(), group.getData().getRoomId(), client.getPlayer().getGroups().contains(group.getId()), false));
                }
            }
        }
    }
}
