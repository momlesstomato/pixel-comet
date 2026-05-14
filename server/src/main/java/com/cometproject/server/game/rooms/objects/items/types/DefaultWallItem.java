package com.cometproject.server.game.rooms.objects.items.types;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.storage.queries.items.ItemDao;


/**
 * Describes default wall item behavior for the room subsystem.
 */
public final class DefaultWallItem extends RoomItemWall {
    /**
     * Creates a default wall item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public DefaultWallItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);
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
            if (!(entity instanceof PlayerEntity))
                return false;

            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)entity;
                if(playerEntity.getPlayer().getData().isSearchFurniActive){
                    int furniId = ItemDao.getFurniIdByItem(this.getId());
                    if(furniId != 0){
                        int catalogId = ItemDao.getCatalogIdByItem(furniId);
                        if(catalogId != 0){
                            String catalogName = ItemDao.getCatalogNameByCatalogId(catalogId);
                            String messageFurni = "Este furni se encuentra en la sección " + catalogName + ". [" + furniId +"]";

                            playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getPlayer().getEntity().getId(), messageFurni, 1));
                        }
                        else playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getPlayer().getEntity().getId(), "Este furni no se ha podido encontrar en el catálogo.", 1));
                    }
                    else playerEntity.getPlayer().getSession().send(new WhisperMessageComposer(playerEntity.getPlayer().getEntity().getId(), "Este furni no se ha podido encontrar en el catálogo.", 1));
                }
            }

            if (!entity.getRoom().getRights().hasRights(((PlayerEntity) entity).getPlayerId())) {
                return false;
            }
        }

        this.toggleInteract(true);
        this.sendUpdate();

        this.saveData();
        return true;
    }
}
