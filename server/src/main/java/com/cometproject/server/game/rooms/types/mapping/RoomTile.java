package com.cometproject.server.game.rooms.types.mapping;

import com.cometproject.api.game.rooms.models.RoomTileState;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.RoomObject;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.AffectedTile;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.Square;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.types.EntityPathfinder;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.types.ItemPathfinder;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.*;
import com.cometproject.server.game.rooms.objects.items.types.floor.games.freeze.FreezeBlockFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.groups.GroupGateFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.pet.breeding.BreedingBoxFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.snowboarding.SnowboardJumpFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.survival.SurvivalBlockFloorItem;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


/**
 * Describes room tile behavior for the room subsystem.
 */
public class RoomTile {
    public Set<RoomEntity> entities;
    private RoomMapping mappingInstance;
    private Position position;
    private RoomEntityMovementNode movementNode;
    private RoomTileStatusType status;
    private RoomTileState state;
    private boolean canStack;
    private long topItem = 0;
    private double stackHeight = 0d;
    private long originalTopItem = 0;
    private double originalHeight = 0d;
    private Position redirect = null;
    private boolean canPlaceItemHere = false;
    private boolean hasItems = false;
    private boolean hasMagicTile = false;
    private boolean hasAdjustableHeight = false;
    private boolean hasGate = false;
    private List<RoomItemFloor> items;
    private Map<Integer, Consumer<RoomEntity>> pendingEvents = new ConcurrentHashMap<>();

    /**
     * Creates a room tile instance for the room subsystem.
     *
     * @param mappingInstance Mapping instance supplied by the caller.
     * @param position Position supplied by the caller.
     */
    public RoomTile(RoomMapping mappingInstance, Position position) {
        this.mappingInstance = mappingInstance;
        this.position = position;
        this.entities = new ConcurrentHashSet<>();
        this.items = new ArrayList<>(); // maybe change this in the future..

        this.reload();
    }

