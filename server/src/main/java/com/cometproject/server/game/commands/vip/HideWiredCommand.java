package com.cometproject.server.game.commands.vip;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.WiredActionRandomEffect;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.addons.WiredAddonUnseenEffect;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.RemoveFloorItemMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.items.SendFloorItemMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes hide wired command behavior for the Comet subsystem.
 */
public class HideWiredCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        final Room room = client.getPlayer().getEntity().getRoom();

        if (!client.getPlayer().getPermissions().getRank().roomFullControl() && !client.getPlayer().getEntity().hasRights()) {
            return;
        }

        String msg = "";

        if (client.getPlayer().getEntity().getRoom().getData().isWiredHidden()) {
            // show wireds
            room.getData().setIsWiredHidden(false);
            msg = Locale.getOrDefault("command.hidewired.shown", "Wired is now visible");

            for (RoomItemFloor floorItem : room.getItems().getFloorItems().values()) {
                if (floorItem instanceof WiredFloorItem || floorItem instanceof WiredAddonUnseenEffect || floorItem instanceof WiredActionRandomEffect) {
                    room.getEntities().broadcastMessage(new SendFloorItemMessageComposer(floorItem));
                }
            }

        } else {
            // hide wireds
            room.getData().setIsWiredHidden(true);
            msg = Locale.getOrDefault("command.hidewired.hidden", "Wired is now hidden");

            for (RoomItemFloor floorItem : room.getItems().getFloorItems().values()) {

                if (floorItem instanceof WiredFloorItem || floorItem instanceof WiredAddonUnseenEffect || floorItem instanceof WiredActionRandomEffect) {
                    room.getEntities().broadcastMessage(new RemoveFloorItemMessageComposer(floorItem.getVirtualId(),
                            client.getPlayer().getId()));
                }
            }
        }

        sendNotif(msg, client);

        GameContext.getCurrent().getRoomService().saveRoomData(room.getData());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "hidewired_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.hidewired.description");
    }
}
