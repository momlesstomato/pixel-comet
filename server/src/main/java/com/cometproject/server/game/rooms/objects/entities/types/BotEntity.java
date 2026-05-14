package com.cometproject.server.game.rooms.objects.entities.types;

import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.bots.BotData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.ai.BotAI;
import com.cometproject.server.game.rooms.objects.entities.types.ai.bots.*;
import com.cometproject.server.game.rooms.objects.entities.types.data.BotDataObject;
import com.cometproject.server.game.rooms.objects.entities.types.data.types.SpyBotData;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.network.messages.outgoing.room.avatar.LeaveRoomMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.TalkMessageComposer;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Describes bot entity behavior for the room subsystem.
 */
public class BotEntity extends RoomEntity {
    private IBotData data;
    private int cycleCount = 0;
    private BotAI ai;

    private BotDataObject dataObject;

    private Map<String, Object> attributes = new ConcurrentHashMap<>();

    /**
     * Creates a bot entity instance for the room subsystem.
     *
     * @param data Data supplied by the caller.
     * @param identifier Identifier supplied by the caller.
     * @param startPosition Start position supplied by the caller.
     * @param startBodyRotation Start body rotation supplied by the caller.
     * @param startHeadRotation Start head rotation supplied by the caller.
     * @param roomInstance Room instance supplied by the caller.
     */
    public BotEntity(IBotData data, int identifier, Position startPosition, int startBodyRotation, int startHeadRotation, Room roomInstance) {
        super(identifier, startPosition, startBodyRotation, startHeadRotation, roomInstance);

        this.data = data;

        switch (data.getBotType()) {
            default:
                this.ai = new DefaultAI(this);
                break;

            case WAITER:
                this.ai = new WaiterAI(this);
                break;

            case QUEST:
                this.ai = new QuestAI(this);
                break;

            case MIMIC:
                this.ai = new MinionAI(this);
                break;

            case SPY:
                this.ai = new SpyAI(this);

                if (this.data.getData() == null) {
                    this.dataObject = new SpyBotData(new LinkedList<>());
                } else {
                    this.dataObject = JsonUtil.getInstance().fromJson(this.data.getData(), SpyBotData.class);
                }

                break;
        }
    }

    /**
     * Executes say for this room contract.
     *
     * @param message Message supplied by the caller.
     */
    public void say(String message) {
        this.getRoom().getEntities().broadcastMessage(new TalkMessageComposer(this.getId(), message, ChatEmotion.NONE, 2));
    }

    /**
     * Executes join room for this room contract.
     *
     * @param room Room participating in the operation.
     * @param password Password supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean joinRoom(Room room, String password) {
        return true;
    }

    /**
     * Executes finalize join room for this room contract.
     */
    @Override
    protected void finalizeJoinRoom() {

    }

    /**
     * Handles the reached tile callback for this room contract.
     *
     * @param tile Tile supplied by the caller.
     */
    @Override
    public void onReachedTile(RoomTile tile) {
    }

    /**
     * Executes leave room for this room contract.
     */
    public void leaveRoom() {
        this.leaveRoom(false, false, false);
    }

    /**
     * Executes leave room for this room contract.
     *
     * @param isOffline Is offline supplied by the caller.
     * @param isKick Is kick supplied by the caller.
     * @param toHotelView To hotel view supplied by the caller.
     */
    @Override
    public void leaveRoom(boolean isOffline, boolean isKick, boolean toHotelView) {
        // Send leave room message to all instance entities
        this.getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(this.getId()));

        // Remove entity from the room
        this.getRoom().getEntities().removeEntity(this);
        this.getRoom().getBots().removeBot(this.getUsername());

        this.data.dispose();
        this.data = null;

        this.attributes.clear();
    }

    /**
     * Handles the chat callback for this room contract.
     *
     * @param message Message supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onChat(String message) {
        return false;
    }

    /**
     * Persists data object for this room contract.
     */
    public void saveDataObject() {
        if (this.dataObject != null) {
            this.data.setData(JsonUtil.getInstance().toJson(this.dataObject));
            this.data.save();
        }
    }

    /**
     * Handles the room dispose callback for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onRoomDispose() {
        // Send leave room message to all instance entities
        this.getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(this.getId()));

        this.saveDataObject();

        this.data.dispose();
        this.data = null;

        this.attributes.clear();

        return true;
    }

    /**
     * Returns the username for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getUsername() {
        return this.data.getUsername();
    }

    /**
     * Returns the motto for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getMotto() {
        return this.data.getMotto();
    }

    /**
     * Returns the figure for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getFigure() {
        return this.data.getFigure();
    }

    /**
     * Returns the gender for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getGender() {
        return this.data.getGender();
    }

    /**
     * Returns the bot id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBotId() {
        return this.data.getId();
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.getBotId());
        msg.writeString(this.getUsername());
        msg.writeString(this.getMotto());
        msg.writeString(this.getFigure());
        msg.writeInt(this.getId());

        msg.writeInt(this.getPosition().getX());
        msg.writeInt(this.getPosition().getY());
        msg.writeDouble(this.getPosition().getZ());
        msg.writeInt(this.getBodyRotation());
        msg.writeInt(4);

        msg.writeString(this.getGender().toLowerCase());
        msg.writeInt(this.getRoom().getData().getOwnerId());
        msg.writeString(this.getRoom().getData().getOwner());

        msg.writeInt(10);
        msg.writeShort(0);
        msg.writeShort(1);
        msg.writeShort(2);
        msg.writeShort(3);
        msg.writeShort(4);
        msg.writeShort(5);
        msg.writeShort(6);
        msg.writeShort(7);
        msg.writeShort(8);
        msg.writeShort(9);

    }

    /**
     * Returns the data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public BotData getData() {
        return (BotData) this.data;
    }

    /**
     * Returns the cycle count for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCycleCount() {
        return this.cycleCount;
    }

    /**
     * Executes decrement cycle count for this room contract.
     */
    public void decrementCycleCount() {
        cycleCount--;
    }

    /**
     * Executes increment cycle count for this room contract.
     */
    public void incrementCycleCount() {
        cycleCount++;
    }

    /**
     * Executes reset cycle count for this room contract.
     */
    public void resetCycleCount() {
        this.cycleCount = 0;
    }

    /**
     * Returns the ai for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public BotAI getAI() {
        return ai;
    }

    /**
     * Updates the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @param attributeValue Attribute value supplied by the caller.
     */
    @Override
    public void setAttribute(String attributeKey, Object attributeValue) {
        if (this.attributes.containsKey(attributeKey)) {
            this.attributes.replace(attributeKey, attributeValue);
        } else {
            this.attributes.put(attributeKey, attributeValue);
        }
    }

    /**
     * Returns the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Object getAttribute(String attributeKey) {
        return this.attributes.get(attributeKey);
    }

    /**
     * Indicates whether this room contract has attribute.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasAttribute(String attributeKey) {
        return this.attributes.containsKey(attributeKey);
    }

    /**
     * Removes attribute from this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     */
    @Override
    public void removeAttribute(String attributeKey) {
        this.attributes.remove(attributeKey);
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public BotDataObject getDataObject() {
        return dataObject;
    }
}