    /**
     * Returns the adjacent tiles for this room contract.
     *
     * @param from From supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<RoomTile> getAdjacentTiles(Position from) {
        final List<RoomTile> roomTiles = Lists.newArrayList();

        for (int rotation : Position.COLLIDE_TILES) {
            final RoomTile tile = this.mappingInstance.getTile(this.getPosition().squareInFront(rotation));

            roomTiles.add(tile);
        }

        roomTiles.sort((left, right) -> {
            if (left == null || right == null || left.getPosition() == null || right.getPosition() == null) return -1;

            final double distanceFromLeft = left.getPosition().distanceTo(from);
            final double distanceFromRight = right.getPosition().distanceTo(from);

            return Double.compare(distanceFromLeft, distanceFromRight);
        });

        return roomTiles;
    }

    /**
     * Executes reload for this room contract.
     */
    public void reload() {
        // reset the tile data
        this.hasItems = false;
        this.redirect = null;
        this.movementNode = RoomEntityMovementNode.OPEN;
        this.status = RoomTileStatusType.NONE;
        this.canStack = true;
        this.hasMagicTile = false;
        this.topItem = 0;
        this.originalHeight = 0d;
        this.originalTopItem = 0;
        this.stackHeight = 0d;
        this.hasAdjustableHeight = false;
        this.hasGate = false;

        if (this.mappingInstance.getModel().getSquareState()[this.getPosition().getX()][this.getPosition().getY()] == null) {
            this.canPlaceItemHere = false;
            this.state = RoomTileState.INVALID;
        } else {
            this.canPlaceItemHere = this.mappingInstance.getModel().getSquareState()[this.getPosition().getX()][this.getPosition().getY()].equals(RoomTileState.VALID);
            this.state = this.mappingInstance.getModel().getSquareState()[this.getPosition().getX()][this.getPosition().getY()];
        }

        // component item is an item that can be used along with an item that overrides the height.
        boolean hasComponentItem = false;

        double highestHeight = 0d;
        long highestItem = 0;

        Double staticOverrideHeight = null;
        Double overrideHeight = null;

        this.items.clear();

        for (Map.Entry<Long, RoomItemFloor> itemEntry : mappingInstance.getRoom().getItems().getFloorItems().entrySet()) {
            final RoomItemFloor item = itemEntry.getValue();

            if (item == null || item.getDefinition() == null) continue; // it's null!

            if (item.getPosition().getX() == this.position.getX() && item.getPosition().getY() == this.position.getY()) {
                items.add(item);
            } else {
                List<AffectedTile> affectedTiles = AffectedTile.getAffectedTilesAt(
                        item.getDefinition().getLength(), item.getDefinition().getWidth(), item.getPosition().getX(), item.getPosition().getY(), item.getRotation());

                for (AffectedTile tile : affectedTiles) {
                    if (this.position.getX() == tile.x && this.position.getY() == tile.y) {
                        if (!items.contains(item)) {
                            items.add(item);
                        }
                    }
                }
            }
        }

        for (RoomItemFloor item : new ArrayList<>(items)) {
            if (item.getDefinition() == null)
                continue;

            if (item instanceof GateFloorItem || item instanceof GroupGateFloorItem || item instanceof VIPGateFloorItem) {
                this.hasGate = true;
            }

            this.hasItems = true;

            final double totalHeight = item.getPosition().getZ() + (item.getOverrideHeight() != -1d ? item.getOverrideHeight() : item.getDefinition().getHeight());

            if (totalHeight > highestHeight) {
                highestHeight = totalHeight;
                highestItem = item.getId();
            }

            final boolean isGate = item instanceof GateFloorItem;

            if (item instanceof MagicStackFloorItem) {
                this.hasMagicTile = true;
            }

            if (!item.getDefinition().canWalk() && !isGate) {
                if (highestItem == item.getId())
                    movementNode = RoomEntityMovementNode.CLOSED;
            }

            switch (item.getDefinition().getInteraction().toLowerCase()) {
                case "bed":
                    status = RoomTileStatusType.LAY;
                    movementNode = RoomEntityMovementNode.END_OF_ROUTE;

                    if (item.getRotation() == 2 || item.getRotation() == 6) {
                        this.redirect = new Position(item.getPosition().getX(), this.getPosition().getY());
                    } else if (item.getRotation() == 0 || item.getRotation() == 4) {
                        this.redirect = new Position(this.getPosition().getX(), item.getPosition().getY());
                    }

                    break;

                case "gate":
                    movementNode = ((GateFloorItem) item).isOpen() ? RoomEntityMovementNode.OPEN : RoomEntityMovementNode.CLOSED;
                    break;

                case "onewaygate":
                    movementNode = RoomEntityMovementNode.CLOSED;
                    break;

                case "wf_pyramid":
                    movementNode = item.getItemData().getData().equals("1") ? RoomEntityMovementNode.OPEN : RoomEntityMovementNode.CLOSED;
                    break;

                case "freeze_block":
                    movementNode = ((FreezeBlockFloorItem) item).isDestroyed() ? RoomEntityMovementNode.OPEN : RoomEntityMovementNode.CLOSED;
                    break;

                case "survival_chest":
                    movementNode = ((SurvivalBlockFloorItem) item).isDestroyed() ? RoomEntityMovementNode.OPEN : RoomEntityMovementNode.CLOSED;
                    break;
            }

            if (item instanceof BreedingBoxFloorItem) {
                movementNode = RoomEntityMovementNode.END_OF_ROUTE;
            }

            if (item instanceof SnowboardJumpFloorItem) {
                hasComponentItem = true;
            }

            if (item.getDefinition().canSit()) {
                status = RoomTileStatusType.SIT;
                movementNode = RoomEntityMovementNode.END_OF_ROUTE;
            }

            if (item.getDefinition().getInteraction().equals("bed")) {
                status = RoomTileStatusType.LAY;
                movementNode = RoomEntityMovementNode.END_OF_ROUTE;
            }

            if (!item.getDefinition().canStack()) {
                this.canStack = false;
            }

            if (item.getOverrideHeight() != -1d) {
                if (item instanceof MagicStackFloorItem) {
                    staticOverrideHeight = item.getOverrideHeight();
                }

                if (item instanceof AdjustableHeightFloorItem) {
                    overrideHeight = highestHeight;
                }

                else {
                    if (overrideHeight != null) {
                        overrideHeight += item.getOverrideHeight() + (hasComponentItem ? 1.0 : 0d);
                    } else {
                        overrideHeight = item.getOverrideHeight() + (hasComponentItem ? 1.0 : 0d);
                    }
                }
            }
        }

        if (overrideHeight != null) {
            this.hasAdjustableHeight = true;
            this.canStack = true;
            this.stackHeight = staticOverrideHeight != null ? staticOverrideHeight : overrideHeight;

            this.originalHeight = highestHeight;
        } else {
            this.stackHeight = highestHeight;
        }

        this.topItem = highestItem;

        if (this.stackHeight == 0d) {
            this.stackHeight = this.mappingInstance.getModel().getSquareHeight()[this.position.getX()][this.position.getY()];
        }

        if (this.originalHeight == 0)
            this.originalHeight = this.stackHeight;
    }

    /**
     * Releases resources owned by this room component.
     */
    public void dispose() {
        this.pendingEvents.clear();
        this.items.clear();
        this.entities.clear();
    }

