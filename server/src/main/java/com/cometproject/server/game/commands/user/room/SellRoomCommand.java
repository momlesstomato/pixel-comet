package com.cometproject.server.game.commands.user.room;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.rooms.RoomData;

/**
 * Describes sell room command behavior for the Comet subsystem.
 */
public class SellRoomCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if(params.length == 0){
            SellRoomCommand.sendNotif("Uso del comando incorrecto. :vendersala cantidad", client);
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();
        if(params[0] == "cancelar" || params[0].contains("cancelar")){
            if(room.getData().getOwnerId() != client.getPlayer().getId()){
                SellRoomCommand.sendNotif("Necesitas ser el dueño de esta sala para ejecutar este comando.", client);
                return;
            }
            RoomData roomData = (RoomData) room.getData();
            if(roomData.isOnSale){
                roomData.roomPrice = 0;
                roomData.isOnSale = false;

                SellRoomCommand.sendNotif("Has cancelado la venta de esta sala.", client);
                String messagePlayer = "Se ha cancelado la venta de esta sala.";
                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), messagePlayer, 5));
            }
            else SellRoomCommand.sendNotif("Esta sala no está en venta.", client);
        }

        if(room.getData().getOwnerId() == client.getPlayer().getId()){
            try{
                RoomData roomData = (RoomData) room.getData();
                int price = Integer.parseInt(params[0]);
                if(price > 0 && price < 9999){
                    roomData.roomPrice = price;
                    roomData.isOnSale = true;

                    SellRoomCommand.sendNotif("Sala en venta. Para cancelar usa :vendersala cancelar", client);
                    String messagePlayer = "Esta sala se encuentra en venta. Precio: " + price + " asteroides. Usa :comprarsala si quieres comprarla.";
                    client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), messagePlayer, 5));
                }
                else SellRoomCommand.sendNotif("La cantidad debe ser entre 0 y 9999.", client);
            }catch (NumberFormatException e){
                SellRoomCommand.sendNotif("Cantidad incorrecta.", client);
            }
        }
        else SellRoomCommand.sendNotif("Necesitas ser el dueño de esta sala para ejecutar este comando.", client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "sellroom_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault(null, "precio");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.sellroom.description");
    }
}

