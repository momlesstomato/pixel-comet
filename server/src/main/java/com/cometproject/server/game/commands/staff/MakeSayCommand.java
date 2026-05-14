package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.objects.entities.RoomEntityType;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes make say command behavior for the Comet subsystem.
 */
public class MakeSayCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 2) return;

        String player = params[0];
        String message = this.merge(params, 1);

        Room room = client.getPlayer().getEntity().getRoom();

        if(player.equals("Custom")){
            room.getEntities().broadcastMessage(new TalkMessageComposer(client.getPlayer().getEntity().getId(), "He intentado que Custom diga: " + message + " ¡Soy alto pendex!", RoomManager.getInstance().getEmotions().getEmotion(message), 0));
            return;
        }
        
        PlayerEntity playerEntity = (PlayerEntity) room.getEntities().getEntityByName(player, RoomEntityType.PLAYER);

        room.getEntities().broadcastMessage(new TalkMessageComposer(playerEntity.getId(), message, RoomManager.getInstance().getEmotions().getEmotion(message), 0));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "makesay_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.username" + " " + "command.parameter.message", "%username% %message%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.makesay.description");
    }
}
