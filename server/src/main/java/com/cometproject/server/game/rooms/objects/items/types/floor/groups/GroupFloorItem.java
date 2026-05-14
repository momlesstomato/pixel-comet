package com.cometproject.server.game.rooms.objects.items.types.floor.groups;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarUpdateMessageComposer;
import org.apache.commons.lang3.StringUtils;


/**
 * Describes group floor item behavior for the room subsystem.
 */
public class GroupFloorItem extends RoomItemFloor {
    private int groupId;

    /**
     * Creates a group floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public GroupFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        final String data = this.getItemData().getData();

        if (!StringUtils.isNumeric(data) || data.isEmpty())
            this.groupId = 0;
        else
            this.groupId = Integer.parseInt(data);
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        final IGroupData groupData = GameContext.getCurrent().getGroupService().getData(this.groupId);

        msg.writeInt(0);
        if (groupData == null) {
            msg.writeInt(2);
            msg.writeInt(0);
        } else {
            msg.writeInt(2);
            msg.writeInt(5);
            msg.writeString(this instanceof GroupGateFloorItem ? ((GroupGateFloorItem) this).isOpen ? "1" : "0" : "0");
            msg.writeString(this.getItemData().getData());
            msg.writeString(groupData.getBadge());

            String colourA = GameContext.getCurrent().getGroupService().getItemService().getSymbolColours().get(groupData.getColourA()) != null ? GameContext.getCurrent().getGroupService().getItemService().getSymbolColours().get(groupData.getColourA()).getFirstValue() : "ffffff";
            String colourB = GameContext.getCurrent().getGroupService().getItemService().getBackgroundColours().get(groupData.getColourB()) != null ? GameContext.getCurrent().getGroupService().getItemService().getBackgroundColours().get(groupData.getColourB()).getFirstValue() : "ffffff";

            msg.writeString(colourA);
            msg.writeString(colourB);
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
            if (!(entity instanceof PlayerEntity))
                return false;

            PlayerEntity pEntity = (PlayerEntity) entity;
            if (!pEntity.getRoom().getRights().hasRights(pEntity.getPlayerId()) && !pEntity.getPlayer().getPermissions().getRank().roomFullControl())
                return false;

        }

        this.toggleInteract(true);
        this.sendUpdate();
        this.saveData();
        return true;
    }


    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param instantUpdate Instant update supplied by the caller.
     */
    public void onEntityStepOn(RoomEntity entity, boolean instantUpdate) {
        if (!this.getDefinition().canSit()) return;

        double height = (entity instanceof PetEntity || entity.hasAttribute("transformation")) ? this.getSitHeight() / 2 : this.getSitHeight();

        entity.setBodyRotation(this.getRotation());
        entity.setHeadRotation(this.getRotation());
        entity.addStatus(RoomEntityStatus.SIT, String.valueOf(height).replace(',', '.'));

        if (instantUpdate)
            this.getRoom().getEntities().broadcastMessage(new AvatarUpdateMessageComposer(entity));
        else
            entity.markNeedsUpdate();
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDataObject() {
        return this.groupId + "";
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        this.onEntityStepOn(entity, false);
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        if (entity.hasStatus(RoomEntityStatus.SIT)) {
            entity.removeStatus(RoomEntityStatus.SIT);
        }

        entity.markNeedsUpdate();
    }

    /**
     * Returns the sit height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getSitHeight() {
        return this.getDefinition().getHeight();
    }

    /**
     * Returns the group id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGroupId() {
        return groupId;
    }
}
