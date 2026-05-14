package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.quests.QuestType;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.utilities.RandomUtil;


/**
 * Describes akinator floor item behavior for the room subsystem.
 */
public class AkinatorFloorItem extends RoomItemFloor {
    private boolean isInUse = false;

    /**
     * Creates a akinator floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public AkinatorFloorItem(RoomItemData itemData, Room room) {
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
        if (!isWiredTrigger) {
            if (!this.getPosition().touching(entity.getPosition())) {
                entity.moveTo(this.getPosition().squareInFront(this.getRotation()).getX(), this.getPosition().squareBehind(this.getRotation()).getY());
                return false;
            }
        }

        entity.cancelWalk();
        entity.lookTo(this.getPosition().squareInFront(this.getRotation()).getX() - 1, this.getPosition().squareBehind(this.getRotation()).getY() - 1);


        if (this.isInUse) {
            ((PlayerEntity) entity).getPlayer().getSession().send(new WhisperMessageComposer(this.getVirtualId(), "El genio de la lámpara está ocupado, déjalo pensar.", 8));
            return false;
        }

        this.isInUse = true;

        String truth = "";
        boolean n = false;

        int random1 = RandomUtil.getRandomInt(1, 6);

        switch (random1){
            case 1: truth="Por rezarle a la Virgensita de HiddenLupe no ganarás más Koins de la cuenta, joven."; break;
            case 2: truth="Le secuestraron... la última vez que le vieron fue chillando en un laboratorio, más tarde se lo llevaron a otro lugar."; n = true; break;
            case 3: truth="Plantar en 20 es un grave error mijo, nunca sabes si el uno puede alegrarte el día."; break;
            case 4: truth="Custom, qué necesito para tener rango de GM, tengo experiencia en Wires. :)"; break;
            case 5: truth="Esto ya pasó una vez... maldito reciclaje de idea... digo... tenéis poco tiempo, sacarlo, por los muerto."; n = true; break;
            case 6: truth="Eh... tú... psst. Sí tú, ¿así que consultando con un clon, eh?"; break;
        }

        entity.getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(this.getVirtualId(), truth, 8));

        if(n){
            // TODO: Achievement for consulting this boy after the 12-10-2018 event.
            ((PlayerEntity) entity).getPlayer().getQuests().progressQuest(QuestType.WEEN_CHECK_AKINATOR, 1);
        }

        this.setTicks(RoomItemFactory.getProcessTime(3));

        this.getItemData().setData("1");
        this.sendUpdate();
        this.saveData();
        return true;
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        if (!"0".equals(this.getItemData().getData())) {
            this.getItemData().setData("0");
        }
    }

    /**
     * Handles the pickup callback for this room contract.
     */
    @Override
    public void onPickup() {
        this.cancelTicks();
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        this.isInUse = false;

        this.getItemData().setData("0");
        this.sendUpdate();

        this.saveData();
    }
}
