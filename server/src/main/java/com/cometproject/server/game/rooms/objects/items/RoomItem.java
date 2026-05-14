package com.cometproject.server.game.rooms.objects.items;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.rooms.objects.IRoomItemData;
import com.cometproject.api.game.rooms.objects.data.LimitedEditionItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.types.LowPriorityItemProcessor;
import com.cometproject.server.game.rooms.objects.BigRoomFloorObject;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.types.AdvancedFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.RollerFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.SoundMachineFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.football.FootballGateFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.banzai.BanzaiTeleporterFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.utilities.attributes.Attributable;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Describes room item behavior for the room subsystem.
 */
public abstract class RoomItem extends BigRoomFloorObject implements Attributable {
    private final Set<Long> wiredItems = Sets.newHashSet();
    private final IRoomItemData itemData;
    protected int ticksTimer;
    private LimitedEditionItemData limitedEditionItemData;
    private Map<String, Object> attributes;
    private int moveDirection = -1;

    /**
     * Creates a room item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public RoomItem(IRoomItemData roomItemData, Room room) {
        super(roomItemData.getId(), roomItemData.getPosition(), room);

        this.itemData = roomItemData;
        this.ticksTimer = -1;
    }

    /**
     * Executes toggle interact for this room contract.
     *
     * @param state State supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean toggleInteract(boolean state) {
        if (!state) {
            if (!(this instanceof WiredFloorItem))
                this.getItemData().setData("0");

            return true;
        }

        if (!StringUtils.isNumeric(this.getItemData().getData())) {
            return true;
        }

        if (this.getDefinition().getInteractionCycleCount() > 1) {
            if (this.getItemData().getData().isEmpty() || this.getItemData().getData().equals(" ")) {
                this.getItemData().setData("0");
            }

            int i = Integer.parseInt(this.getItemData().getData()) + 1;

            if (i > (this.getDefinition().getInteractionCycleCount() - 1)) { // take one because count starts at 0 (0, 1) = count(2)
                this.getItemData().setData("0");
            } else {
                this.getItemData().setData(i + "");
            }

            return true;
        } else {
            return false;
        }
    }


    /**
     * Returns the wired items for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Long> getWiredItems() {
        return this.wiredItems;
    }

    /**
     * Executes requires tick for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public final boolean requiresTick() {
        return this.hasTicks() || this instanceof WiredFloorItem || this instanceof AdvancedFloorItem || this instanceof RollerFloorItem;
    }

    /**
     * Indicates whether this room contract has ticks.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    protected final boolean hasTicks() {
        return (this.ticksTimer > 0);
    }

    /**
     * Updates the ticks for this room contract.
     *
     * @param time Time supplied by the caller.
     */
    protected final void setTicks(int time) {
        this.ticksTimer = time;

        if (this instanceof BanzaiTeleporterFloorItem) {
            LowPriorityItemProcessor.getInstance().submit(((RoomItemFloor) this));
        }
    }

    /**
     * Indicates whether this room contract can cel ticks.
     */
    protected final void cancelTicks() {
        this.ticksTimer = -1;
    }

    /**
     * Executes tick for this room contract.
     */
    public final void tick() {
        this.onTick();

        if (this.ticksTimer > 0) {
            this.ticksTimer--;
        }

        if (this.ticksTimer == 0) {
            this.cancelTicks();
            this.onTickComplete();
        }
    }

    /**
     * Handles the tick callback for this room contract.
     */
    protected void onTick() {
        // Override this
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    protected void onTickComplete() {
        // Override this
    }

    /**
     * Handles the placed callback for this room contract.
     */
    public void onPlaced() {
        // Override this
    }

    /**
     * Handles the pickup callback for this room contract.
     */
    public void onPickup() {
        // Override this
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        // Override this
        return true;
    }

    /**
     * Handles the load callback for this room contract.
     */
    public void onLoad() {
        // Override this
    }

    /**
     * Handles the unload callback for this room contract.
     */
    public void onUnload() {
        // Override this
    }

    /**
     * Handles the entity leave room callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void onEntityLeaveRoom(RoomEntity entity) {
        // Override this
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void composeItemData(IComposer msg) {
        msg.writeInt(1);
        msg.writeInt(0);

        msg.writeString((this instanceof FootballGateFloorItem) ? "" :
                (this instanceof WiredFloorItem) ? ((WiredFloorItem) this).getState() ? "1" : "0" :
                        (this instanceof SoundMachineFloorItem) ? ((SoundMachineFloorItem) this).getState() ? "1" : "0" :
                                this.getItemData().getData());
    }

    /**
     * Updates the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @param attributeValue Attribute value supplied by the caller.
     */
    @Override
    public void setAttribute(String attributeKey, Object attributeValue) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }

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
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }

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
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }

        return this.attributes.containsKey(attributeKey);
    }

    /**
     * Removes attribute from this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     */
    @Override
    public void removeAttribute(String attributeKey) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }

        this.attributes.remove(attributeKey);
    }

    /**
     * Executes serialize for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public abstract void serialize(IComposer msg);

    /**
     * Returns the definition for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract FurnitureDefinition getDefinition();

    /**
     * Executes send update for this room contract.
     */
    public abstract void sendUpdate();

    /**
     * Executes save for this room contract.
     */
    public abstract void save();

    /**
     * Persists data for this room contract.
     */
    public abstract void saveData();

    /**
     * Releases resources owned by this room component.
     */
    public void dispose() {

    }

    /**
     * Returns the item data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomItemData getItemData() {
        return this.itemData;
    }

    /**
     * Returns the limited edition item data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public LimitedEditionItemData getLimitedEditionItemData() {
        return limitedEditionItemData;
    }

    /**
     * Updates the limited edition item data for this room contract.
     *
     * @param limitedEditionItemData Limited edition item data supplied by the caller.
     */
    public void setLimitedEditionItemData(LimitedEditionItemData limitedEditionItemData) {
        this.limitedEditionItemData = limitedEditionItemData;
    }

    /**
     * Returns the move direction for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMoveDirection() {
        return moveDirection;
    }

    /**
     * Updates the move direction for this room contract.
     *
     * @param moveDirection Move direction supplied by the caller.
     */
    public void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }

    /**
     * Returns the rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getRotation();
}
