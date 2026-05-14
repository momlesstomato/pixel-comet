package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.rentables.RentableDataMessageComposer;
import com.cometproject.server.storage.queries.items.ItemDao;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Describes rentable space floor item behavior for the room subsystem.
 */
public class RentableSpaceFloorItem  extends RoomItemFloor {

    /**
     * Creates a rentable space floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public RentableSpaceFloorItem (RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (isWiredTrigger) {
            return false;
        }

        PlayerEntity p = ((PlayerEntity) entity);

        if(p == null) return false;

        if(ItemDao.getRentableData((int)getId()) < 1){
            p.getPlayer().getSession().send(new RentableDataMessageComposer(false, 0, 100));
            return true;
        }

        int renter = ItemDao.getRenterBySpace((int)getId());
        String renterName = PlayerDao.getRenterById(renter);

        p.getPlayer().getSession().send(new RentableDataMessageComposer(true, 0, renter, renterName, 100));
        return true;
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        this.getItemData().setData("48");
        this.sendUpdate();

        this.saveData();
    }

    /**
     * Handles the item added to stack callback for this room contract.
     *
     * @param f F supplied by the caller.
     */
    @Override
    public void onItemAddedToStack(RoomItemFloor f){
    }

    /**
     * Executes verify status for this room contract.
     *
     * @param p P supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean verifyStatus(Player p){
        // Create Rentable component for player and check.
        return true;
    }
    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
    }
}