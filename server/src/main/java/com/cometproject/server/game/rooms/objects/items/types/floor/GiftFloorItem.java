package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.furniture.types.GiftData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes gift floor item behavior for the room subsystem.
 */
public class GiftFloorItem extends RoomItemFloor {
    private GiftData giftData;
    private boolean isOpened = false;

    /**
     * Creates a gift floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     * @throws Exception When the operation cannot complete.
     */
    public GiftFloorItem(RoomItemData roomItemData, Room room) throws Exception {
        super(roomItemData, room);

        this.giftData = JsonUtil.getInstance().fromJson(roomItemData.getData().split("GIFT::##")[1], GiftData.class);

        if (!CatalogManager.getInstance().getGiftBoxesNew().contains(giftData.getSpriteId()) && !CatalogManager.getInstance().getGiftBoxesOld().contains(giftData.getSpriteId())) {
            throw new Exception("some sad fucker used an exploit, bye bye gift.");
        }
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        final GiftData giftData = this.getGiftData();
        final PlayerAvatar purchaser = PlayerManager.getInstance().getAvatarByPlayerId(giftData.getSenderId(), PlayerAvatar.USERNAME_FIGURE);

        msg.writeInt(giftData.getWrappingPaper() * 1000 + giftData.getDecorationType());
        msg.writeInt(1);
        msg.writeInt(6);
        msg.writeString("EXTRA_PARAM");
        msg.writeString("");
        msg.writeString("MESSAGE");
        msg.writeString(giftData.getMessage());
        msg.writeString("PURCHASER_NAME");
        msg.writeString(purchaser.getUsername());
        msg.writeString("PURCHASER_FIGURE");
        msg.writeString(purchaser.getFigure());
        msg.writeString("PRODUCT_CODE");
        msg.writeString("");
        msg.writeString("state");
        msg.writeString(this.isOpened() ? "1" : "0");
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param state State supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int state, boolean isWiredTrigger) {
        this.isOpened = true;

        this.sendUpdate();
//        this.getRoom().getEntities().broadcastMessage(new RemoveFloorItemMessageComposer(this.getVirtualId(), this.getItemData().getOwnerId(), 1200));
//        this.getRoom().getEntities().broadcastMessage(new SendFloorItemMessageComposer(this));

        this.isOpened = false;
        return true;
    }

    /**
     * Returns the gift data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public GiftData getGiftData() {
        return giftData;
    }

    /**
     * Indicates whether opened applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOpened() {
        return isOpened;
    }
}
