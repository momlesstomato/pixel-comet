package com.cometproject.server.game.commands.user.room;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.objects.items.RoomItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.RoomReloadListener;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.items.ItemDao;
import com.cometproject.server.storage.queries.rooms.RoomDao;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.data.rooms.RoomData;
import com.cometproject.storage.api.services.ICurrencyService;

import java.util.ArrayList;

/**
 * Describes buy room command behavior for the Comet subsystem.
 */
public class BuyRoomCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        Room room = client.getPlayer().getEntity().getRoom();
        RoomData roomData = (RoomData) room.getData();

        if(roomData.isOnSale){
            if(room.getData().getOwnerId() != client.getPlayer().getId()){
                final String currencyCode = CometBootstrap.resolve(ICurrencyService.class)
                        .currencyCodeForUseCase(CurrencyUseCases.ROOM_PURCHASE);
                if(client.getPlayer().getData().getCurrencyBalance(currencyCode) > roomData.roomPrice){
                    Session roomSeller = NetworkManager.getInstance().getSessions().getByPlayerId(roomData.getOwnerId());
                    if (roomSeller == null) {
                        BuyRoomCommand.sendNotif(Locale.getOrDefault("command.user.offline", "\u00a1El usuario no est\u00e1 en l\u00ednea!"), client);
                        return;
                    }

                    client.getPlayer().getData().decreaseCurrency(currencyCode, roomData.roomPrice);
                    client.getPlayer().sendBalance();
                    roomSeller.getPlayer().getData().increaseCurrency(currencyCode, roomData.roomPrice);
                    roomSeller.getPlayer().sendBalance();

                    room.getData().setOwnerId(client.getPlayer().getId());
                    room.getData().setOwner(client.getPlayer().getEntity().getUsername());

                    GameContext.getCurrent().getRoomService().saveRoomData(client.getPlayer().getEntity().getRoom().getData());
                    client.getPlayer().getData().save();
                    roomSeller.getPlayer().getData().save();

                    ArrayList<RoomItem> itemsToChange = new ArrayList<RoomItem>();

                    itemsToChange.addAll(room.getItems().getFloorItems().values());
                    itemsToChange.addAll(room.getItems().getWallItems().values());

                    for(RoomItem item : itemsToChange)
                        ItemDao.updateOwnerItem(item.getId(), client.getPlayer().getId());

                    RoomDao.updateOnwerRoom(room.getId(), client.getPlayer().getId(), client.getPlayer().getEntity().getUsername());

                    RoomManager.getInstance().loadRoomsForUser(client.getPlayer());
                    RoomManager.getInstance().loadRoomsForUser(roomSeller.getPlayer());

                    RoomReloadListener reloadListener = new RoomReloadListener(room, (players, newRoom) -> {
                        for (Player player : players) {
                            if (player.getEntity() != null) continue;
                            player.getSession().send(new NotificationMessageComposer("furni_placement_error", Locale.getOrDefault("command.unload.roomReloaded", "The room was reloaded.")));
                            player.getSession().send(new RoomForwardMessageComposer(newRoom.getId()));
                        }
                    });
                    RoomManager.getInstance().addReloadListener(client.getPlayer().getEntity().getRoom().getId(), reloadListener);
                    room.reload();

                    BuyRoomCommand.sendNotif("Has comprado la sala por " + roomData.roomPrice + " asteroides.", client);
                    BuyRoomCommand.sendNotif("Has vendido la sala por " + roomData.roomPrice + " asteroides.", roomSeller);

                    roomData.roomPrice = 0;
                    roomData.isOnSale = false;
                }
                else BuyRoomCommand.sendNotif("No tienes suficiente dinero para comprar esta sala.", client);
            }
            else BuyRoomCommand.sendNotif("No puedes comprar tu propia sala.", client);
        }
        else BuyRoomCommand.sendNotif("Esta sala no está en venta.", client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "buyroom_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault(null, "");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.buyroom.description");
    }
}
