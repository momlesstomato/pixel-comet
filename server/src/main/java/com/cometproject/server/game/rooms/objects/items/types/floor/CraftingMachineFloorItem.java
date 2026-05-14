package com.cometproject.server.game.rooms.objects.items.types.floor;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.items.crafting.CraftingMachine;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes crafting machine floor item behavior for the room subsystem.
 */
public class CraftingMachineFloorItem  extends RoomItemFloor {

    /**
     * Creates a crafting machine floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public CraftingMachineFloorItem (RoomItemData itemData, Room room) {
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

        if(p == null)
            return false;

        CraftingMachine machine = ItemManager.getInstance().getCraftingMachine(this.getDefinition().getId());

        if(machine == null)
            return false;

        p.getPlayer().setLastCraftingMachine(machine);

        //p.getPlayer().getSession().send(new CraftableProductsMessageComposer(machine));
        return true;
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        this.getItemData().setData("1");
        this.sendUpdate();

        this.saveData();
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
    }
}