    /**
     * Handles the entity enters tile callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void onEntityEntersTile(RoomEntity entity) {
        if (this.pendingEvents.containsKey(entity.getId())) {
            this.pendingEvents.get(entity.getId()).accept(entity);
            this.pendingEvents.remove(entity.getId());
        }
    }

    /**
     * Executes schedule event for this room contract.
     *
     * @param entityId Entity id supplied by the caller.
     * @param event Event supplied by the caller.
     */
    public void scheduleEvent(int entityId, Consumer<RoomEntity> event) {
        this.pendingEvents.put(entityId, event);
    }

    /**
     * Returns the movement node for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntityMovementNode getMovementNode() {
        return this.movementNode;
    }

    /**
     * Returns the stack height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getStackHeight() {
        return this.getStackHeight(null);
    }

    /**
     * Returns the stack height for this room contract.
     *
     * @param itemToStack Item to stack supplied by the caller.
     * @return Value exposed by the contract.
     */
    public double getStackHeight(RoomItemFloor itemToStack) {
        double stackHeight;

        if (this.hasMagicTile()/* || (topItem != null && topItem instanceof AdjustableHeightFloorItem)*/) {
            stackHeight = this.stackHeight;
        } else {

            stackHeight = itemToStack != null && itemToStack.getId() == this.getTopItem() ? itemToStack.getPosition().getZ() : this.originalHeight;
        }

        return stackHeight;
    }

    /**
     * Returns the walk height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getWalkHeight() {
        double height = this.stackHeight;

        RoomItemFloor roomItemFloor = this.mappingInstance.getRoom().getItems().getFloorItem(this.topItem);

        if (roomItemFloor != null && (roomItemFloor.getDefinition().canSit() || roomItemFloor instanceof BedFloorItem || roomItemFloor instanceof SnowboardJumpFloorItem)) {

            if (roomItemFloor instanceof SnowboardJumpFloorItem) {
                height += 1.0;
            } else {
                height -= roomItemFloor.getDefinition().getHeight();
            }
        }

        if (this.hasAdjustableHeight && roomItemFloor instanceof SeatFloorItem) {
            height += ((SeatFloorItem) roomItemFloor).getSitHeight();
        }

        return height;
    }

    /**
     * Indicates whether reachable applies to this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isReachable(RoomEntity entity) {
        List<Square> path = EntityPathfinder.getInstance().makePath(entity, this.position);
        return path != null && path.size() > 0;
    }

    /**
     * Indicates whether reachable applies to this room contract.
     *
     * @param object Object supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isReachable(RoomObject object) {
        List<Square> path = ItemPathfinder.getInstance().makePath(object, this.position);
        return path != null && path.size() > 0;
    }

    /**
     * Returns the entity for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntity getEntity() {
        for (RoomEntity entity : this.getEntities()) {
            return entity;
        }

        return null;
    }

    /**
     * Returns the entities for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<RoomEntity> getEntities() {
        return this.entities;
    }

    /**
     * Returns the status for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomTileStatusType getStatus() {
        return this.status;
    }

    /**
     * Returns the position for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Indicates whether this room contract can stack.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canStack() {
        return this.canStack;
    }

    /**
     * Returns the top item for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getTopItem() {
        return this.topItem;
    }

    /**
     * Updates the top item for this room contract.
     *
     * @param topItem Top item supplied by the caller.
     */
    public void setTopItem(int topItem) {
        this.topItem = topItem;
    }

    /**
     * Returns the top item instance for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomItemFloor getTopItemInstance() {
        return this.mappingInstance.getRoom().getItems().getFloorItem(this.getTopItem());
    }

    /**
     * Returns the redirect for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getRedirect() {
        return redirect;
    }

    /**
     * Updates the redirect for this room contract.
     *
     * @param redirect Redirect supplied by the caller.
     */
    public void setRedirect(Position redirect) {
        this.redirect = redirect;
    }

    /**
     * Returns the original top item for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getOriginalTopItem() {
        return originalTopItem;
    }

    /**
     * Returns the original height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getOriginalHeight() {
        return originalHeight;
    }

    /**
     * Indicates whether this room contract can place item here.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canPlaceItemHere() {
        return canPlaceItemHere;
    }

    /**
     * Indicates whether this room contract has items.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasItems() {
        return hasItems;
    }

    /**
     * Returns the tile height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getTileHeight() {
        return this.mappingInstance.getModel().getSquareHeight()[this.position.getX()][this.position.getY()];
    }

    /**
     * Indicates whether this room contract has magic tile.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasMagicTile() {
        return this.hasMagicTile;
    }

    /**
     * Returns the items for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<RoomItemFloor> getItems() {
        return items;
    }

    /**
     * Indicates whether this room contract has adjustable height.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasAdjustableHeight() {
        return hasAdjustableHeight;
    }

    /**
     * Returns the state for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomTileState getState() {
        return state;
    }

    /**
     * Indicates whether this room contract has gate.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasGate() {
        return this.hasGate;
    }
}
