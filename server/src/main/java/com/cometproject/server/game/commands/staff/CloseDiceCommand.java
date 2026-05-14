package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.DiceFloorItem;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.game.utilities.DistanceCalculator;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes close dice command behavior for the Comet subsystem.
 */
public class CloseDiceCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        PlayerEntity playerEntity = client.getPlayer().getEntity();
        if (playerEntity == null) return;

        int diceAmount = 0;

        for (RoomItemFloor item : playerEntity.getRoom().getItems().getByClass(DiceFloorItem.class)) {
            if (DistanceCalculator.tilesTouching(playerEntity.getPosition(), item.getPosition())) {
                if (!item.getItemData().getData().equals("0")) {
                    item.getItemData().setData("0");
                    item.sendUpdate();
                    item.saveData();
                    diceAmount++;
                }
            }
        }

        if (diceAmount > 0) {
            String filteredMessage = Locale.getOrDefault("command.closedice.message", "* %username% closed %amount% dices *").replace("%username%", playerEntity.getUsername()).replace("%amount%", "" + diceAmount);
            playerEntity.getRoom().getEntities().broadcastMessage(new TalkMessageComposer(client.getPlayer().getEntity().getId(), filteredMessage, ChatEmotion.NONE, 1));
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "closedice_command";
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
        return Locale.getOrDefault("command.closedice.description", "");
    }
}