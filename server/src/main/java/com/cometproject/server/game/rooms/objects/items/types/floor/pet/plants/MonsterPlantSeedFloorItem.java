package com.cometproject.server.game.rooms.objects.items.types.floor.pet.plants;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.pets.PetManager;
import com.cometproject.server.game.pets.data.PetMonsterPlantData;
import com.cometproject.server.game.pets.races.plants.PetMonsterPlant;
import com.cometproject.server.game.pets.races.plants.PetMonsterPlantColor;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.MonsterPlantEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.pets.PetDao;

/**
 * Describes monster plant seed floor item behavior for the room subsystem.
 */
public class MonsterPlantSeedFloorItem extends RoomItemFloor {

    /**
     * Creates a monster plant seed floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public MonsterPlantSeedFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced(){
        Session session = NetworkManager.getInstance().getSessions().getByPlayerId(this.getItemData().getOwnerId());

        if(session != null){

        }
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


            int minimumRarity = 0;

            int bodyRarity = randomBody(minimumRarity);
            int colorRarity = randomColor(minimumRarity);

            PetMonsterPlant body = PetManager.getInstance().getMonsterPlantBodies().get(bodyRarity);
            PetMonsterPlantColor color = PetManager.getInstance().getMonsterPlantColors().get(colorRarity);

            final PetMonsterPlantData petData = new PetMonsterPlantData
                    (
                            minimumRarity,
                            bodyRarity,
                            colorRarity,
                            this.getItemData().getOwnerId(),
                            body.getId(),
                            color.getId(),
                            Comet.getRandom().nextInt(12) + 1,
                            Comet.getRandom().nextInt(11),
                            Comet.getRandom().nextInt(12) + 1,
                            Comet.getRandom().nextInt(11),
                            Comet.getRandom().nextInt(12) + 1,
                            Comet.getRandom().nextInt(11)
                    );
            final int petId = PetDao.createPet(this.getItemData().getOwnerId(), petData.getName(), petData.getTypeId(), petData.getRaceId(), "FFFFFF", petData.getExtradata());
            petData.setId(petId);

            MonsterPlantEntity petEntity = this.getRoom().getPets().addMonsterPlant(petData, this.getPosition());
            this.getTile().getEntities().add(petEntity);

            this.getRoom().getEntities().broadcastMessage(new AvatarsMessageComposer(petEntity));
            petEntity.addStatus(RoomEntityStatus.GESTURE, RoomEntityStatus.FLASH.getStatusCode());
            petEntity.sendGrowth();
            petEntity.removeStatus(RoomEntityStatus.GESTURE);


            this.getRoom().getItems().removeItem(this, null, false, true);


        }
        return false;
    }


    /**
     * Executes random body for this room contract.
     *
     * @param minimumRarity Minimum rarity supplied by the caller.
     * @return Result produced by the operation.
     */
    public int randomBody(int minimumRarity) {
        int size = (minimumRarity == 7) ? 4 : 1;
        int sizeMin = (minimumRarity == 7) ? 1 : 2;
        int min = Math.max(minimumRarity - sizeMin, 0);
        int max = Math.min(minimumRarity + size, 11);
        return Comet.getRandom().nextInt(max - min + 1) + min;
    }

    /**
     * Executes random color for this room contract.
     *
     * @param minimumRarity Minimum rarity supplied by the caller.
     * @return Result produced by the operation.
     */
    public int randomColor(int minimumRarity) {
        boolean isGold = minimumRarity == 7;

        int min = Math.max(minimumRarity - ((isGold) ? 1 : 3), 0);
        if (min == 0) {
            min = Math.max(minimumRarity - 2, 0);
            if (min == 0) {
                min = Math.max(minimumRarity - 1, 0);
            }
        }

        int max = Math.min((minimumRarity + 3), 10);

        return Comet.getRandom().nextInt(max - min + 1) + min;
    }


    /**
     * Executes random for this room contract.
     *
     * @param low Low supplied by the caller.
     * @param high High supplied by the caller.
     * @param bias Bias supplied by the caller.
     * @return Result produced by the operation.
     */
    public int random(int low, int high, double bias) {
        double r = Math.random();
        r = Math.pow(r, bias);
        return (int) (low + (high - low) * r);
    }


    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(1);
        msg.writeString("rarity");
        msg.writeString(this.getItemData().getData());
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
    }
}