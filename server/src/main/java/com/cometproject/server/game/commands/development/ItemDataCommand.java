package com.cometproject.server.game.commands.development;

import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes item data command behavior for the Comet subsystem.
 */
public class ItemDataCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        final PlayerEntity playerEntity = client.getPlayer().getEntity();

        RoomItemFloor floorItem = playerEntity.getTile().getTopItemInstance();

        if (floorItem == null) {
            return;
        }

        client.send(new MotdNotificationMessageComposer(String.format("Item Data (%s)\n\nRotation: %s\nType: %s\nPosition: %s", floorItem.getId(), floorItem.getRotation(), floorItem.getClass().getSimpleName(), floorItem.getPosition())));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "dev";
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
        return "Gets the data for the item you're currently standing on";
    }
}
